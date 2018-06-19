package com.example.duang1996.githubfetcher.userInfo.presenter;

import com.example.duang1996.githubfetcher.eventBus.event.MessageEvent;

import com.example.duang1996.githubfetcher.userInfo.model.GetReposCallback;
import com.example.duang1996.githubfetcher.userInfo.model.IReposModel;
import com.example.duang1996.githubfetcher.userInfo.model.ReposModel;
import com.example.duang1996.githubfetcher.userInfo.view.IReposView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duang1996 on 2018/4/17.
 */

public class ReposPresenterCompl implements IReposPresenter{
    private IReposModel iReposModel;
    private IReposView iReposView;
    private List<Map<String, Object>> reposList = new ArrayList<>();

    public ReposPresenterCompl() {
        this.iReposModel = new ReposModel();
    }
    public void attachView(IReposView iReposView) {
        this.iReposView = iReposView;
    }

    public void detachView() {
        this.iReposView = null;
    }

    private boolean isViewAttached(){
        return iReposView != null;
    }
    @Override
    public void getRepos(String userName) {
        if(isViewAttached()) {
            iReposView.showProcessBar();
            iReposModel.getRepos(userName, new GetReposCallback() {
                @Override
                public void GetSuccess(List<Map<String, Object>> list) {
                    reposList = list;
                    EventBus.getDefault().post(new MessageEvent());
                }
                @Override
                public void GetComplete() {
                    iReposView.hideProcessBar();
                }
                @Override
                public void GetFailed() {

                }
            });
        }
    }
    @Override
    public List<Map<String, Object>> getReposList() {
        if(isViewAttached()) {
            return reposList;
        }
        return null;
    }
    @Override
    public void destorySubscription() {
        if(isViewAttached()) {
            iReposModel.destorySubscription();
        }
    }
}