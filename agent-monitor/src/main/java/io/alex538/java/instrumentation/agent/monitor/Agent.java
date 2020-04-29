package io.alex538.java.instrumentation.agent.monitor;

import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("[Monitor] In premain method");
        startMonitoring(agentArgs);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        log.info("[Monitor] In agentmain method");
        startMonitoring(agentArgs);
    }

    static void startMonitoring(String args) {
        log.info("[Monitor] starting monitoring with arguments: {}", args);

        Map<String, String> properties = argsToMap(args);
        int port = Integer.parseInt(properties.get("port"));
        HttpServer.createAndStart(port);
    }

    static Map<String, String> argsToMap(String args) {
        return Arrays
                .stream(args.split(","))
                .map(pair -> pair.split("~"))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

}
