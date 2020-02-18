package org.apache.camel.community.so;

import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration()
        .component("undertow")
        .host("127.0.0.1")
        .port(9090)
        .bindingMode(RestBindingMode.auto)
        .dataFormatProperty("prettyPrint", "true");


    rest("/api")
        .post("/erroradmin").consumes(MediaType.APPLICATION_JSON_VALUE).type(ErrorType.class).to("direct:postError")
        .get("/erroradmin/{id}").produces(MediaType.APPLICATION_JSON_VALUE).to("direct:getError");

    from("direct:getError")
        .process( exchange -> {
           exchange.getMessage().setBody(("{'messageID':'"+ UUID.randomUUID().toString() +"','ticketID':'000000'}"));
        });
    from("direct:postError")
        .process(exchange -> {
            ErrorType typ = exchange.getIn().getBody(ErrorType.class);
            System.out.println(String.format("Received Type:%s {MessageID: %s, TicketID: %s} ",typ.getClass().getName(),typ.getMessageID(),typ.getTicketID()));
        }).transform().constant("200 OK");
    }

}
