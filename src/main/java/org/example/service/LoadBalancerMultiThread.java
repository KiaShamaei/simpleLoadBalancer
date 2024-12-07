package org.example.service;

import org.example.server.ServerTestMultiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LoadBalancerMultiThread {
    private List<ServerTestMultiThread> servers;
    private int currentIndex;
    private ExecutorService executorService;

    public LoadBalancerMultiThread(int threadPoolSize) {
        this.servers = new ArrayList<>();
        this.currentIndex = 0;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize); // Thread pool for request processing
    }

    // Add a server to the load balancer
    public void addServer(ServerTestMultiThread server) {
        servers.add(server);
    }

    // Distribute a request using Round Robin
    public Future<String> distributeRequest(String request) {
        if (servers.isEmpty()) {
            throw new IllegalStateException("No servers available to handle requests!");
        }

        // Get the next available server using Round Robin
        ServerTestMultiThread server;
        synchronized (this) {
            server = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size();
        }

        // Submit the request to the server using ExecutorService
        return executorService.submit(() -> {
            if (server.isAvailable()) {
                server.handleRequest(request);
                return "Request \"" + request + "\" successfully handled by " + server.getName();
            } else {
                return "Request \"" + request + "\" failed because " + server.getName() + " is unavailable.";
            }
        });
    }

    // Shut down the executor service
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
