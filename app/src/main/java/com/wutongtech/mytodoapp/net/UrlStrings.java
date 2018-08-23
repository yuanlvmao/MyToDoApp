package com.wutongtech.mytodoapp.net;

/**
 * Created by wutongtech_shengmao on 18-8-23 11:02.
 * 作用：获取url
 */
public final class UrlStrings {

    /**
     * 基础url
     */
    private static final String BASE_URL = "http://www.wanandroid.com/";

    /**
     * 私有化构造器，该类不允许创建对象
     */
    private UrlStrings() {

    }

    /**
     * 通过urlid获取url
     *
     * @param id id
     * @return url
     */
    public static String getUrl(int id) {
        String url;
        switch (id) {
            case UrlIds.LOGIN:
                url = BASE_URL + "user/login";
                break;
            case UrlIds.REGISTER:
                url = BASE_URL + "user/register";
                break;

            default:
                url = BASE_URL;
                break;
        }
        return url;
    }

}
