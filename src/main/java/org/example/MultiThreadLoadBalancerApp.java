package org.example;

import org.example.server.ServerTest;
import org.example.server.ServerTestMultiThread;
import org.example.service.LoadBalancerMultiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MultiThreadLoadBalancerApp {
    public static void main(String[] args) {
        // Create the load balancer with a thread pool size of 3
        LoadBalancerMultiThread loadBalancer = new LoadBalancerMultiThread(3);

        // Add servers to the load balancer
        ServerTestMultiThread server1 = new ServerTestMultiThread("Server-1");
        ServerTestMultiThread server2 = new ServerTestMultiThread("Server-2");
        ServerTestMultiThread server3 = new ServerTestMultiThread("Server-3");
        loadBalancer.addServer(server1);
        loadBalancer.addServer(server2);
        loadBalancer.addServer(server3);

        // Simulate client requests
        var requestFutures = new ArrayList<Future<String>>();
        for (int i = 1; i <= 10; i++) {
            String request = "Request-" + i;
            requestFutures.add(loadBalancer.distributeRequest(request));
        }

        // Retrieve and print the results of the requests
        for (Future<String> future : requestFutures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error while processing request: " + e.getMessage());
            }
        }

        // Shut down the load balancer
        loadBalancer.shutdown();
    }

}
