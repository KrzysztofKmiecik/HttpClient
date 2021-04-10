package com.example.httpclients;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ObjectInputStream;


public class SpringClients {

    public static void main(String[] args) {
      //  restTemplate();
        webClient();

    }

    private static void webClient() {

        WebClient client = WebClient
                .builder()
                .baseUrl("https://api.github.com/users")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


        WebClient.RequestHeadersSpec<?> requestHeadersSpec = client.get().uri("/KrzysztofKmiecik");
        Mono<String> mono = requestHeadersSpec.retrieve().bodyToMono(String.class);
        mono
                .doOnNext(body-> System.out.println(body))
                .subscribe();


    }

    private static void restTemplate() {

        RestTemplate template =new RestTemplateBuilder()
                .rootUri("https://api.github.com/users")
           //     .basicAuthentication("user","1234")

                .build();

        ResponseEntity<String> response = template.getForEntity("/KrzysztofKmiecik",String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());


        ResponseEntity<MyGitHub> myGitHubResponseEntity = template.getForEntity("/KrzysztofKmiecik", MyGitHub.class);
        System.out.println(myGitHubResponseEntity.getBody());
        System.out.println(myGitHubResponseEntity.getBody().getLogin());

        MyGitHub myGitHub = myGitHubResponseEntity.getBody();


    }

}
