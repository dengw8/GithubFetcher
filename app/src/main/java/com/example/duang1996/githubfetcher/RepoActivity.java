package com.example.duang1996.githubfetcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.duang1996.githubfetcher.Factory.ServiceFactory;
import com.example.duang1996.githubfetcher.Model.Repo;
import com.example.duang1996.githubfetcher.Service.GitHubService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RepoActivity extends AppCompatActivity {
    private ListView reposView;
    private ProgressBar progressBar;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> reposLists = new ArrayList<>();

    private GitHubService gitHubService;

    private Subscription subscription;

    private Subscriber<List<Repo>> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        gitHubService = new ServiceFactory().getGitHubService();
        String login = getIntent().getStringExtra("login");
        Log.d("Mydebug", "username");

        subscriber = new Subscriber<List<Repo>>() {

            @Override
            public void onCompleted() {
                Log.d("Mydebug", "Repo onCompleted");
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Mydebug", "Repo onError");
            }

            @Override
            public void onNext(List<Repo> reposList) {
                Log.d("Mydebug", "Repo onNext ");
                for (int i = 0; i < reposList.size(); i++) {
                    Map<String, Object> tem = new LinkedHashMap<>();
                    tem.put("name", reposList.get(i).getName());
                    tem.put("type", reposList.get(i).getLanguage());
                    tem.put("des", reposList.get(i).getDescription());
                    reposLists.add(tem);
                }
                reposView = findViewById(R.id.repoList);
                adapter = new SimpleAdapter(RepoActivity.this, reposLists, R.layout.repo_item,
                        new String[]{"name", "type", "des"}, new int[]{R.id.name, R.id.type, R.id.description});
                reposView.setAdapter(adapter);
            }
        };

        subscription = gitHubService.getRepos(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }
}
