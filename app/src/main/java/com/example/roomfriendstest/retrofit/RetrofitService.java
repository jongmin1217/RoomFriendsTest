package com.example.roomfriendstest.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("users")
    Call<ResponseBody> userInfo(
            @Query("q") String q,
            @Query("page") int page);

    @GET("orgs")
    Call<ResponseBody> orgInfo();
}
