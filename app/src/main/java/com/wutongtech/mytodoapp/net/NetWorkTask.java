package com.wutongtech.mytodoapp.net;


import com.wutongtech.mytodoapp.listener.HttpListener;

import java.util.Map;

import okhttp3.Request;

/**
 * 网络请求任务类 --- 包装一层，，，如果需要替换网络请求库，就不需要改太多代码，只需要把具体请求实现类替换即可
 */
public class NetWorkTask {

	private static NetWorkTask task = new NetWorkTask();

	private NetWorkTask(){

	}

	public static NetWorkTask getInstance(){
		return task;
	}

	/**
	 * 发起网络请求 异步post
	 * @param requestId 网络请求的url的id
	 * @param map 参数集合
	 * @param httpListener 监听
	 */
	public void executePost(int requestId, Map<String,String> map, HttpListener httpListener){
		OkHttpUtil.getInstance().executePost(requestId,map, httpListener);
	}

	/**
	 * 发起网络请求 异步get
	 * @param requestId 网络请求的url的id
	 * @param map 参数集合
	 * @param httpListener 监听
	 */
	public void executeGet(int requestId, Map<String,String> map, HttpListener httpListener){
		OkHttpUtil.getInstance().executeGet(requestId,map, httpListener);
	}

	/**
	 * 发起网络请求 异步 请求方式由request决定
	 * @param requestId 网络请求的url的id
	 * @param request 具体的请求
	 * @param httpListener 监听
	 */
	public void execute(int requestId, Request request, HttpListener httpListener){
		OkHttpUtil.getInstance().execute(requestId,request, httpListener);
	}

}
