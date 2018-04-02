package com.linkage.study.attend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.linkage.study.attend.R;
import com.linkage.study.attend.app.BaseActivity;
import com.linkage.study.attend.app.Urls;
import com.linkage.study.attend.utils.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_password_forget)
public class PasswordForgetActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_back)
    private ImageButton titleBack;
    @ViewInject(R.id.title_text)
    private TextView titleText;
    @ViewInject(R.id.title_menu1)
    private Button menuButton;
    @ViewInject(R.id.edit_username)
    private EditText usernameText;
    @ViewInject(R.id.edit_code)
    private EditText codeText;
    @ViewInject(R.id.get_code)
    private TextView getCode;
    @ViewInject(R.id.btn_login)
    private Button loginBtn;

    private String username;
    private String vCode;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            usernameText.setFocusable(true);
            usernameText.setFocusableInTouchMode(true);
            usernameText.requestFocus();
            showInputMethod(PasswordForgetActivity.this, getCurrentFocus());
            usernameText.setSelection(usernameText.getText().toString().length());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        titleText.setText("找回密码");
        menuButton.setText("");
        handler.sendEmptyMessageDelayed(1, 200);
        Intent intent = getIntent();
        if(intent != null) {
            username = intent.getStringExtra("username");
        }
        usernameText.setText(username);
        titleBack.setOnClickListener(this);
        menuButton.setOnClickListener(this);
        getCode.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setText(millisUntilFinished/1000 + "秒");
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
            getCode.setText("发送验证码");
        }
    };

    private void sendSms() {
        showLoading();
        RequestParams params = new RequestParams(Urls.api);
        params.addParameter("mobile", username);
        x.http().request(HttpMethod.POST, params, new HttpCallback(this) {
            @Override
            public void success(String s) {
                dismissLoading();
                Toast.makeText(PasswordForgetActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                getCode.setEnabled(false);
                timer.start();
            }

            @Override
            public void error(Throwable ex, String msg, boolean isOnCallback) {
                dismissLoading();
                if(ex != null)ex.printStackTrace();
                LogUtil.e("---onError-----" + "onError");
                Toast.makeText(PasswordForgetActivity.this, TextUtils.isEmpty(msg) ? "请求失败" : msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cancel(CancelledException e) {

            }
        });
    }

    private void verifyCode() {
        showLoading();
        RequestParams params = new RequestParams(Urls.api);
        params.addParameter("mobile", username);
        params.addParameter("vcode", vCode);
        x.http().request(HttpMethod.POST, params, new HttpCallback(this) {
            @Override
            public void success(String s) {
                dismissLoading();
                JSONObject json = null;
                String memberId = "", lostToken = "";
                try {
                    json = new JSONObject(s);
                    JSONObject jsonUser = json.optJSONObject("data");
                    memberId = jsonUser.optString("member_id");
                    lostToken = jsonUser.optString("lost_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PasswordForgetActivity.this, PasswordModifyActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("member_id", memberId);
                intent.putExtra("lost_token", lostToken);
                startActivity(intent);
                finished();
            }

            @Override
            public void error(Throwable ex, String msg, boolean isOnCallback) {
                dismissLoading();
                if(ex != null)ex.printStackTrace();
                LogUtil.e("---onError-----" + "onError");
                Toast.makeText(PasswordForgetActivity.this, TextUtils.isEmpty(msg) ? "请求失败" : msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cancel(CancelledException e) {

            }
        });
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_menu1:
                break;
            case R.id.get_code:
                username = usernameText.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendSms();
                break;
            case R.id.btn_login:
                username = usernameText.getText().toString();
                vCode = codeText.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(vCode)) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyCode();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyboard(getCurrentFocus());
    }
}
