package org.apache.camel.community.so;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
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
