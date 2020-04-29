package io.alex538.java.instrumentation.agent.attach;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class AgentLoader {

    public VirtualMachineDescriptor getJvmPidByApplicationName(String applicationName, String exclude) {
        log.info("Searching JVM application with name: {}", applicationName);
        VirtualMachineDescriptor descriptor = VirtualMachine.list()
                .stream()
                .filter(jvm -> {
                    log.info("checking jvm: {}", jvm.displayName());
                    return jvm.displayName().contains(applicationName) && !jvm.displayName().toLowerCase().contains(exclude);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Target Application not found"));

        log.info("Found JVM application with name {} and pid {}", applicationName, descriptor);

        return descriptor;
    }

    @SneakyThrows
    public void attach(VirtualMachineDescriptor descriptor, String agentJarFile) {
        log.info("Attaching to JVM with PID: " + descriptor + "...");
        VirtualMachine jvm = VirtualMachine.attach(descriptor);
        jvm.loadAgent(new File(agentJarFile).getAbsolutePath());
        jvm.detach();
        log.info("Attached to target JVM and loaded Java agent successfully");
    }

}