package com.australia.administrator.australiandelivery.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


public abstract class HttpUtils {
    private String url;
    private Map<String, String> params;
    private List<FilesBean> files;
    private PostFormBuilder postFormBuilder;

    public HttpUtils(String url, Map<String, String> params) {
        this(url, params, null);

    }

    public HttpUtils(String url, Map<String, String> params, List<FilesBean> files) {
        this.url = url;
        this.params = params;
        this.files = files;
        postFormBuilder = OkHttpUtils.post();
        postFormBuilder.url(url);
    }

    public HttpUtils(String url) {
        this(url, null, null);
    }

    public void clicent() {
        postFormBuilder
                .build().writeTimeOut(60000).readTimeOut(60000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        HttpUtils.this.onError(call, e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HttpUtils.this.onResponse(response, id);
                    }
                });
    }

    public PostFormBuilder addFile(String key, String name, File file) {
        postFormBuilder.addFile(key, name, file);
        return postFormBuilder;
    }

    public PostFormBuilder addParam(String key, String value) {
        postFormBuilder.addParams(key, value);
        return postFormBuilder;
    }


    public abstract void onError(Call call, Exception e, int id);

    public abstract void onResponse(String response, int id);

}
