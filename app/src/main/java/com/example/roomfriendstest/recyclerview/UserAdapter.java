package com.example.roomfriendstest.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roomfriendstest.R;
import com.example.roomfriendstest.activity.MainView;
import com.example.roomfriendstest.model.UserData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {

    private Context context;
    private MainView view;
    ArrayList<UserData> listData = new ArrayList<>();

    public UserAdapter(Context context, MainView view){
        this.context = context;
        this.view = view;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_userinfo, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void removeItem(){
        listData.clear();
        notifyDataSetChanged();
    }

    public void getItem(ArrayList<UserData> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private UserData userData;

        @BindView(R.id.profileImage)
        ImageView profileImage;
        @BindView(R.id.login)
        TextView login;
        @BindView(R.id.score)
        TextView score;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @SuppressLint("SetTextI18n")
        public void onBind(UserData userData) {
            this.userData = userData;

            Glide.with(context).load(userData.getProfile()).into(profileImage);
            login.setText(userData.getUserName());
            score.setText(userData.getScore());
        }
        @Override
        public void onClick(View view) {

        }
    }
}
