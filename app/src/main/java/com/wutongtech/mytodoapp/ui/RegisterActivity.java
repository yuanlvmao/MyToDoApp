package com.wutongtech.mytodoapp.ui;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wutongtech.mytodoapp.R;
import com.wutongtech.mytodoapp.base.BaseActivity;
import com.wutongtech.mytodoapp.bean.BaseResult;
import com.wutongtech.mytodoapp.bean.RegisterData;
import com.wutongtech.mytodoapp.listener.HttpListener;
import com.wutongtech.mytodoapp.net.NetWorkTask;
import com.wutongtech.mytodoapp.net.UrlIds;
import com.wutongtech.mytodoapp.utils.ToastUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {

    // UI
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setTitleText(R.string.register);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mConfirmPasswordView = findViewById(R.id.password_confirm);
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

    }

    /**
     * 注册
     */
    private void attemptLogin() {
        // 重置状态
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // 取得用户名密码
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        String confirmPassword = mConfirmPasswordView.getText().toString().trim();

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

        //检查确认密码
        if(!TextUtils.equals(password,confirmPassword)){
            mConfirmPasswordView.setError(getString(R.string.password_not_same));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            registerUser(email,password,confirmPassword);
        }
    }

    /**
     * 注册用户
     * @param email 用户名
     * @param password 密码
     * @param confirmPassword 确认密码
     */
    private void registerUser(String email, String password, String confirmPassword) {
        Map<String,String> map = new HashMap<>();
        map.put("username",email);
        map.put("password",password);
        map.put("repassword",confirmPassword);
        NetWorkTask.getInstance().executePost(UrlIds.REGISTER, map, new HttpListener() {
            @Override
            public void onSuccess(int requestId, String response) {
                Gson gson = new GsonBuilder().create();
                Type type = gson.fromJson(response,new TypeToken<BaseResult<RegisterData>>(){}.getType());
                BaseResult<RegisterData> result = gson.fromJson(response,type);
                if(result == null){
                    ToastUtils.showMessage(R.string.connect_error);
                    return;
                }
                if(result.getErrorCode() == 0){
                    //注册成功
                    ToastUtils.showMessage(R.string.register_success);
                    finish();
                }else {//注册失败
                    ToastUtils.showMessage(result.getErrorMsg());
                }

            }

            @Override
            public void onFail(int requestId, Exception e) {
                ToastUtils.showMessage(R.string.connect_error);
            }
        });
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
     * 注册
     * @param v view
     */
    public void register(View v) {
        attemptLogin();
    }

}

