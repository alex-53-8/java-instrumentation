package io.alex538.java.instrumentation.application;

public class ThreadUtils {

    public static void sleep() {
        int sleepTime = random(100, 1000);
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int random(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

}
