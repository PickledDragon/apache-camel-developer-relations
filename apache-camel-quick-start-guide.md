Apache Camel is a versatile integration framework and allows several modes of deployment. Apache Camel can be deployed as a stand-alone application, in a servlet container, in an OSGi container, as a Kubernetes operator or as a Kafka Connect connector. 

The easiest and the most minimalistic way to get started with Apache Camel is to start it as a stand-alone application. In this example, however, we'll throw some spring boot capabilities into the mix, to demonstrate few extra handy features of Apache Camel provides out-of-the-box.

We'll be using Maven archetypes to get started in a single command. We assume you already have a modern JDK (JDK 8 or higher) and Apache Maven installed and configured on your machine.

Open a terminal, change to a directory where you would like to create a sample Apache Camel project and run the following command.

````Powershell
mvn -DgroupId=org.apache.camel.community -DartifactId=apache-camel-quickstart -Dversion=1.0-SNAPSHOT -DarchetypeGroupId=org.apache.camel.archetypes -DarchetypeArtifactId=camel-archetype-spring-boot -DarchetypeVersion=3.0.1 -DinteractiveMode=false org.apache.maven.plugins:maven-archetype-plugin:RELEASE:generate
````
If your terminal supports escaping line breaks with `\` character, the following block may be used. They do exactly the same thing!
````bash
mvn -DgroupId=org.apache.camel.community \ 
-DartifactId=apache-camel-quickstart \
-Dversion=1.0-SNAPSHOT \
-DarchetypeGroupId=org.apache.camel.archetypes \
-DarchetypeArtifactId=camel-archetype-spring-boot \
-DarchetypeVersion=3.0.1 \
-DinteractiveMode=false \
org.apache.maven.plugins:maven-archetype-plugin:RELEASE:generate
````

If everything went well, Maven will generate a skeleton project for you as shown in the example below logs. Note that this is not the entire log but just the last part of it.

````
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: camel-archetype-spring-boot:3.0.1
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: org.apache.camel.community
[INFO] Parameter: artifactId, Value: apache-camel-quickstart
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: org.apache.camel.community
[INFO] Parameter: packageInPathFormat, Value: org/apache/camel/community
[INFO] Parameter: maven-resources-plugin-version, Value: <your.version.here>
[INFO] Parameter: maven-war-plugin-version, Value: <your.version.here>
[INFO] Parameter: groupId, Value: org.apache.camel.community
[INFO] Parameter: maven-compiler-plugin-version, Value: <your.version.here>
[INFO] Parameter: spring-boot-version, Value: <spring.boot.version>
[INFO] Parameter: slf4j-version, Value: <slf4j.version>
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: log4j-version, Value: <log4j.version>
[INFO] Parameter: camel-version, Value: 3.0.1
[INFO] Parameter: package, Value: org.apache.camel.community
[INFO] Parameter: artifactId, Value: apache-camel-quickstart
[INFO] Project created from Archetype in dir: <your directory name here>\apache-camel-quickstart
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.00 s
[INFO] Finished at: 2020-01-01T13:00:00+01:00
[INFO] ------------------------------------------------------------------------
````
To run the project you've just generated, `cd` to the `apache-camel-quickstart` directory and run the following command!
````
mvn spring-boot:run
````

This command will start the quick start project and you should see output similar to the one shown below.

````
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.1.RELEASE)

2020-02-21 13:34:36.783  INFO 13792 --- [           main] o.a.c.community.MySpringBootApplication  : Starting MySpringBootApplication on WPU8L0050493 with PID 13792 (C:\workspace\projects\stackoverflow\apache-camel-quickstart\target\classes started by BN83XR in c:\workspace\projects\stackoverflow\apache-camel-quickstart)
2020-02-21 13:34:36.786  INFO 13792 --- [           main] o.a.c.community.MySpringBootApplication  : No active profile set, falling back to default profiles: default
2020-02-21 13:34:37.811  INFO 13792 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.apache.camel.spring.boot.CamelAutoConfiguration' of type [org.apache.camel.spring.boot.CamelAutoConfiguration$$EnhancerBySpringCGLIB$$72a2a9b] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2020-02-21 13:34:38.356  WARN 13792 --- [           main] io.undertow.websockets.jsr               : UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
2020-02-21 13:34:38.390  INFO 13792 --- [           main] io.undertow.servlet                      : Initializing Spring embedded WebApplicationContext
2020-02-21 13:34:38.390  INFO 13792 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1544 ms
2020-02-21 13:34:38.924  INFO 13792 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-02-21 13:34:39.222  INFO 13792 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 3 endpoint(s) beneath base path '/actuator'
2020-02-21 13:34:39.488  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootRoutesCollector   : Loading additional Camel XML routes from: classpath:camel/*.xml
2020-02-21 13:34:39.489  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootRoutesCollector   : Loading additional Camel XML rests from: classpath:camel-rest/*.xml
2020-02-21 13:34:39.493  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootCamelContext      : Apache Camel 3.0.1 (CamelContext: MyCamel) is starting
2020-02-21 13:34:39.500  INFO 13792 --- [           main] o.a.c.management.JmxManagementStrategy   : JMX is enabled
2020-02-21 13:34:39.771  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootCamelContext      : StreamCaching is not in use. If using streams then its recommended to enable stream caching. See more details at http://camel.apache.org/stream-caching.html
2020-02-21 13:34:39.824  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootCamelContext      : Route: hello started and consuming from: timer://hello?period=2000
2020-02-21 13:34:39.832  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootCamelContext      : Total 1 routes, of which 1 are started
2020-02-21 13:34:39.832  INFO 13792 --- [           main] o.a.c.s.boot.SpringBootCamelContext      : Apache Camel 3.0.1 (CamelContext: MyCamel) started in 0.341 seconds
2020-02-21 13:34:39.855  INFO 13792 --- [           main] io.undertow                              : starting server: Undertow - 2.0.27.Final
2020-02-21 13:34:39.863  INFO 13792 --- [           main] org.xnio                                 : XNIO version 3.3.8.Final
2020-02-21 13:34:39.878  INFO 13792 --- [           main] org.xnio.nio                             : XNIO NIO Implementation Version 3.3.8.Final
2020-02-21 13:34:39.961  INFO 13792 --- [           main] o.s.b.w.e.u.UndertowServletWebServer     : Undertow started on port(s) 8080 (http) with context path ''
2020-02-21 13:34:39.964  INFO 13792 --- [           main] o.a.c.community.MySpringBootApplication  : Started MySpringBootApplication in 3.673 seconds (JVM running for 4.141)
Hello World
Hello World
Hello World
Hello World
Hello World
````
Congratulations!! Now that you've just created your first Apache Camel project and ran it successfully, let's see what happened behind the scenes.
