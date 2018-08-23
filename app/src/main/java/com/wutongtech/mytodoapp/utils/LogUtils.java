package com.wutongtech.mytodoapp.utils;

import android.util.Log;

/**
 * Created by wutongtech_shengmao on 2017/6/7 10:50.
 * 作用：日志打印的工具类
 */
public class LogUtils {

//    public static boolean debug = false;//是否是debug模式
    public static boolean debug = true;//是否是debug模式

    private static final String TAG = "MyToDoApp.LOG";

    public static void e(String tag, String message){
        if(debug){
            Log.e(tag,message);
        }
    }

    public static void d(String tag, String message){
        if(debug){
            Log.d(tag,message);
        }
    }

    public static void v(String tag, String message){
        if(debug){
            Log.v(tag,message);
        }
    }

    public static void i(String tag, String message){
        if(debug){
            Log.i(tag,message);
        }
   }

    public static void w(String tag, String message){
        if(debug){
            Log.w(tag,message);
        }
    }

    public static void e(Class clazz, String message){
        if(debug){
            Log.e(clazz.getSimpleName(),message);
        }
    }

    public static void d(Class clazz, String message){
        if(debug){
            Log.d(clazz.getSimpleName(),message);
        }
    }

    public static void v(Class clazz, String message){
        if(debug){
            Log.v(clazz.getSimpleName(),message);
        }
    }

    public static void i(Class clazz, String message){
        if(debug){
            Log.i(clazz.getSimpleName(),message);
        }
    }

    public static void w(Class clazz, String message){
        if(debug){
            Log.w(clazz.getSimpleName(),message);
        }
    }


    public static void e(String message){
        if(debug){
            Log.e(TAG,message);
        }
    }

    public static void d(String message){
        if(debug){
            Log.d(TAG,message);
        }
    }

    public static void v(String message){
        if(debug){
            Log.v(TAG,message);
        }
    }

    public static void i(String message){
        if(debug){
            Log.i(TAG,message);
        }
    }

    public static void w(String message){
        if(debug){
            Log.w(TAG,message);
        }
    }




}
