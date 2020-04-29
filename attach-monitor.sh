#!/bin/sh

attach=./agent-attach/target/agent-attach-1.0-SNAPSHOT-jar-with-dependencies.jar
agent=./agent-monitor/target/agent-monitor-1.0-SNAPSHOT-jar-with-dependencies.jar=port~10000,another_poperty~value

java -Dname=agent-attach -jar $attach application $agent agent-attach

