package com.example.duang1996.githubfetcher.Main.presenter;


import com.example.duang1996.githubfetcher.Main.model.GetUserInfoCallback;
import com.example.duang1996.githubfetcher.Main.model.IMainModel;
import com.example.duang1996.githubfetcher.Main.model.MainModel;
import com.example.duang1996.githubfetcher.Main.view.IMainView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by duang1996 on 2018/4/17.
 */

//view 与model之间的桥梁
public class MainPresenterCompl implements IMainPresenter{
    private IMainModel iMainModel;  //
    private IMainView iMainView;  // 调用Activity中重载IMainView的方法

    private List<Map<String, Object>> userList = new ArrayList<>();

    public MainPresenterCompl(){
        this.iMainModel = new MainModel();
    }

    public void attachView(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void detachView() {
        this.iMainView= null;
    }

    public boolean isViewAttached(){
        return iMainView != null;
    }

    @Override
    public void getUserInfo(String str) {
        if(isViewAttached()) {
            iMainView.showProcessBar();
            iMainModel.getUserInfo(str, new GetUserInfoCallback() {
                @Override
                public void GetSuccess(Map<String, Object> tem) {
                    userList.add(tem);
                    iMainView.notifyItemInserted(userList.size() - 1);
                }

                @Override
                public void GetComplete() {
                    iMainView.hideProcessBar();
                }

                @Override
                public void GetFailed() {

                }
            });
        }
    }
    @Override
    public void setClick(int i) {
        if(isViewAttached()) {
            Map<String, Object> tem = userList.get(i);
            String login = tem.get("name").toString();
            iMainView.toRepoActivity(login);
        }
    }
    @Override
    public void setLongClick(int i) {
        if(isViewAttached()) {
            userList.remove(i);
        }
    }
    @Override
    public void clear() {
        if(isViewAttached()) {
            userList.clear();
        }
    }
    @Override
    public List<Map<String, Object>> getUserList() {
        if(isViewAttached()) {
            return userList;
        }
        return null;
    }
    @Override
    public void destorySubscription() {
        if(isViewAttached()) {
            iMainModel.destorySubscription();
        }
    }
}
