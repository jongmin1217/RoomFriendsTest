package com.example.roomfriendstest.view;

import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.example.roomfriendstest.data.UserData.UserList;
import com.example.roomfriendstest.data.UserData;
import com.example.roomfriendstest.api.NetRetrofit;
import com.example.roomfriendstest.util.Event;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserList>> users;
    public MutableLiveData<Event<Boolean>> showErrorToast = new MutableLiveData<>();

    private Call<UserData> res;

    public LiveData<ArrayList<UserList>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
        }
        return users;
    }

    public void getUserInfo(String search, int page) {
        if(res != null) res.cancel();
        res = NetRetrofit.getInstance().getService().userInfo(search, page);
        res.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful()) {
                    UserData resource = response.body();
                    users.setValue(resource.items);
                }else{
                    showErrorToast.setValue(new Event(true));
                    Log.d("qweqwe","failed");
                }
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
