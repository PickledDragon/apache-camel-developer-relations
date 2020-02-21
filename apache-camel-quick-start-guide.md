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

If everything went 
