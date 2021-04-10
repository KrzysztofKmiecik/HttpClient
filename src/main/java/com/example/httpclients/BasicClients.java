package com.example.httpclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Collectors;


public class BasicClients {

    public static void main(String[] args) throws IOException, InterruptedException {

        // httpUrlConnection();

        newHttpClient();

    }

    private static void newHttpClient() throws IOException, InterruptedException {

        HttpClient client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request=HttpRequest
                .newBuilder(URI.create("https://api.github.com/users/KrzysztofKmiecik"))
                .timeout(Duration.ofSeconds(120))
                .header("accept","application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    private static void httpUrlConnection() throws IOException {
        URL url = new URL("https://api.github.com/users/KrzysztofKmiecik");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");

        System.out.println(connection.getResponseCode());

        InputStream stream = connection.getInputStream();

        String requestBody = new BufferedReader(new InputStreamReader(stream))
                .lines()
                .collect(Collectors.joining("/n"));


        System.out.println(requestBody);

    }


}
