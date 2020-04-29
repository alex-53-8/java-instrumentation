package io.alex538.java.instrumentation.agent.performance;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

@Slf4j
public class Agent {
    private static final String className = "io.alex538.java.instrumentation.application.RestService";
    private static final String methodName = "fetchData";

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("[Time measure] In premain method with args {}", agentArgs);

        addTransformer(className, methodName, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        log.info("[Time measure] In agentmain method with args {}", agentArgs);

        addTransformer(className, methodName, inst);
        applyForLoadedClasses(className, inst);
    }

    public static void addTransformer(String className, String methodName, Instrumentation inst) {
        TimeMeasurementClassTransformer dt = new TimeMeasurementClassTransformer(className, methodName);
        inst.addTransformer(dt, true);
    }

    @SneakyThrows
    public static void applyForLoadedClasses(String className, Instrumentation instrumentation) {
        for(Class<?> clazz: instrumentation.getAllLoadedClasses()) {
            if(clazz.getName().equals(className)) {
                instrumentation.retransformClasses(clazz);
                return;
            }
        }

        log.info("[Time measure] Failed to find class [{}]", className);
    }

}