#!/bin/sh

java \
-javaagent:"agent-monitor/target/agent-monitor-1.0-SNAPSHOT-jar-with-dependencies.jar=port~10000,another_poperty~value" \
-javaagent:"agent-performance/target/agent-performance-1.0-SNAPSHOT-jar-with-dependencies.jar=property~value,property2~value2" \
-Dsome_property=123 \
-jar application/target/application-1.0-SNAPSHOT.jar