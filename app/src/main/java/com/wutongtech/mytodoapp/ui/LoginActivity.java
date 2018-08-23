package com.wutongtech.mytodoapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wutongtech.mytodoapp.R;
import com.wutongtech.mytodoapp.base.BaseActivity;
import com.wutongtech.mytodoapp.listener.HttpListener;
import com.wutongtech.mytodoapp.net.NetWorkTask;
import com.wutongtech.mytodoapp.net.UrlIds;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

    // UI
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setTitleText(R.string.login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * 登录
     */
    private void attemptLogin() {
        // 重置状态
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // 获取输入的文本
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // 检查密码
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 检查用户名
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            login(email,password);
        }
    }

    /**
     * 校验密码位数
     * @param password 密码
     * @return true正常 false太短
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * 显示dialog
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 登录
     * @param userName 用户名
     * @param password 密码
     */
    private void login(String userName,String password){
        showProgress(true);
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password",password);
        NetWorkTask.getInstance().executePost(UrlIds.LOGIN, map, new HttpListener() {
            @Override
            public void onSuccess(int requestId, String response) {
//                BaseResult result =
            }

            @Override
            public void onFail(int requestId, Exception e) {
                showProgress(false);
            }
        });
    }

    /**
     * 注册
     * @param v view
     */
    public void register(View v) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

}

