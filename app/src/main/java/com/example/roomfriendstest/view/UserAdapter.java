package com.example.roomfriendstest.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roomfriendstest.data.UserData.UserList;
import com.example.roomfriendstest.databinding.RecyclerviewUserinfoBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {
    private ArrayList<UserList> users = new ArrayList<>();
    private Listener listener;

    public UserAdapter(Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewUserinfoBinding binding = RecyclerviewUserinfoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        (holder).onBind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setItem(ArrayList<UserList> users, int pageNum){
        if(pageNum==1){
            this.users = users;
            notifyDataSetChanged();
        }else{
            this.users.addAll(users);
            notifyItemRangeInserted(getItemCount()-users.size(),getItemCount());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private RecyclerviewUserinfoBinding binding;

        public ItemViewHolder(RecyclerviewUserinfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void onBind(UserList userData) {
            binding.setUserdata(userData);
            binding.executePendingBindings();

            OrganizationsAdapter adapter = new OrganizationsAdapter();
            binding.organizationRecyclerview.setAdapter(adapter);

            binding.profileImage.setOnClickListener(view -> {
                if(binding.organizationRecyclerview.getVisibility()!=View.VISIBLE){
                    listener.onItemClick(userData.getLogin(),adapter);
                    binding.organizationRecyclerview.setVisibility(View.VISIBLE);
                }else binding.organizationRecyclerview.setVisibility(View.GONE);
            });
        }
    }

}
