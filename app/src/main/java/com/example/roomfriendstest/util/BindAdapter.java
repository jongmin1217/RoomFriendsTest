package com.example.roomfriendstest.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BindAdapter {

    @BindingAdapter("verAdapter")
    public static void verAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
