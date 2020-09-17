package com.example.roomfriendstest.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roomfriendstest.R;
import com.example.roomfriendstest.activity.MainView;
import com.example.roomfriendstest.activity.UserInfoViewModel;
import com.example.roomfriendstest.databinding.RecyclerviewUserinfoBinding;
import com.example.roomfriendstest.model.OrgData;
import com.example.roomfriendstest.model.UserData;
import com.example.roomfriendstest.retrofit.NetRetrofit;
import com.example.roomfriendstest.retrofit.RetrofitService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {
    private UserInfoViewModel viewModel;

    ArrayList<UserData> listData = new ArrayList<>();

    public UserAdapter(UserInfoViewModel viewModel){
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewUserinfoBinding binding = RecyclerviewUserinfoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        (holder).onBind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return viewModel.getItems() == null ? 0 : viewModel.getItems().size();
    }

    public void removeItem(){
        listData.clear();
        notifyDataSetChanged();
    }

    public void set(){
        notifyDataSetChanged();
    }

    public void addItem(ArrayList<UserData> listData){
        this.listData.addAll(listData);
        notifyItemRangeInserted(getItemCount(),listData.size());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{
        private RecyclerviewUserinfoBinding binding;


        public ItemViewHolder(RecyclerviewUserinfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            //init();
        }

        @SuppressLint("SetTextI18n")
        public void onBind(UserInfoViewModel viewModel, int pos) {
            binding.setViewmodel(viewModel);
            binding.setPos(pos);
            binding.executePendingBindings();

        }
//        @Override
//        public void onClick(View view) {
//            dataList = new ArrayList<>();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("https://api.github.com/users/"+userData.getUserName()+"/")
//                    .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
//                    .build();
//
//            RetrofitService service = retrofit.create(RetrofitService.class);
//
//            Call<ResponseBody> res = service.orgInfo();
//            res.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    try {
//                        if (response.body() != null) {
//                            String result = response.body().string();
//                            JsonParser jsonParser = new JsonParser();
//                            JsonArray jsonArray = (JsonArray) jsonParser.parse(result);
//
//
//                            Log.d("qweqwe","11  "+jsonArray.size());
//                            if (jsonArray.size() != 0) {
//                                for (int i = 0; i < jsonArray.size(); i++) {
//                                    JsonObject object = (JsonObject) jsonArray.get(i);
//
//                                    data = new OrgData();
//                                    data.setProfileImage(object.get("avatar_url").getAsString());
//                                    Log.d("qweqwe","aa  "+object.get("avatar_url").getAsString());
//                                    dataList.add(data);
//                                }
//                                adapter.getItem(dataList);
//                                organizationRecyclerview.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    } catch (IOException  e) {
//                        e.printStackTrace();
//                    }
//                }
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e("Err", t.getMessage());
//                }
//            });
//        }

//        private void init(){
//            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            organizationRecyclerview.setLayoutManager(mLayoutManager);
//            adapter = new OrgAdapter(context);
//            organizationRecyclerview.setAdapter(adapter);
//        }

    }
}
