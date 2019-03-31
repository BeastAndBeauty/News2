package com.example.paopao.news.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.paopao.news.R;
import com.example.paopao.news.utils.DensityUtil;
import com.example.paopao.news.utils.PreUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btn_start;
    private LinearLayout ll_point_group;
    private ImageView iv_white_point;
    //两点的间距
    private int leftMax;
    private int widthdpi;
    private int[] imageIds=new int[]{R.drawable.guide_0,R.drawable.guide_1,
            R.drawable.guide_2,R.drawable.guide_3,R.drawable.guide_4};
    private ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager= (ViewPager) findViewById(R.id.viewPager);
        btn_start= (Button) findViewById(R.id.btn_start);
        ll_point_group= (LinearLayout) findViewById(R.id.ll_point_group);
        iv_white_point= (ImageView) findViewById(R.id.iv_white_point);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //表示新手引导已经展示过了，下次不再展示
                PreUtils.putBoolean(GuideActivity.this,"isFirst",false);
                //跳转主页面
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();

            }
        });
        //初始化展示的数据
        initData();
    }

    private void initData(){

        widthdpi= DensityUtil.dip2px(this,10);
        //初始化引导图片数据
        imageViews=new ArrayList<>();

        for (int i=0;i<imageIds.length;i++){
            ImageView image=new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            imageViews.add(image);

            //创建点
            ImageView point=new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            //单位是像素
            //把单位
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(widthdpi,widthdpi);
            point.setLayoutParams(params);
            if(i!=0){
                //不包括第0个点，所有点距离左边有10个像素
                params.leftMargin=widthdpi;
            }
            //添加到线性布局里面
            ll_point_group.addView(point);
        }



        GuideAdapter adapter=new GuideAdapter();
        viewPager.setAdapter(adapter);//viewpager设置数据
        //设置滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //当前显示最后一个条目的时候，跳转按钮可见
                if (position==imageIds.length-1)
                    btn_start.setVisibility(View.VISIBLE);
                else
                    btn_start.setVisibility(View.GONE);
            }
        });

        //根据View的声明周期，当视图执行到onLayout或者onDraw的时候 ，视图的高和宽，边距都有了
        iv_white_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //得到屏幕滑动百分比
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        /**
         * 当页面滚动回调这个方法
         * @param position 当前滑动的位置
         * @param positionOffset 页面滑动的百分比
         * @param positionOffsetPixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //两点间移动的距离=屏幕滑动百分比*间距
            //两点间滑动距离对应的坐标=原来的起始位置+两点间移动的距离
            int leftmargin= (int) (position*leftMax+positionOffset*leftMax);
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) iv_white_point.getLayoutParams();
            params.leftMargin=leftmargin;
            iv_white_point.setLayoutParams(params);
        }

        /**
         * 当前页面被选中的时候，回调这个方法
         * @param position 被选中页面的对应的位置
         */
        @Override
        public void onPageSelected(int position) {

        }

        /**
         * 当ViewPager页面滑动状态发生变化的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            //执行不只一次
            iv_white_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            //间距=第一个点距离左边的距离-第0个点距离左边的距离
            leftMax=ll_point_group.getChildAt(1).getLeft()-ll_point_group.getChildAt(0).getLeft();

        }
    }


    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        //初始化界面数据，类似getView
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView imageView=imageViews.get(position);
//            container.addView(imageView);
//            return imageView;

            ImageView v=imageViews.get(position);
//            ViewGroup parent = (ViewGroup) v.getParent();
            //Log.i("ViewPaperAdapter", parent.toString());
//             if (parent != null) {
//                 parent.removeAllViews();		}
            container.addView(v);
             return v;


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
