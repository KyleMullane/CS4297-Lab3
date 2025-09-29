import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

public class ConcurrentApiClient {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java ConcurrentApiClient <requestCount> <endpointPath>");
            System.out.println("Example: java ConcurrentApiClient 50 rr/api/items");
            return;
        }

        int requestCount = Integer.parseInt(args[0]);
        String endpointPath = args[1]; 
        String fullUri = "http://localhost:8080/" + endpointPath;

        System.out.println("Starting " + requestCount + " concurrent requests to: " + fullUri);

        ExecutorService executor = Executors.newFixedThreadPool(requestCount);
        HttpClient client = HttpClient.newBuilder()
                                      .connectTimeout(Duration.ofSeconds(10))
                                      .build();

        for (int i = 0; i < requestCount; i++) {
            final int requestId = i;
            executor.submit(() -> {
                Instant start = Instant.now();
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(fullUri))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    Instant end = Instant.now();
                    long durationMillis = Duration.between(start, end).toMillis();

                    System.out.printf("Req %d: Status %d, Time %dms%n",
                            requestId, response.statusCode(), durationMillis);
                    
                } catch (Exception e) {
                    Instant end = Instant.now();
                    long durationMillis = Duration.between(start, end).toMillis();
                    System.err.printf("Req %d: FAILED after %dms - %s%n",
                            requestId, durationMillis, e.getMessage());
                }
            });
        }

        executor.shutdown();
        System.out.println("Waiting for all requests to finish...");
        if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
            System.err.println("Some requests did not finish in time.");
        }
        System.out.println("All requests complete.");
    }
}


