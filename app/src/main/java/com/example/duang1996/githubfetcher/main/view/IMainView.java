package com.example.duang1996.githubfetcher.main.view;


/**
 * Created by duang1996 on 2018/4/17.
 */

public interface IMainView{
    void showProcessBar();
    void hideProcessBar();
    void toRepoActivity(String userName);
    void notifyItemInserted(int i);
}
