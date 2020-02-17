#/bin/bash

mvn -DgroupId=org.apache.camel.community.so -DartifactId=q$1 -Dversion=1.0-SNAPSHOT -DarchetypeGroupId=org.apache.camel.archetypes -DarchetypeArtifactId=camel-archetype-spring-boot -DarchetypeVersion=3.0.1 org.apache.maven.plugins:maven-archetype-plugin:RELEASE:generate
