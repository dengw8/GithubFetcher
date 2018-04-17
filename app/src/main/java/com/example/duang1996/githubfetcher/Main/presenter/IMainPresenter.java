package com.example.duang1996.githubfetcher.Main.presenter;

import java.util.List;
import java.util.Map;

/**
 * Created by duang1996 on 2018/4/17.
 */

public interface IMainPresenter {
    void getUserInfo(String str);
    void setClick(int i);
    void setLongClick(int i);
    void clear();
    void destorySubscription();
    List<Map<String, Object>> getUserList();

}
