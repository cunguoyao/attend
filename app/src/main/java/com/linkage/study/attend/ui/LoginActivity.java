package com.linkage.study.attend.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.linkage.study.attend.app.BaseApplication;
import com.linkage.study.attend.app.Urls;
import com.linkage.study.attend.data.db.User;
import com.linkage.study.attend.utils.HttpCallback;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_back)
    private ImageButton titleBack;
    @ViewInject(R.id.title_text)
    private TextView titleText;
    @ViewInject(R.id.title_menu1)
    private Button menuButton;
    @ViewInject(R.id.edit_username)
    private EditText usernameText;
    @ViewInject(R.id.edit_password)
    private EditText passwordText;
    @ViewInject(R.id.checkbox_password)
    private CheckBox mCbDisplayPassword;
    @ViewInject(R.id.text_forget_password)
    private TextView forgetText;
    @ViewInject(R.id.btn_login)
    private Button loginBtn;

    private String username;
    private String password;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            usernameText.setFocusable(true);
            usernameText.setFocusableInTouchMode(true);
            usernameText.requestFocus();
            showInputMethod(LoginActivity.this, getCurrentFocus());
            usernameText.setSelection(usernameText.getText().toString().length());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        titleText.setText("登录");
        menuButton.setText("注册");
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
        loginBtn.setOnClickListener(this);
        forgetText.setOnClickListener(this);
    }

    private void login() {
        showLoading();
        RequestParams params = new RequestParams(Urls.api);
        params.addParameter("uname", username);
        params.addParameter("password", password);
        x.http().request(HttpMethod.POST, params, new HttpCallback(this) {
            @Override
            public void success(String s) {
                dismissLoading();
                try {
                    //result = new String(result.getBytes("GB2312"), "utf-8");
                    JSONObject json = new JSONObject(s);
                    JSONObject jsonUser = json.optJSONObject("data");
                    String memberId = jsonUser.optString("member_id");
                    String status = jsonUser.optString("status");
                    String token = jsonUser.optString("accesstoken");
                    User user = BaseApplication.getInstance().getDbManager().selector(User.class)
                            .where("id", "=", Long.valueOf(memberId)).findFirst();
                    if(user == null) {
                        user = new User();
                        user.setId(Long.valueOf(memberId));
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setLastLoginTime(System.currentTimeMillis());
                        if ("true".equals(status)) {
                            user.setStatus(1);
                        } else {
                            user.setStatus(0);
                        }
                        user.setToken(token);
                        user.setDefaultAccount(1);
                        BaseApplication.getInstance().getDbManager().save(user);
                    }else {
                        user.setId(Long.valueOf(memberId));
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setLastLoginTime(System.currentTimeMillis());
                        if ("true".equals(status)) {
                            user.setStatus(1);
                        } else {
                            user.setStatus(0);
                        }
                        user.setToken(token);
                        user.setDefaultAccount(1);
                        BaseApplication.getInstance().getDbManager().saveOrUpdate(user);
                    }
                    //registerPush();
                    finish();
                }catch (Exception e) {
                    onError(e, true);
                }
            }

            @Override
            public void error(Throwable ex, String msg, boolean isOnCallback) {
                dismissLoading();
                if(ex != null)ex.printStackTrace();
                LogUtil.e("---onError-----" + "onError");
                Toast.makeText(LoginActivity.this, TextUtils.isEmpty(msg) ? "请求失败" : msg, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(this, RegisterActivity.class);
                username = usernameText.getText().toString();
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
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
        hideKeyboard(getCurrentFocus());
    }
}
