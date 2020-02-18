# Answer to [SO:60264067](https://stackoverflow.com/questions/60264067/apache-camel-how-to-process-a-json-object-sent-by-curl-into-headers)

Welcome to Stackoverflow!
I strongly believe the mention of `bindingMode="json"` at Line 13, is the root cause of this failure.
The [manual](https://camel.apache.org/manual/latest/rest-dsl.html) says

> When using binding you must also configure what POJO type to map to. This is mandatory for incoming messages, and optional for outgoing.

I am really scared of the XML DSL but, here is an approximate equivalent rest DSL in Java.

````Java
@Component
@Slf4j
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration()
                .component("undertow")
                .host("127.0.0.1")
                .port(9090)
                //This works only when a POJO mapping is possible - Ref: https://camel.apache.org/manual/latest/rest-dsl.html
                //<quote>When using binding you must also configure what POJO type to map to. This is mandatory for incoming messages, and optional for outgoing.</quote>
                //.bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");


        rest("/api")
                .post("/erroradmin")
                .to("direct:postError")

                .get("/erroradmin/{id}").to("direct:getError");

        from("direct:getError")
                .process(exchange -> {
                    exchange.getMessage().setBody(("{'messageID':'" + UUID.randomUUID().toString() + "','ticketID':'1234'}"));
                });

        from("direct:postError")
                .unmarshal()
                .json(JsonLibrary.Jackson)
                .process(exchange -> {
                    log.info("Type of incoming body:{}", exchange.getIn().getBody().getClass().getName());
                    log.info("Incoming body:{}", exchange.getIn().getBody());
                }).transform().constant("{'httpResponse:200':'OK'}");
    }

}
````

Once it is up, and I send the payload with cURL as below

````bash
curl -d '{"msgId":"EB2C7265-EF68-4F8F-A709-BEE2C52E842B", "ticket":"ERR001"}' -H "Content-Type: application/json" -X POST http://localh
ost:9090/api/erroradmin
````

You'll see something like the below in the logs
````bash
2020-02-18 11:44:13.032  INFO 2708 --- [  XNIO-1 task-4] o.a.c.community.so.MySpringBootRouter    : Type of incoming body:java.util.LinkedHashMap
2020-02-18 11:44:13.032  INFO 2708 --- [  XNIO-1 task-4] o.a.c.community.so.MySpringBootRouter    : Incoming body:{msgId=EB2C7265-EF68-4F8F-A709-BEE2C52E842B, ticket=ERR001}

````

Oh btw, your original JSON payload was malformed.
The entire Java project is available here, if you wish to play around
