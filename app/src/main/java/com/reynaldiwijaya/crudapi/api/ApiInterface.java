package com.reynaldiwijaya.crudapi.api;

import com.reynaldiwijaya.crudapi.model.User.UserResponse;
import com.reynaldiwijaya.crudapi.model.detail.ResponseDetailUser;
import com.reynaldiwijaya.crudapi.model.login.LoginBody;
import com.reynaldiwijaya.crudapi.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // Membuat Login
    @POST("/api/login")
    Call<LoginResponse> postLogin(@Body LoginBody loginBody);

    // Merequest get data user
    @GET("/api/users")
    Call<UserResponse> getUser(
            @Query("per_page") int perPage
    );

    @GET("/api/users/{id}")
    Call<ResponseDetailUser> getDetailUser(@Path("id") int id);
}
