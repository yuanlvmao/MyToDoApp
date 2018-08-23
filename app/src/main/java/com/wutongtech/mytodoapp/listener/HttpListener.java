package com.wutongtech.mytodoapp.listener;

/**
 * 网络请求结果的回调接口, 所有网络请求的类都要实现这个接口
 */
public interface HttpListener {

	/**
	 * 网络请求成功
	 * @param requestId 请求id
	 * @param response 响应
	 */
	void onSuccess(int requestId, String response);

	/**
	 * 网络请求失败
	 * @param requestId 请求id
	 * @param e 异常
	 */
	void onFail(int requestId, Exception e);
}
