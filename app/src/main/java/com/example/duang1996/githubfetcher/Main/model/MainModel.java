package com.example.duang1996.githubfetcher.Main.model;

import com.example.duang1996.githubfetcher.Bean.User;
import com.example.duang1996.githubfetcher.Factory.ServiceFactory;
import com.example.duang1996.githubfetcher.Service.GitHubService;

import java.util.LinkedHashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duang1996 on 2018/4/17.
 */

public class MainModel implements IMainModel{
    private GitHubService gitHubService = new ServiceFactory().getGitHubService();;
    private Subscription subscription;
    private Subscriber<User> subscriber;

    @Override
    public void getUserInfo(String userName, final GetUserInfoCallback getUserInfoCallback) {
        subscriber = new Subscriber<User>() {
            @Override
            public void onCompleted() {
                getUserInfoCallback.GetComplete();
            }
            @Override
            public void onError(Throwable e) {
                getUserInfoCallback.GetFailed();
            }
            @Override
            public void onNext(User user) {
                Map<String, Object> tem = new LinkedHashMap<>();
                tem.put("name", user.getLogin());
                tem.put("id", "id: " + user.getId());
                tem.put("blog", "blog: " + user.getBlog());
                getUserInfoCallback.GetSuccess(tem);
            }
        };
        subscription = gitHubService.getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    @Override
    public void destorySubscription() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}