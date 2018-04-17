package com.example.duang1996.githubfetcher.Main.model;

/**
 * Created by duang1996 on 2018/4/17.
 */

public interface IMainModel {
    void getUserInfo(String userName, GetUserInfoCallback getUserInfoCallback);
    void destorySubscription();
}
