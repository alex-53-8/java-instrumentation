package io.alex538.java.instrumentation.agent.monitor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

@Slf4j
public class HttpServer extends Thread {
    private Socket socket;

    public static HttpServer createAndStart(int port) {
        HttpServer monitorService = new HttpServer(port);
        monitorService.start();

        return monitorService;
    }

    private final int port;

    public HttpServer(int port) {
        this.port = port;
        setName("MonitorHttpService");
        setDaemon(true);
    }

    @SneakyThrows
    public void run() {
        ServerSocket server = new ServerSocket(port, 100, InetAddress.getByName("localhost"));
        log.info("[Monitor] Listening for connection on port {}...", port);

        while (!isInterrupted()) {
            try (Socket socket = this.socket = server.accept()) {
                socket.getOutputStream().write(toHttpResponse(PerformanceMonitor.getSystemInformation()));
            } catch (SocketException e) {
                log.error("[Monitor] An exception happened during socket closing: " + e.toString());
            }
        }
    }

    byte[] toHttpResponse(String info) {
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-type: text\r\n");
        sb.append("Content-length: ").append(info.getBytes().length);
        sb.append("\r\n\r\n");
        sb.append(info);

        return sb.toString().getBytes();
    }

    public void interrupt() {
        super.interrupt();

        try {
            socket.close();
        } catch (IOException e) {
            log.error("[Monitor] An exception happened during socket closing: ", e);
        }
    }
}
