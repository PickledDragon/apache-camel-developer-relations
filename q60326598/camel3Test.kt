package org.apache.camel.community

import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.AdviceWithRouteBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.Test

class Camel3Test : CamelTestSupport() {

    val startUri = "direct:start"
    val errorUri = "direct:errors"

    override fun isUseAdviceWith(): Boolean {
        return true
    }

    override fun createRouteBuilder(): RoutesBuilder {
        return object : RouteBuilder() {
            override fun configure() {

                onException(Exception::class.java)
                        .to(errorUri)
                        .handled(true);

                from(errorUri)
                        .routeId(errorUri)
                        .log("error happened!")

                from(startUri)
                        .routeId(startUri)
                        .throwException(Exception())
            }

        }
    }

    @Test
    fun `exception is routed to error logging route`() {
        val exchange = createExchangeWithBody("")

        // Create new mock endpoint that will replace our error route
        val mockEndpoint = getMockEndpoint("mock:$errorUri")

        AdviceWithRouteBuilder.adviceWith(context, startUri) { routeBuilder ->
            routeBuilder.mockEndpoints(errorUri)
            routeBuilder.interceptSendToEndpoint(errorUri)
                    .skipSendToOriginalEndpoint()
                    .to(mockEndpoint)
        }

        context.start()

        mockEndpoint.expectedMessageCount(1)

        template.send(startUri, exchange)

        assertMockEndpointsSatisfied()
    }
}
