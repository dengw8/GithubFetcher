package com.example.duang1996.githubfetcher.userInfo.model;

import com.example.duang1996.githubfetcher.bean.Repo;
import com.example.duang1996.githubfetcher.factory.ServiceFactory;
import com.example.duang1996.githubfetcher.service.GitHubService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duang1996 on 2018/4/17.
 */

public class ReposModel implements IReposModel {
    private GitHubService gitHubService = new ServiceFactory().getGitHubService();;
    private Subscription subscription;
    private Subscriber<List<Repo>> subscriber;
    @Override
    public void getRepos(String userName, final GetReposCallback getReposCallback) {
        subscriber = new Subscriber<List<Repo>>() {
            @Override
            public void onCompleted() {
                getReposCallback.GetComplete();
            }

            @Override
            public void onError(Throwable e) {
                getReposCallback.GetFailed();
            }

            @Override
            public void onNext(List<Repo> reposList) {
                List<Map<String, Object>> list = new ArrayList<>();
                for (int i = 0; i < reposList.size(); i++) {
                    Map<String, Object> tem = new LinkedHashMap<>();
                    tem.put("name", reposList.get(i).getName());
                    tem.put("type", reposList.get(i).getLanguage());
                    tem.put("des", reposList.get(i).getDescription());
                    list.add(tem);
                }
                getReposCallback.GetSuccess(list);
            }
        };
        subscription = gitHubService.getRepos(userName)
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
