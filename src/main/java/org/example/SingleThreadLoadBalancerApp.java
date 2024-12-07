package org.example;

import org.example.server.ServerTest;
import org.example.service.LoadBalancerSingleThread;

/**
 * Hello world!
 *
 */
public class SingleThreadLoadBalancerApp
{
    public static void main(String[] args) {
        System.out.println( "LoadBalancer get ready ..." );
        // Create the Load Balancer
        LoadBalancerSingleThread loadBalancer = new LoadBalancerSingleThread();

        // Add servers to the load balancer
        ServerTest server1 = new ServerTest("Server-1");
        ServerTest server2 = new ServerTest("Server-2");
        ServerTest server3 = new ServerTest("Server-3");
        loadBalancer.addServer(server1);
        loadBalancer.addServer(server2);
        loadBalancer.addServer(server3);

        // Simulate client requests
        System.out.println("Distributing requests...");
        for (int i = 1; i <= 10; i++) {
            loadBalancer.distributeRequest("Request " + i);
        }

        // Simulate a server failure
        System.out.println("\nSimulating server failure...");
        server2.setAvailable(false);

        // Distribute more requests after failure
        for (int i = 11; i <= 15; i++) {
            loadBalancer.distributeRequest("Request " + i);
        }

        // Restore the server and distribute more requests
        System.out.println("\nRestoring server...");
        server2.setAvailable(true);
        server3.setAvailable(false);
        for (int i = 16; i <= 20; i++) {
            loadBalancer.distributeRequest("Request " + i);
        }
    }
}
