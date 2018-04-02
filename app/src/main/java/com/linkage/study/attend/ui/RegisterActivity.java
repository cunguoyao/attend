package com.linkage.study.attend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.linkage.study.attend.R;
import com.linkage.study.attend.app.BaseActivity;
import com.linkage.study.attend.app.Urls;
import com.linkage.study.attend.utils.HttpCallback;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

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
    @ViewInject(R.id.edit_password)
    private EditText passwordText;
    @ViewInject(R.id.checkbox_password)
    private CheckBox mCbDisplayPassword;
    @ViewInject(R.id.text_forget_password)
    private TextView forgetText;
    @ViewInject(R.id.btn_login)
    private Button loginBtn;

    private String username;
    private String vCode;
    private String password;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            usernameText.setFocusable(true);
            usernameText.setFocusableInTouchMode(true);
            usernameText.requestFocus();
            showInputMethod(RegisterActivity.this, getCurrentFocus());
            usernameText.setSelection(usernameText.getText().toString().length());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        titleText.setText("注册");
        menuButton.setText("登录");
        handler.sendEmptyMessageDelayed(1, 200);
        mCbDisplayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordText.setSelection(passwordText.getText().toString().length());
                }else {
                    passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordText.setSelection(passwordText.getText().toString().length());
                }
            }
        });
        Intent intent = getIntent();
        if(intent != null) {
            username = intent.getStringExtra("username");
        }
        usernameText.setText(username);
        titleBack.setOnClickListener(this);
        menuButton.setOnClickListener(this);
        getCode.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgetText.setOnClickListener(this);
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
                Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                getCode.setEnabled(false);
                timer.start();
            }

            @Override
            public void error(Throwable ex, String msg, boolean isOnCallback) {
                dismissLoading();
                if(ex != null)ex.printStackTrace();
                LogUtil.e("---onError-----" + "onError");
                Toast.makeText(RegisterActivity.this, TextUtils.isEmpty(msg) ? "请求失败" : msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cancel(CancelledException e) {

            }
        });
    }

    private void register() {
        showLoading();
        RequestParams params = new RequestParams(Urls.api);
        params.addParameter("uname", username);
        params.addParameter("vcode", vCode);
        params.addParameter("password", password);
        x.http().request(HttpMethod.POST, params, new HttpCallback(this) {
            @Override
            public void success(String s) {
                dismissLoading();
                try {
                    //result = new String(result.getBytes("GB2312"), "utf-8");
                    JSONObject json = new JSONObject(s);
                    JSONObject jsonData = json.optJSONObject("data");
                    String status = jsonData.optString("status");
                    if("true".equals(status)) {
                        Toast.makeText(RegisterActivity.this, "注册成功，请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finished();
                    }else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    onError(e, true);
                }
            }

            @Override
            public void error(Throwable ex, String msg, boolean isOnCallback) {
                dismissLoading();
                if(ex != null)ex.printStackTrace();
                LogUtil.e("---onError-----" + "onError");
                Toast.makeText(RegisterActivity.this, TextUtils.isEmpty(msg) ? "请求失败" : msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cancel(CancelledException cex) {
                dismissLoading();
                LogUtil.e("---onCancelled-----" + "onCancelled");
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
                Intent intent = new Intent(this, LoginActivity.class);
                username = usernameText.getText().toString();
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
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
                password = passwordText.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(vCode)) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                register();
                break;
            case R.id.text_forget_password:
                Intent intent2 = new Intent(this, PasswordForgetActivity.class);
                username = usernameText.getText().toString();
                intent2.putExtra("username", username);
                startActivity(intent2);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        hideKeyboard(getCurrentFocus());
    }
}
