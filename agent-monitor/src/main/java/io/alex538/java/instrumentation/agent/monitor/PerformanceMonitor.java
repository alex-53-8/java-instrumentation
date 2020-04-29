package io.alex538.java.instrumentation.agent.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.management.ManagementFactory.getOperatingSystemMXBean;

public class PerformanceMonitor {

    public static String getSystemInformation() {
        OperatingSystemMXBean system = getOperatingSystemMXBean();
        Runtime rt = Runtime.getRuntime();
        Map<String, Object> parameters = new LinkedHashMap<>();

        parameters.put("systemLoad", system.getSystemLoadAverage());
        parameters.put("processorsCount", system.getAvailableProcessors());
        parameters.put("memory", "free: " + rt.freeMemory() + ", total: " + rt.totalMemory() + ", max: " + rt.maxMemory());
        parameters.put("version", system.getVersion());
        parameters.put("jvmArgs", ManagementFactory.getRuntimeMXBean().getInputArguments().stream().collect(Collectors.joining("\r\n")));

        return parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "\r\n" + e.getValue())
                .collect(Collectors.joining("\r\n\r\n"));
    }

}