package com.example.paopao.news.request;


import com.example.paopao.news.Callback.OnResponseListener;
import com.example.paopao.news.utils.NetUtils;

public abstract class BaseRequest {
    public abstract String getTypes();



    public abstract Object onResponseParse(String response);

    public void sendRequest(final OnResponseListener responseListener) {
        //http://v.juhe.cn/toutiao/index?type=junshi&key=b4e4872881c15d0f972c336eccab2037
        String url = "http://v.juhe.cn/toutiao/index?type=" + getTypes() + "&key=b4e4872881c15d0f972c336eccab2037";
        NetUtils.asyncRequest(url, new OnResponseListener() {
            @Override
            public void onResponse(Object result) {
                responseListener.onResponse(onResponseParse(result.toString()));
            }
        });
    }
}
