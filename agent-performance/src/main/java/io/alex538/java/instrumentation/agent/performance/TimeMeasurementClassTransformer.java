package io.alex538.java.instrumentation.agent.performance;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

@Slf4j
public class TimeMeasurementClassTransformer implements ClassFileTransformer {

    private String targetClassName;

    private String targetMethod;

    public TimeMeasurementClassTransformer(String targetClassName, String targetMethod) {
        this.targetClassName = targetClassName;
        this.targetMethod = targetMethod;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;
        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");

        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }

        log.info("[Agent] Transforming class [{}]", className);
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.appendClassPath(new LoaderClassPath(loader));
            CtClass cc = cp.get(targetClassName);
            CtMethod m = cc.getDeclaredMethod(targetMethod);
            m.addLocalVariable("start", CtClass.longType);
            m.insertBefore("start = System.currentTimeMillis();");

            StringBuilder endBlock = new StringBuilder();

            m.addLocalVariable("runTime", CtClass.longType);
            endBlock.append("runTime = (System.currentTimeMillis() - start);");

            endBlock.append("log.info(\"[" + targetMethod + "] method run time: \" + runTime + \" ms\");");

            m.insertAfter(endBlock.toString());

            byteCode = cc.toBytecode();
            cc.detach();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            log.error("Exception", e);
        }

        return byteCode;
    }
}