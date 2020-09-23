package com.example.roomfriendstest.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.roomfriendstest.data.OrganizationsData;
import com.example.roomfriendstest.data.UserData;
import com.example.roomfriendstest.databinding.RecyclerviewOrganizationBinding;
import com.example.roomfriendstest.databinding.RecyclerviewUserinfoBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.ItemViewHolder> {
    private ArrayList<OrganizationsData> users = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewOrganizationBinding binding = RecyclerviewOrganizationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    public void setItem(ArrayList<OrganizationsData> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewOrganizationBinding binding;

        public ItemViewHolder(RecyclerviewOrganizationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void onBind(OrganizationsData userData) {
            binding.setUserdata(userData);
            binding.executePendingBindings();
        }
    }

}
