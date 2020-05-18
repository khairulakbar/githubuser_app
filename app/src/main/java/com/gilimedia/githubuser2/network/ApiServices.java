package com.gilimedia.githubuser2.network;

import com.gilimedia.githubuser2.response.ResponseDetailUser;
import com.gilimedia.githubuser2.response.ResponseFollowers;
import com.gilimedia.githubuser2.response.ResponseFollowing;
import com.gilimedia.githubuser2.response.ResponseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiServices {

    //@Headers("Authorization : token <d7ee02a878e4b23fd3e8f907c91cfa76f9059bef>")
    @GET("search/users")
    Call<ResponseUser> req_show_user(@Query("q") String username);

    @GET("users/{username}")
    Call<ResponseDetailUser> req_detail_user(@Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<ResponseFollowers>> req_followers(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<ResponseFollowing>> req_following(@Path("username") String username);
}
