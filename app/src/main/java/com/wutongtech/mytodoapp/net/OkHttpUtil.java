package com.wutongtech.mytodoapp.net;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.wutongtech.mytodoapp.MyApplication;
import com.wutongtech.mytodoapp.listener.HttpListener;
import com.wutongtech.mytodoapp.utils.LogUtils;
import com.wutongtech.mytodoapp.utils.NetUtils;

import java.io.EOFException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wutongtech_shengmao on 2017/5/2 19:29.
 * 作用：okhttp的请求工具类
 */

public class OkHttpUtil {

    private static final String TAG = OkHttpUtil.class.getSimpleName();
    private static OkHttpUtil util = new OkHttpUtil();

    private OkHttpClient client;
    private Handler handler = new Handler(Looper.getMainLooper());

    private OkHttpUtil(){
        if(client == null){
            OkHttpClient.Builder builder =  new OkHttpClient.Builder()
                    .readTimeout(HttpConstantValue.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(HttpConstantValue.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(HttpConstantValue.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
            builder.sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .cookieJar(new CookieJar() {
                        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.context);

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            if (cookies != null && cookies.size() > 0) {
                                for (Cookie item : cookies) {
                                    cookieStore.add(url, item);
                                }
                            }
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url);
                            return cookies;
                        }
                    });
            if(LogUtils.debug){//debug环境下开启日志
                builder.addInterceptor(new LogInterceptor());
            }
            client = builder.build();

        }
    }

    public static OkHttpUtil getInstance(){
        return util;
    }

    public OkHttpClient getClient(){
        return client;
    }


    /**
     * post请求 异步
     * @param requestId 请求id
     * @param map 参数集合
     * @param httpListener 监听器
     */
    public void executePost(final int requestId, Map<String, String> map, final HttpListener httpListener) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();

        if(map != null){
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for(Map.Entry<String,String> value : entries){
                bodyBuilder.add(value.getKey(),value.getValue() == null?"":value.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(UrlStrings.getUrl(requestId))
                .post(bodyBuilder.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if(e != null && LogUtils.debug){
                    e.printStackTrace();
                }
                if(httpListener != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            httpListener.onFail(requestId,e);
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, final Response response){

                if(httpListener != null){
                    String responseStr = null;
                    try {
                        responseStr = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final String finalResponseStr = responseStr;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            httpListener.onSuccess(requestId, finalResponseStr);
                        }
                    });

                }


            }
        });
    }


    /**
     * post请求 同步
     * @param requestId 请求id
     * @param map 参数集合
     */
    public String asyncPost(final int requestId, Map<String, String> map) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();

        if(map != null){
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for(Map.Entry<String,String> value : entries){
                bodyBuilder.add(value.getKey(),value.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(UrlStrings.getUrl(requestId))
                .post(bodyBuilder.build())
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }


    /**
     * get请求  异步
     * @param requestId 请求id
     * @param map 参数
     * @param httpListener 监听
     */
    public void executeGet(final int requestId, Map<String, String> map, final HttpListener httpListener) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            if(map != null){
                int pos = 0;
                for (String key : map.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    //对参数进行URLEncoder
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(map.get(key), "utf-8")));
                    pos++;
                }
            }
            String param = tempParams.toString();
            String url = UrlStrings.getUrl(requestId);
            if(!TextUtils.isEmpty(param)){
                url = url + "?" + param;
            }
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    if(e != null && LogUtils.debug){
                        e.printStackTrace();
                    }
                    if(httpListener != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                httpListener.onFail(requestId,e);
                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(httpListener != null){
                        String responseStr = null;
                        try {
                            responseStr = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final String finalResponseStr = responseStr;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                httpListener.onSuccess(requestId, finalResponseStr);
                            }
                        });

                    }
                }
            });
        } catch (final Exception e) {
            if(e != null && LogUtils.debug){
                e.printStackTrace();
            }
            if(httpListener != null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpListener.onFail(requestId,e);
                    }
                });
            }

        }
    }

    /**
     * http请求  异步 请求方式由request指定
     * @param requestId 请求id
     * @param request 请求
     * @param httpListener 监听器
     */
    public void execute(final int requestId, Request request, final HttpListener httpListener) {
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    if(e != null && LogUtils.debug){
                        e.printStackTrace();
                    }
                    if(httpListener != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                httpListener.onFail(requestId,e);
                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(httpListener != null){
                        String responseStr = null;
                        try {
                            responseStr = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final String finalResponseStr = responseStr;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                httpListener.onSuccess(requestId, finalResponseStr);
                            }
                        });

                    }

                }
            });
        } catch (final Exception e) {
            if(e != null && LogUtils.debug){
                e.printStackTrace();
            }
            if(httpListener != null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpListener.onFail(requestId,e);
                    }
                });
            }

        }
    }

    public Handler getMainHandler(){
        return handler;
    }


    /**********************************************************************************/

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;//不验证
        }
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtils.v(TAG, "request:" + request.toString());

            RequestBody requestBody = request.body();

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    LogUtils.v(TAG,name + ": " + headers.value(i));
                }
            }

            Buffer buffer = new Buffer();
            if(requestBody != null){
                requestBody.writeTo(buffer);
            }

            Charset charset = Charset.forName("UTF-8");
            if(requestBody != null){
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
            }


            if (isPlaintext(buffer)) {
                LogUtils.v(TAG,"request map : " + NetUtils.decodeUrl(buffer.readString(charset)));
            }


            okhttp3.Response response = chain.proceed(chain.request());
//            long t1 = System.nanoTime();
//
//            long t2 = System.nanoTime();
//            Log.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.i(TAG, "response body:" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }


        boolean isPlaintext(Buffer buffer) {
            try {
                Buffer prefix = new Buffer();
                long byteCount = buffer.size() < 64 ? buffer.size() : 64;
                buffer.copyTo(prefix, 0, byteCount);
                for (int i = 0; i < 16; i++) {
                    if (prefix.exhausted()) {
                        break;
                    }
                    int codePoint = prefix.readUtf8CodePoint();
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false;
                    }
                }
                return true;
            } catch (EOFException e) {
                return false; // Truncated UTF-8 sequence.
            }
        }
    }

}
