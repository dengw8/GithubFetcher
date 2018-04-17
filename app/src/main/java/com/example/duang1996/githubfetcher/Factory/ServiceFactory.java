package com.example.duang1996.githubfetcher.Factory;

import android.util.Log;

import com.example.duang1996.githubfetcher.Service.GitHubService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duang1996 on 2017/12/12.
 */

public class ServiceFactory {
    private String baseUrl;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private static final int TIMEOUT = 10;

    public ServiceFactory() {
        baseUrl = "https://api.github.com";
        okHttpClient = createOkHttpClient();
        /*
         *创建Retrofit实例
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)   //其实Retrofit已经默认使用OkHttp作为网络请求的客户端了，对OkHttp没有特殊配置不用加这句
                .build();
    }

    /*
     *配置相应的 OkHttpClient 对象
     */
    private static OkHttpClient createOkHttpClient() {
        return  new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
    }

    public GitHubService getGitHubService() {
        /*
         *返回一个 GitHubService 代理对象
         */
        Log.d("Mydebug", "创建成功GitHubService");
        return retrofit.create(GitHubService.class);
    }
}
