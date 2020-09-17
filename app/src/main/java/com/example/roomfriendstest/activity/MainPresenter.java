package com.example.roomfriendstest.activity;

import android.util.Log;
import android.widget.Toast;

import com.example.roomfriendstest.model.UserData;
import com.example.roomfriendstest.recyclerview.UserAdapter;
import com.example.roomfriendstest.retrofit.NetRetrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView view;
    private UserData data;
    private ArrayList<UserData> dataList;
    private UserAdapter adapter;

    public MainPresenter(MainView view, UserAdapter adapter){
        this.view = view;
        this.adapter = adapter;
    }

    public void userInfo(String search, final int page){
        dataList = new ArrayList<>();
        Call<ResponseBody> res = NetRetrofit.getInstance().getService().userInfo(search,page);
        res.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {
                        String result = response.body().string();
                        JSONObject myChatroomObject = new JSONObject(result);
                        JSONArray jsonArray = myChatroomObject.getJSONArray("items");

                        if (jsonArray.length() != 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userListObject = jsonArray.getJSONObject(i);
                                data = new UserData();
                                data.setUserName(userListObject.getString("login"));
                                data.setProfile(userListObject.getString("avatar_url"));
                                data.setScore(userListObject.getString("score"));
                                data.setOrganizationsUrl(userListObject.getString("organizations_url"));

                                Log.d("scroll",userListObject.getString("login"));
                                dataList.add(data);
                            }
                            if(page==1){
                                adapter.getItem(dataList);
                                view.screenVisible();
                            }else{
                                adapter.addItem(dataList);
                            }
                        }else{
                            if(page==1){
                                adapter.removeItem();
                                view.noResult();
                            }
                        }
                    }else{
                        view.responseError();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Err", t.getMessage());
            }
        });
    }

    public void removeItem(){
        adapter.removeItem();
    }
}
