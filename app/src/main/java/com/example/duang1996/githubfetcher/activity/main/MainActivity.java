package com.example.duang1996.githubfetcher.activity.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.duang1996.githubfetcher.adapter.CardAdapter;

import com.example.duang1996.githubfetcher.activity.main.presenter.MainPresenterCompl;
import com.example.duang1996.githubfetcher.activity.main.view.IMainView;
import com.example.duang1996.githubfetcher.R;
import com.example.duang1996.githubfetcher.activity.userInfo.RepoActivity;


public class MainActivity extends AppCompatActivity implements IMainView{
    private EditText input;
    private Button clearBtn;
    private Button fetchBtn;
    private ProgressBar progressBar;

    private RecyclerView usersView;
    private CardAdapter cardAdapter;

    private MainPresenterCompl mainPresenterCompl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         *申请网络权限
         */
        connectNetwork();
        /*
         *初始化元素
         */
        initViews();
    }

    private void initViews() {
        input = findViewById(R.id.input);
        clearBtn = findViewById(R.id.clearBtn);
        fetchBtn = findViewById(R.id.fetchBtn);
        progressBar = findViewById(R.id.progressBar);
        usersView = findViewById(R.id.userList);
        usersView.setLayoutManager(new LinearLayoutManager(this));

        mainPresenterCompl = new MainPresenterCompl();
        mainPresenterCompl.attachView(this);

        cardAdapter = new CardAdapter(this, mainPresenterCompl.getUserList());
        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                mainPresenterCompl.setClick(i);
            }
        });

        cardAdapter.setOnItemLongClickListener(new CardAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int i) {
                mainPresenterCompl.setLongClick(i);
                cardAdapter.notifyItemRemoved(i);
            }
        });
        usersView.setAdapter(cardAdapter);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = mainPresenterCompl.getUserList().size();
                mainPresenterCompl.clear();
                cardAdapter.notifyItemRangeRemoved(0, size);
            }
        });

        fetchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputStr = input.getText().toString().trim();
                if (inputStr.equals("")) return;
                int size = mainPresenterCompl.getUserList().size();
                mainPresenterCompl.getUserInfo(inputStr);
            }
        });
    }

    //检查网络连接
    private void connectNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if ((networkInfo == null) || !networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "当前网络未连接", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "当前网络已连接", Toast.LENGTH_SHORT).show();
        }
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
    public void toRepoActivity(String userName){
        Intent intent = new Intent(MainActivity.this, RepoActivity.class);
        intent.putExtra("login", userName);
        startActivity(intent);
    }
    @Override
    public void notifyItemInserted(int i) {
        cardAdapter.notifyItemInserted(i);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenterCompl.destorySubscription();
        mainPresenterCompl.detachView();
    }
}