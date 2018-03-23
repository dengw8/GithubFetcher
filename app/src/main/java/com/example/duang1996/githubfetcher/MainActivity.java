package com.example.duang1996.githubfetcher;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.duang1996.githubfetcher.Adapter.CardAdapter;
import com.example.duang1996.githubfetcher.Factory.ServiceFactory;
import com.example.duang1996.githubfetcher.Model.Repo;
import com.example.duang1996.githubfetcher.Model.User;
import com.example.duang1996.githubfetcher.Service.GitHubService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private Button clearBtn;
    private Button fetchBtn;
    private ProgressBar progressBar;

    private RecyclerView usersView;
    private List<Map<String, Object>> userList = new ArrayList<>();
    private CardAdapter cardAdapter;

    private GitHubService gitHubService;

    private User temUser;

    private Subscription subscription;
    private Subscriber<User> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         *判断当前应用是否可以访问网络
         */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if ((networkInfo == null) || !networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "当前网络未连接", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "当前网络已连接", Toast.LENGTH_SHORT).show();
        }

        /*
         *初始化元素
         */
        initElement();

        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {

                Map<String, Object> tem = userList.get(i);
                String login = tem.get("name").toString();
                Intent intent = new Intent(MainActivity.this, RepoActivity.class);
                intent.putExtra("login", login);
                startActivity(intent);
            }
        });

        cardAdapter.setOnItemLongClickListener(new CardAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int i) {
                userList.remove(i);
                cardAdapter.notifyItemRemoved(i);
            }
        });

        usersView.setAdapter(cardAdapter);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = userList.size();
                userList.clear();
                cardAdapter.notifyItemRangeRemoved(0, size);
            }
        });

        fetchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputStr = input.getText().toString().trim();
                if(inputStr.equals("")) return;
                /*
                 *显示进度条
                 */
                progressBar.setVisibility(View.VISIBLE);

                subscriber = new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Mydebug", "User onCompleted");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Mydebug", "User onError");
                    }

                    @Override
                    public void onNext(User user) {
                        try {
                            Log.d("Mydebug", "User onCompleted");
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Mydebug", "User onNext");
                        Map<String, Object> tem = new LinkedHashMap<>();
                        tem.put("name", user.getLogin());
                        tem.put("id", "id: " + user.getId());
                        tem.put("blog", "blog: " + user.getBlog());
                        userList.add(tem);
                        cardAdapter.notifyItemInserted(userList.size() - 1);
                    }
                };

                subscription = gitHubService.getUser(inputStr)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
            }
        }) ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void initElement() {
        input = findViewById(R.id.input);
        clearBtn = findViewById(R.id.clearBtn);
        fetchBtn = findViewById(R.id.fetchBtn);
        progressBar = findViewById(R.id.progressBar);
        usersView = findViewById(R.id.userList);
        usersView.setLayoutManager(new LinearLayoutManager(this));

        cardAdapter = new CardAdapter(this, userList);

        gitHubService = new ServiceFactory().getGitHubService();
    }
}