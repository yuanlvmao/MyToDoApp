package com.wutongtech.mytodoapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by wutongtech_shengmao on 18-8-23 14:03.
 * 作用：和网络有关的工具类
 */
public class NetUtils {

    private NetUtils(){
    }

    /**
     * 解码
     * @param url url
     * @return 解码后的url
     */
    public static String decodeUrl(String url) {
        if (url != null && url.contains("%")) {
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

}
