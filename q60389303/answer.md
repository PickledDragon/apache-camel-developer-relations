# [Answer to SO:60389303](https://stackoverflow.com/questions/60389303/what-is-mediation-engine/60417768#60417768)
The *mediation engine* reference in this context is originating from the topic of [Enterprise Application Integration][1] and is closely related to what [GoF Mediator Pattern][2] does - That is encapsulate communication between entities. In the case of [EAI][1], a mediator/mediation engine sits between multiple disparate systems and acts as a broker between them, instead of letting the systems communicate directly. 

Mediation approach in EAI offers capabilities like

1. Reduced coupling between systems. For instance, you do not have to learn and implement a legacy mainframe protocol in modern systems, just because you want to get some data from main frames. A mediation engine like Apache Camel could talk [REST][3] over HTTPS at one end and some archaic mainframe protocol on the other end. 
2. Ease of migration: Once the mainframes area replaced with something else, you could just change the mediation layer to do something about it, instead of modifying multiple impacted systems that used to talk to mainframes.
3. Access to single resource/service via multiple channels: Let's say you have an old system that currently does [SOAP][4] over HTTP but you would like to offer [REST][3] with JSON payload to some of your new customers. Instead of building completely new systems up-front for this purpose, you could throw Apache Camel as a mediator and it would accept JSON payloads at one end and SOAP on the other end. Whoever wants to talk JSON can go through Camel and whoever wants to do SOAP may continue in a direct connection to the legacy system. Someday if some hypothetical `FooBar` protocol becomes popular, and if Apache Camel provides you a `FooBar` component, your users who demands `FooBar` support could be routed through Camel, to the system that still speaks SOAP.

All these stuff is discussed in detail in the [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/) site and book. Apache Camel implements truck loads of patterns as described in the EIP Book. I hope this answer helped you to understand the *mediation* role Apache Camel could play in Enterprise IT ecosystems.


  [1]: https://en.wikipedia.org/wiki/Enterprise_application_integration
  [2]: https://en.wikipedia.org/wiki/Mediator_pattern
  [3]: https://en.wikipedia.org/wiki/Representational_state_transfer
  [4]: https://en.wikipedia.org/wiki/SOAP
