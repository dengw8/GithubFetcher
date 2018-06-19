package com.example.duang1996.githubfetcher.activity.userInfo.model;

import java.util.List;
import java.util.Map;


/**
 * Created by duang1996 on 2018/4/17.
 */

public interface GetReposCallback {
    void GetSuccess(List<Map<String, Object>> reposList);
    void GetComplete();
    void GetFailed();
}
