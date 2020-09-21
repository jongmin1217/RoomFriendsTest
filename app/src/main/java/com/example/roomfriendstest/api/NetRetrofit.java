package com.example.roomfriendstest.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit<T> {

//    private T service;
//    private String baseUrl = "https://api.github.com/search/";
//
//    public T getClient(Class<? extends T> type){
//        if(service == null){
//            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//
//                    Request original = chain.request();
//                    Request request = original.newBuilder().
//                            header("ex-hader","sample").
//                            method(original.method(),original.body()).
//                            build();
//
//                    return chain.proceed(request);
//                }
//            }).build();
//
//            Retrofit client = new Retrofit.Builder().
//                    baseUrl(baseUrl).client(okHttpClient).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    build();
//            service = client.create(type);
//        }
//        return service;
//    }

    private static NetRetrofit ourInstance = new NetRetrofit();
    public static NetRetrofit getInstance() {
        return ourInstance;
    }
    private NetRetrofit() {
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .build();

    RetrofitService service = retrofit.create(RetrofitService.class);

    public RetrofitService getService() {
        return service;
    }
}
