package com.example.duang1996.githubfetcher.activity.userInfo.model;

/**
 * Created by duang1996 on 2018/4/17.
 */

public interface IReposModel {
    void getRepos(String userName, GetReposCallback getReposCallback);
    void destorySubscription();
}
