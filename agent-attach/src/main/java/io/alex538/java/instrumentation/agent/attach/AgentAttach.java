package io.alex538.java.instrumentation.agent.attach;

import com.sun.tools.attach.VirtualMachineDescriptor;

public class AgentAttach {
    public static void main(String[] args) {
        AgentLoader loader = new AgentLoader();
        VirtualMachineDescriptor descriptor = loader.getJvmPidByApplicationName(args[0], args[2]);
        loader.attach(descriptor, args[1]);
    }
}
