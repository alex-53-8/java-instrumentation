#!/bin/sh

attach=./agent-attach/target/agent-attach-1.0-SNAPSHOT-jar-with-dependencies.jar
agent=./agent-performance/target/agent-performance-1.0-SNAPSHOT-jar-with-dependencies.jar=property1~value1,property2~value2

java -jar $attach application $agent agent-attach

