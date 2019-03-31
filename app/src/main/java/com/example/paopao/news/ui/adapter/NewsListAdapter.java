package com.example.paopao.news.ui.adapter;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paopao.news.R;
import com.example.paopao.news.domain.NewsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paopao on 2018/10/7.
 */

public class NewsListAdapter extends BaseAdapter {
    private List<NewsBean.ResultBean.DataBean> data;
    private Context context;

    public NewsListAdapter(List<NewsBean.ResultBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<NewsBean.ResultBean.DataBean> data){
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        MyViewHolder holder;
        if(convertView==null){
            view=View.inflate(context, R.layout.item_news,null);
            holder=new MyViewHolder();
            holder.ivIcon=view.findViewById(R.id.iv_icon);
            holder.txTitle=view.findViewById(R.id.tv_title);
            holder.tvFrom=view.findViewById(R.id.tv_from);
            holder.tvDate=view.findViewById(R.id.tv_date);
            view.setTag(holder);
        }else {//复用convertView
            view=convertView;
            holder= (MyViewHolder) view.getTag();
        }

        //数据填充
        NewsBean.ResultBean.DataBean dataBean=data.get(position);
        holder.txTitle.setText(dataBean.getTitle());

        if(dataBean.getAnthor_name()==null){

            holder.tvFrom.setText("新闻来源");
        }else{
            holder.tvFrom.setText(dataBean.getAnthor_name());
        }
        holder.tvDate.setText(dataBean.getDate());
        if (!TextUtils.isEmpty(dataBean.getThumbnail_pic_s())){
            Picasso.with(context)
                    .load(dataBean.getThumbnail_pic_s())
                    .placeholder(R.drawable.v1)
                    .error(R.drawable.v1)
                    .into(holder.ivIcon);//图片
        }


        return view;
    }

    private static class MyViewHolder{
        ImageView ivIcon;
        TextView txTitle,tvFrom,tvDate;
    }

}
