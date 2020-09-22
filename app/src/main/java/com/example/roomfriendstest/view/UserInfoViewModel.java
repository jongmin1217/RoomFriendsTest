package com.example.roomfriendstest.view;

import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.example.roomfriendstest.data.OrganizationsData;
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
    private MutableLiveData<ArrayList<OrganizationsData>> organizationsUsers;
    public MutableLiveData<Event<Boolean>> showErrorToast = new MutableLiveData<>();

    private Call<UserData> userRes;
    private Call<ArrayList<OrganizationsData>> orgRes;

    public LiveData<ArrayList<UserList>> getUsers() {
        if (users == null)  users = new MutableLiveData<>();
        return users;
    }

    public LiveData<ArrayList<OrganizationsData>> getOrganizationsUsers() {
        if (organizationsUsers == null) organizationsUsers = new MutableLiveData<>();
        return organizationsUsers;
    }

    public void getUserInfo(String search, int page) {
        if(userRes != null) userRes.cancel();
        userRes = NetRetrofit.getInstance().getService().userInfo(search, page);
        userRes.enqueue(new Callback<UserData>() {
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

    public void getOrganizations(String login){
        String url = "users/"+login+"/orgs";
        Log.d("qweqwe",url);
        if(orgRes != null) orgRes.cancel();
        orgRes = NetRetrofit.getInstance().getService().organizationsInfo(url);
        orgRes.enqueue(new Callback<ArrayList<OrganizationsData>>() {
            @Override
            public void onResponse(Call<ArrayList<OrganizationsData>> call, Response<ArrayList<OrganizationsData>> response) {
                if (response.isSuccessful()) {
                    ArrayList<OrganizationsData> resource = response.body();
                    organizationsUsers.setValue(resource);
                }else{
                    showErrorToast.setValue(new Event(true));
                    Log.d("qweqwe","failed");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<OrganizationsData>> call, Throwable t) {
                t.printStackTrace();
                Log.d("qweqwe","error "+t);
            }
        });
    }

}
