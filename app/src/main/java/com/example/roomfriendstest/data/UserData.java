package com.example.roomfriendstest.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserData {
    @SerializedName("items")
    public ArrayList<UserList> items;

    public class UserList{
        @SerializedName("avatar_url")
        private String avatar_url;

        @SerializedName("login")
        private String login;

        @SerializedName("score")
        private String score;

        public String getAvatar_url() { return avatar_url; }
        public String getLogin() { return login; }
        public String getScore() { return score; }
    }


}
