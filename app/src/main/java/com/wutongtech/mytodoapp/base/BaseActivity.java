package com.wutongtech.mytodoapp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wutongtech.mytodoapp.R;

/**
 * Created by wutongtech_shengmao on 18-8-23 10:11.
 * 作用：基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里

    protected TextView title, titleRight;
    protected ImageView leftImage;
    protected ImageView tipsImage;
    protected View toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            AppBarLayout appBarLayout= (AppBarLayout) findViewById(R.id.appbar);
            appBarLayout.setPadding(0, (int) (getResources().getDisplayMetrics().density*25),0,0);
        }
        initToolBar();
    }


    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        toolbar = findViewById(R.id.title_bar);
        title = (TextView)findViewById(R.id.title);
        titleRight = (TextView)findViewById(R.id.title_right);
        leftImage = (ImageView) findViewById(R.id.left_image);
        tipsImage = (ImageView) findViewById(R.id.tips_image);

        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     * 隐藏toolBar
     */
    protected void hideToolBar(){
        toolbar.setVisibility(View.GONE);
    }

    /**
     * 左侧按钮的点击事件
     * @param listener 监听
     */
    protected void setLeftClickListener(View.OnClickListener listener){
        leftImage.setOnClickListener(listener);
    }

    /**
     * 设置toolbar的ui文本
     * @param leftRes 左边资源文件
     * @param title 标题文本
     * @param rightTitle 右侧标题文本
     */
    protected void setToolBarUi(int leftRes,String title,String rightTitle){
        if(leftRes > 0 ){
            leftImage.setImageResource(leftRes);
        }
        this.title.setText(title);
        this.titleRight.setText(rightTitle);
    }

    /**
     * 设置toolbar的ui文本
     * @param leftRes 左边图片的资源文件
     * @param title 文本
     * @param rightTitle 右边的文本
     */
    protected void setToolBarUi(int leftRes,int title,int rightTitle){
        if(leftRes > 0 ){
            leftImage.setImageResource(leftRes);
        }
        this.title.setText(title);
        this.titleRight.setText(rightTitle);
    }

    /**
     * 设置标题
     * @param title 标题
     */
    protected void setTitleText(String title){
        this.title.setText(title);
    }

    /**
     * 设置标题
     * @param title 标题
     */
    protected void setTitleText(int title){
        this.title.setText(title);
    }

    /**
     * 设置右侧标题
     * @param title 标题
     */
    protected void setRightTitleText(String title){
        this.titleRight.setText(title);
    }

    /**
     * 设置右侧标题
     * @param title 标题
     */
    protected void setRightTitleText(int title){
        this.titleRight.setText(title);
    }

    /**
     * 隐藏右侧标题
     */
    protected void hideRightTitle(){
        this.titleRight.setVisibility(View.GONE);
    }

    /**
     * 隐藏右侧标题
     */
    protected void showRightTitle(){
        this.titleRight.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏标题
     */
    protected void hideTitle(){
        this.title.setVisibility(View.GONE);
    }

    /**
     * 隐藏左侧标题
     */
    protected void hideLeftImage(){
        this.leftImage.setVisibility(View.GONE);
    }

    /**
     * 隐藏右侧的提示图片
     */
    protected void hideTipsImage(){
        this.tipsImage.setVisibility(View.GONE);
    }

    /**
     * 显示右侧的提示图片
     */
    protected void showTipsImage(){
        this.tipsImage.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧文本的点击事件
     * @param listener 监听器
     */
    protected void setRightTextClickListener(View.OnClickListener listener){
        this.titleRight.setOnClickListener(listener);
    }

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }

    @Override
    public void setContentView(int layoutResID) {

        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }

    @Override
    public void setContentView(View view) {

        parentLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

        parentLinearLayout.addView(view, params);

    }

}
