package com.example.httpclients;

import lombok.Data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class RetrofitClient {

    public static void main(String[] args) throws IOException {


        // retrofit();
        retrifitMapper();


    }

    private static void retrifitMapper() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubListService gitHubListService = retrofit.create(GitHubListService.class);
        Call<List<Repository>> call = gitHubListService.listRepos("KrzysztofKmiecik");
        Response<List<Repository>> response = call.execute();
        System.out.println(response.code());
        System.out.println(response.body());

    }

    private static void retrofit() throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<ResponseBody> call = gitHubService.listRepos("KrzysztofKmiecik");
        Response<ResponseBody> response = call.execute();
        System.out.println(response.code());
        System.out.println(response.body().string());

    }


    interface GitHubListService {
        @GET("users/{user}/repos")
        Call<List<Repository>> listRepos(@Path("user") String user);

    }


    interface GitHubService {
        @GET("users/{user}/repos")
        Call<ResponseBody> listRepos(@Path("user") String user);


    }


    @Data
    class Repository {

        String id;
        String name;
        String fullName;
        String htmlUrl;
        String description;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

}
