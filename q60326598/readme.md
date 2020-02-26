[Asnwer to SO:60326598](https://stackoverflow.com/questions/60326598/camel-3-how-to-intercept-a-route-from-onexception-using-interceptsendtoendpo/60416158#60416158)

First of all, thank you very much for a very well framed question with proper code samples! Mock component's manual refers to introduction of a feature ['Mocking Existing Endpoints'](https://camel.apache.org/components/latest/mock-component.html#_mocking_existing_endpoints) very likely this is what is blocked you. I am not very sure which version of Camel introduced this feature. 

In any case, to get around the current limitation, you may use the automatic mocking feature itself. Your test method could be changed as below to get it working.

````Kotlin
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
````

There were two changes made to the original code.

1. Mock endpoint was renamed from `mock:test` to be in line with the automatically generated mock endpoint types (`mock:direct:errors`)
2. A call to `routeBuilder.mockEndpoints(errorUri)` so that camel can automatically inject Mocks, for the patters as described by `errorUri`

In addition to it, it is possible replace the block below
````kotlin
  routeBuilder.mockEndpoints(errorUri)
  routeBuilder.interceptSendToEndpoint(errorUri)
          .skipSendToOriginalEndpoint()
          .to(mockEndpoint)
````
with a one liner `routeBuilder.mockEndpointsAndSkip(errorUri)`, unless there are specific reasons to use `intercept` as you've mentioned in your question.

### Additional observations:

Running your code, without changes clearly show a `RouteReifier` hooking in the Mock endpoint, `mock://test` in place of `direct:errors`. In addition, the `context` appeared to have a proper `endpointStrategy` as well. 

This could be a bug. Though there are easy alternatives, please consider raising this as an issue on [ASF Jira](https://issues.apache.org/jira/projects/CAMEL/issues) as well.

````
14:32:34.307 [main] INFO org.apache.camel.reifier.RouteReifier - Adviced route before/after as XML:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<route xmlns="http://camel.apache.org/schema/spring" customId="true" id="direct:start">
    <from uri="direct:start"/>
    <onException>
        <exception>java.lang.Exception</exception>
        <to uri="direct:errors"/>
    </onException>
    <throwException/>
</route>

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<route xmlns="http://camel.apache.org/schema/spring" customId="true" id="direct:start">
    <from uri="direct:start"/>
    <onException>
        <exception>java.lang.Exception</exception>
        <to uri="direct:errors"/>
    </onException>
    <interceptSendToEndpoint skipSendToOriginalEndpoint="true" uri="direct:errors">
        <to uri="mock://test"/>
    </interceptSendToEndpoint>
    <throwException/>
</route>
````

### Test passing in IDE

[![Screenshot from IDE][1]][1]


### Java implementation (if someone needs it)
````Java

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

public class Camel3RouteTest extends CamelTestSupport {

    private static final String startUri = "direct:start";
    private static final String errorUri = "direct:errors";
    private static final String mockErrorURI = "mock:"+ errorUri;
    private static final String ERROR_MESSAGE = "ERROR MESSAGE!";

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                onException(Exception.class)
                        .to(errorUri);

                from(errorUri)
                        .routeId(errorUri)
                        .log("error happened!");

                from(startUri)
                        .routeId(startUri)
                        .throwException(new Exception(ERROR_MESSAGE));

            }
        };
    }

    @Test
    public void testExecution() throws Exception {

        AdviceWithRouteBuilder.adviceWith(context, startUri, adviceWithRouteBuilder -> {
            //a.mockEndpointsAndSkip(errorUri);

            adviceWithRouteBuilder.mockEndpoints(errorUri);
            adviceWithRouteBuilder.interceptSendToEndpoint(errorUri).skipSendToOriginalEndpoint().to(mockErrorURI);
        });

        MockEndpoint mockEndpoint = getMockEndpoint(mockErrorURI);
        mockEndpoint.setExpectedMessageCount(1);

        context.start();
        sendBody(startUri, "A Test message");
        assertMockEndpointsSatisfied();

        Assert.assertNotNull(mockEndpoint.getExchanges().get(0).getProperty(Exchange.EXCEPTION_CAUGHT));
        Exception receivedException = (Exception) mockEndpoint.getExchanges().get(0).getProperty(Exchange.EXCEPTION_CAUGHT);

        Assert.assertTrue(receivedException instanceof Exception);
        Assert.assertEquals(receivedException.getMessage(), ERROR_MESSAGE);


    }


}

````

  [1]: https://i.stack.imgur.com/TPAio.png
