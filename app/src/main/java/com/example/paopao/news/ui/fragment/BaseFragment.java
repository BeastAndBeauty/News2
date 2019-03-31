package com.example.paopao.news.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paopao.news.Callback.OnResponseListener;
import com.example.paopao.news.R;
import com.example.paopao.news.domain.NewsBean;
import com.example.paopao.news.engine.NetWork;
import com.example.paopao.news.ui.activity.WebViewActivity;
import com.example.paopao.news.ui.adapter.NewsListAdapter;
import com.example.paopao.news.utils.NetUtils;
import com.example.paopao.news.utils.RefreshLayout;
import com.google.gson.Gson;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    RefreshLayout refreshLayout;
    ListView listView;
    ImageView ivError;//加载失败显示的图片
    ProgressBar progressBar;//第一次加载时显示的圆形进度条
    NewsListAdapter adapter;//ListView适配器
    List<NewsBean.ResultBean.DataBean> datas;//加载的数据



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_toutiao,container,false);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        listView=view.findViewById(R.id.list_view);
        ivError=view.findViewById(R.id.iv_error);
        progressBar=view.findViewById(R.id.progress_bar);
        initView();
        initData();
        return view;

    }

    private void initData(){
        progressBar.setVisibility(View.VISIBLE);
        ivError.setVisibility(View.GONE);//隐藏错误消息
        refesh();//刷新数据
        sendRequest();

    }

    private void initView(){
        //给ListView设置适配器，从而显示到界面上
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟网络交互
//
                sendRequest();
            }
        });

        //没有更多数据
        refreshLayout.setHasMore(false);
        //加载失败，点击重连
        ivError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        //ListView条目的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean.ResultBean.DataBean dataBean=datas.get(position);
                Intent intent= WebViewActivity.newIntent(getContext(),dataBean.getUrl());
                getActivity().startActivity(intent);
            }
        });
//
    }

    private void refesh(){
        //top对应的是头条新闻
        NetWork.createApi().getNews("b4e4872881c15d0f972c336eccab2037",getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsBean>() {
                    @Override
                    public void call(NewsBean newsBean) {
                        refreshLayout.setEnabled(true);//允许下拉刷新
                        progressBar.setVisibility(View.GONE);//隐藏进度条
                        refreshLayout.setRefreshing(false);//停止下拉刷新
                        datas=newsBean.getResult().getData();
                        adapter=new NewsListAdapter(datas,getContext());
                        listView.setAdapter(adapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ivError.setVisibility(View.VISIBLE);//加载失败显示的错误图片
                        refreshLayout.setEnabled(false);//禁止下拉刷新
                        progressBar.setVisibility(View.GONE);//隐藏进度条
                        refreshLayout.setRefreshing(false);//停止下拉刷新
                        Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    }
                });


    }

    //返回新闻类型，改成抽象方法
    protected abstract String getType();

    public void sendRequest() {
        try {
            //http://v.juhe.cn/toutiao/index?type=junshi&key=b4e4872881c15d0f972c336eccab2037
            String url = "http://v.juhe.cn/toutiao/index?type=" + getType() + "&key=b4e4872881c15d0f972c336eccab2037";
            NetUtils.asyncRequest(url, new OnResponseListener() {
                @Override
                public void onResponse(Object result) {
                    Gson gson = new Gson();
                    NewsBean newsBean = gson.fromJson(result.toString(), NewsBean.class);
                    refreshLayout.setEnabled(true);//允许下拉刷新
                    progressBar.setVisibility(View.GONE);//隐藏进度条
                    refreshLayout.setRefreshing(false);//停止下拉刷新
                    datas = newsBean.getResult().getData();
                    adapter = new NewsListAdapter(datas, getContext());
                    listView.setAdapter(adapter);

                }
            });
        }catch (Exception e){
            ivError.setVisibility(View.VISIBLE);//加载失败显示的错误图片
            refreshLayout.setEnabled(false);//禁止下拉刷新
            progressBar.setVisibility(View.GONE);//隐藏进度条
            refreshLayout.setRefreshing(false);//停止下拉刷新
            Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }




}
