package org.example.service;


import org.example.server.ServerTest;

import java.util.ArrayList;
import java.util.List;

// Load Balancer class
public class LoadBalancerSingleThread {
    private List<ServerTest> servers;
    private int currentIndex;

    public LoadBalancerSingleThread() {
        this.servers = new ArrayList<ServerTest>();
        this.currentIndex = 0;
    }

    // Add a server to the load balancer
    public void addServer(ServerTest server) {
        servers.add(server);
    }

    // Distribute the request using Round Robin
    public void distributeRequest(String request) {
        if (servers.isEmpty()) {
            System.out.println("No servers available to handle the request!");
            return;
        }

        // Find the next available server
        int attempts = 0;
        while (attempts < servers.size()) {
            ServerTest server = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size(); // Move to the next server

            if (server.isAvailable()) {
                server.handleRequest(request);
                return;
            }
            attempts++;
        }

        // If no servers are available
        System.out.println("All servers are down! Request \"" + request + "\" could not be handled.");
    }
}
