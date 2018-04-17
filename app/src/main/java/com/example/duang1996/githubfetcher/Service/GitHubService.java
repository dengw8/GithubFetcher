package com.example.duang1996.githubfetcher.Service;

import com.example.duang1996.githubfetcher.Bean.Repo;
import com.example.duang1996.githubfetcher.Bean.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by duang1996 on 2017/12/12.
 */

public interface GitHubService {
    /*
     *获取特定用户的接口
     */
    //@GET("users/{user}")
    @HTTP(method="GET", path="users/{user}", hasBody = false)
    Observable<User> getUser(@Path("user") String User);

    /*
     *获取当前用户的项目列表的接口
     */
    @GET("users/{user}/repos")
    Observable<List<Repo>> getRepos(@Path("user") String User);  //Observable 被观察者

}
