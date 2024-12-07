package org.example.server;

import java.util.concurrent.TimeUnit;

public class ServerTestMultiThread {
    private String name;
    private boolean isAvailable;

    public ServerTestMultiThread(String name) {
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

    // Simulate handling a request
    public void handleRequest(String request) {
        try {
            System.out.println("Server " + name + " is processing: " + request);
            TimeUnit.MILLISECONDS.sleep(500); // Simulate some processing time
            System.out.println("Server " + name + " completed: " + request);
        } catch (InterruptedException e) {
            System.out.println("Server " + name + " was interrupted while processing: " + request);
        }
    }
}
