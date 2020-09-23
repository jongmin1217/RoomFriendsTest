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
    public ArrayList<UserList> users = new ArrayList<>();
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
            listener.scroll(0);
        }else{
            this.users.addAll(users);
            notifyItemRangeInserted(getItemCount()-users.size(),getItemCount());
            listener.scroll(getItemCount()-users.size());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private RecyclerviewUserinfoBinding binding;
        private OrganizationsAdapter adapter;

        public ItemViewHolder(RecyclerviewUserinfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void onBind(UserList userData) {
            binding.setUserdata(userData);
            binding.executePendingBindings();

            adapter = new OrganizationsAdapter();
            binding.organizationRecyclerview.setAdapter(adapter);

            if(!userData.getOrgVisible()){
                binding.organizationRecyclerview.setVisibility(View.GONE);
            }else{
                binding.organizationRecyclerview.setVisibility(View.VISIBLE);
                adapter.setItem(userData.getUsers());
            }
            binding.layout.setOnClickListener(view -> {
                if(binding.organizationRecyclerview.getVisibility()==View.GONE){
                    if(userData.getUsers()==null){
                        listener.onItemClick(userData.getLogin(),adapter,getAdapterPosition());
                    }else adapter.setItem(userData.getUsers());
                    binding.organizationRecyclerview.setVisibility(View.VISIBLE);
                }else {
                    binding.organizationRecyclerview.setVisibility(View.GONE);
                    userData.setOrgVisible(false);
                }
            });
        }
    }
}
