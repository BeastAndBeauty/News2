package com.example.paopao.news.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.paopao.news.R;
import com.example.paopao.news.utils.PreUtils;

public class SplashActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        iv= (ImageView) findViewById(R.id.activity_splash);
        //初始化动画
        initAnim();
    }

    private void initAnim(){
        //透明度动画
        AlphaAnimation alpha=new AlphaAnimation(0,1);
        alpha.setDuration(3000);//动画时长
        alpha.setFillAfter(true);//动画运行完成，保留结束时的状态
        //监听动画
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画完成，进行下一个页面
                jumpToNextPage();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iv.startAnimation(alpha);
    }

    private void jumpToNextPage(){
        //判断是否是第一次进入，默认是第一次
        boolean isFirst= PreUtils.getBoolean(this,"isFirst",true);

        Intent intent=new Intent();
        if(isFirst)
            //进入引导页面
        intent.setClass(this,GuideActivity.class);
        else
            //进入功能页面
        intent.setClass(this,MainActivity.class);

        startActivity(intent);
        finish();


    }


}
