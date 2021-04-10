package com.example.httpclients;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BasicClients {


    public static void main(String[] args) throws IOException, InterruptedException {

       // httpUrlConnection();
      //  httpUrlConnectionMapper();

        //     newHttpClient();
        //  newAsynchClient();




    }




    private static void newAsynchClient() {

        HttpClient client = HttpClient
                .newBuilder()
                .build();


        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.github.com/users/KrzysztofKmiecik"))
                .build();


        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        System.err.println(throwable.getMessage());
                        throwable.printStackTrace();
                    } else {
                        System.out.println("Code: " + response.statusCode());
                        System.out.println("Body: " + response.body());
                    }
                });

        future.join();


    }

    private static void newHttpClient() throws IOException, InterruptedException {

        HttpClient client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.github.com/users/KrzysztofKmiecik"))
                .timeout(Duration.ofSeconds(120))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());


    }

    private static void httpUrlConnection() throws IOException {

        URL url = new URL("https://api.github.com/users/KrzysztofKmiecik");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        System.out.println(connection.getResponseCode());


        InputStream stream = connection.getInputStream();


        String responseBody = new BufferedReader(new InputStreamReader(stream))
                .lines()
                .collect(Collectors.joining("/"));


        System.out.println(responseBody);


    }

    private static void httpUrlConnectionMapper() throws IOException {

        URL url = new URL("https://api.github.com/users/KrzysztofKmiecik");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        InputStream stream = connection.getInputStream();


        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"name\":\"hello\", \"age\":\"37\"}";



        //  Map<String,String> map = mapper.readValue(stream, Map.class);
        //   Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

        //  System.out.println(map);


        //https://www.jsonschema2pojo.org
        MyGitHub myGitHub = mapper.readValue(stream, MyGitHub.class);

        System.out.println(myGitHub.getLogin());


    }



}
