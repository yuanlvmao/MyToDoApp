package com.wutongtech.mytodoapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.wutongtech.mytodoapp.MyApplication;


/**
 * Created by wutongtech_shengmao on 2017/6/7 10:50.
 * 作用：提示工具类
 */
public class ToastUtils {

    private static Toast toast;

    public static void showMessage(Context mContext, String message){
        Context context = mContext.getApplicationContext();
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showMessage(Context mContext, int message){
        Context context = mContext.getApplicationContext();
        if(toast == null){
            toast = Toast.makeText(context,message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showMessage(String message){
        if(toast == null){
            toast = Toast.makeText(MyApplication.context, message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showMessage(int message){
        if(toast == null){
            toast = Toast.makeText(MyApplication.context,message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

}
