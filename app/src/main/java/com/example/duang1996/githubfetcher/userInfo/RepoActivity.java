package com.example.duang1996.githubfetcher.userInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.duang1996.githubfetcher.eventBus.event.MessageEvent;
import com.example.duang1996.githubfetcher.bean.Repo;
import com.example.duang1996.githubfetcher.R;
import com.example.duang1996.githubfetcher.userInfo.presenter.ReposPresenterCompl;
import com.example.duang1996.githubfetcher.userInfo.view.IReposView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import rx.Subscriber;

public class RepoActivity extends AppCompatActivity implements IReposView{
    private ListView reposView;
    private ProgressBar progressBar;
    private SimpleAdapter adapter;
    private ReposPresenterCompl reposPresenterCompl;

    private String userName;

    private Subscriber<List<Repo>> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        initView();

        EventBus.getDefault().register(this);

        reposPresenterCompl.getRepos(userName);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        adapter = new SimpleAdapter(RepoActivity.this, reposPresenterCompl.getReposList(), R.layout.repo_item,
                new String[]{"name", "type", "des"}, new int[]{R.id.name, R.id.type, R.id.description});

        reposView.setAdapter(adapter);
    }

    private void initView() {
        reposView = findViewById(R.id.repoList);
        progressBar = findViewById(R.id.progressBar);
        userName = getIntent().getStringExtra("login");

        reposPresenterCompl = new ReposPresenterCompl();
        reposPresenterCompl.attachView(this);
    }

    @Override
    public void showProcessBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProcessBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        reposPresenterCompl.destorySubscription();
        reposPresenterCompl.detachView();
    }
}
