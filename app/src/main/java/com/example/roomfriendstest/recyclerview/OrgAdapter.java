package com.example.roomfriendstest.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roomfriendstest.R;
import com.example.roomfriendstest.activity.MainView;
import com.example.roomfriendstest.model.OrgData;
import com.example.roomfriendstest.model.UserData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ItemViewHolder>  {
    private ArrayList<OrgData> listData = new ArrayList<>();
    private Context context;

    public OrgAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_organization, parent, false);
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

    public void getItem(ArrayList<OrgData> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.profileImage)
        ImageView profileImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @SuppressLint("SetTextI18n")
        public void onBind(OrgData orgData) {

            Glide.with(context).load(orgData.getProfileImage()).into(profileImage);
        }
    }
}
