package com.example.roomfriendstest.model;

public class UserData {
    private String profile;
    private String userName;
    private String score;

    public UserData(String profile,String userName,String score){
        this.profile = profile;
        this.userName = userName;
        this.score = score;
    }

    public String getProfile() {
        return profile;
    }

    public String getUserName() {
        return userName;
    }

    public String getScore() {
        return score;
    }

}
