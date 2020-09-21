package com.example.roomfriendstest.view;

import android.util.Log;
import android.view.View;
import com.example.roomfriendstest.data.UserData.UserList;
import com.example.roomfriendstest.data.UserData;
import com.example.roomfriendstest.api.NetRetrofit;
import java.util.ArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserList>> users;
    public LiveData<ArrayList<UserList>> getUsers(){
        if (users == null) {
            users = new MutableLiveData<>();
            ArrayList<UserList> qwe = new ArrayList<>();
            users.setValue(qwe);
        }
        return users;
    }

    public void getUserInfo(String search, final int page){
        Call<UserData> res = NetRetrofit.getInstance().getService().userInfo(search,page);
        res.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()){
                    UserData resource = response.body();
                    users.setValue(resource.items);
                }
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onClick(View view){
        getUserInfo("a",1);
    }

}
