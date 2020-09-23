package com.example.roomfriendstest.view;

public interface Listener {
    void onItemClick(String login,OrganizationsAdapter organizationsAdapter,int clickPosition);
    void scroll(int position);
}
