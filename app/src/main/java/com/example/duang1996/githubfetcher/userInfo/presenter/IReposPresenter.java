package com.example.duang1996.githubfetcher.userInfo.presenter;

import java.util.List;
import java.util.Map;

/**
 * Created by duang1996 on 2018/4/17.
 */

public interface IReposPresenter{
    void getRepos(String userName);
    void destorySubscription();
    List<Map<String, Object>> getReposList();

}
