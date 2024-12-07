package org.example.server;

// A simple Server class to represent backend servers
public class ServerTest {
    private String name;
    private boolean isAvailable;

    public ServerTest(String name) {
        this.name = name;
        this.isAvailable = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Method to simulate processing a request
    public void handleRequest(String request) {
        if (isAvailable) {
            System.out.println("Request \"" + request + "\" handled by server: " + name);
        } else {
            System.out.println("Server " + name + " is down!");
        }
    }
}
