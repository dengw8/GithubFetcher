package com.example.duang1996.githubfetcher.main.model;

import java.util.Map;

/**
 * Created by duang1996 on 2018/4/17.
 */

public interface GetUserInfoCallback {
    void GetSuccess(Map<String, Object> tem);
    void GetComplete();
    void GetFailed();
}
