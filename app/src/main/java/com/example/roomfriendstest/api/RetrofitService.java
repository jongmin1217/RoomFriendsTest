package com.example.roomfriendstest.api;

import com.example.roomfriendstest.data.OrganizationsData;
import com.example.roomfriendstest.data.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitService {

    @GET("search/users")
    Call<UserData> userInfo(
            @Query("q") String q,
            @Query("page") int page);

    @GET
    Call<ArrayList<OrganizationsData>> organizationsInfo(@Url String url);
}
