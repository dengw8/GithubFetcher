package com.example.duang1996.githubfetcher.Service;

import com.example.duang1996.githubfetcher.Model.Repo;
import com.example.duang1996.githubfetcher.Model.User;

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


    /* 被观察者的定义方式，使用 create() 方法来创建一个 Observable
     * 传入了一个 OnSubscribe 对象作为参数，OnSubscribe 会被存储在返回的 Observable 对象中，作用相当于一个计划表
     * 当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发，
     * call()方法中传入的参数是一个观察者Subscriber
     * 被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });
    */
}
