package com.cpe.fire.domain.gps;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class HttpPost {

    public static void sendJsonPost(String data){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://host.docker.internal:5000/capteurs"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
