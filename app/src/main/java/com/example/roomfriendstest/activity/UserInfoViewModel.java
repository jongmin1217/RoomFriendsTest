package com.example.roomfriendstest.activity;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.roomfriendstest.model.UserData;
import com.example.roomfriendstest.recyclerview.UserAdapter;
import com.example.roomfriendstest.retrofit.NetRetrofit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends BaseObservable {
    private ArrayList<UserData> items;
    private UserAdapter adapter;


    public UserInfoViewModel(){
        if(items==null){
            items = new ArrayList<>();
        }
        if(adapter==null){
            adapter = new UserAdapter(this);

        }
    }

    public void getUserInfo(String search, final int page){
        Call<ResponseBody> res = NetRetrofit.getInstance().getService().userInfo(search,page);
        res.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {
                        String result = response.body().string();
                        JSONObject userInfoObject = new JSONObject(result);
                        JSONArray jsonArray = userInfoObject.getJSONArray("items");

                        if (jsonArray.length() != 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userListObject = jsonArray.getJSONObject(i);
                                UserData data = new UserData(userListObject.getString("avatar_url"),
                                        userListObject.getString("login"),
                                        userListObject.getString("score"));

                                //Log.d("qweqwe",userListObject.getString("avatar_url"));
                                items.add(data);
                            }
                            adapter.set();
                        }
                    }else{
                        //view.responseError();
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

    public void onCreate(){
        adapter.notifyDataSetChanged();
    }

    public void onResume(){

    }

    public UserAdapter getAdapter(){
        return adapter;
    }

    public ArrayList<UserData> getItems(){
        return items;
    }

    public String getProfileImage(int pos){
        return items.get(pos).getProfile();
    }

    public String getUserName(int pos){
        return items.get(pos).getUserName();
    }

    public String getScore(int pos){
        return items.get(pos).getScore();
    }

    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView imageView, String url){
        Log.d("qweqweqwe","되는거니  "+url);
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
