package com.example.lab03;

import java.net.http.*;
import java.net.URI;
import java.util.concurrent.*;

public class ConcurrentAPIClient {
    public static void main(String[] args) throws Exception {
        int requestCount = Integer.parseInt(args[0]); // e.g. passed as a CLI parameter
        ExecutorService executor = Executors.newFixedThreadPool(requestCount);
        HttpClient client = HttpClient.newHttpClient();

        for (int i = 0; i < requestCount; i++) {
            executor.submit(() -> {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/api/items"))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("Response: " + response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
