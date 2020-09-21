package com.example.roomfriendstest.api;

import com.example.roomfriendstest.data.UserData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("users")
    Call<UserData> userInfo(
            @Query("q") String q,
            @Query("page") int page);

}
