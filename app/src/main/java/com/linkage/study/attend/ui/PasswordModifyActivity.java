package com.linkage.study.attend.ui;

import android.content.Intent;
import android.os.Bundle;
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

import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_password_modify)
public class PasswordModifyActivity extends BaseActivity implements View.OnClickListener {

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
    @ViewInject(R.id.btn_login)
    private Button loginBtn;

    private String username;
    private String lostToken;
    private String password;
    private String memberId;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            usernameText.setFocusable(true);
            usernameText.setFocusableInTouchMode(true);
            usernameText.requestFocus();
            showInputMethod(PasswordModifyActivity.this, getCurrentFocus());
            usernameText.setSelection(usernameText.getText().toString().length());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Intent intent = getIntent();
        if(intent == null) {
            finish();
            return;
        }
        username = intent.getStringExtra("username");
        lostToken = intent.getStringExtra("lost_token");
        memberId = intent.getStringExtra("member_id");
       /* if(TextUtils.isEmpty(lostToken) || TextUtils.isEmpty(memberId)) {
            finish();
            return;
        }*/
        titleText.setText("修改密码");
        menuButton.setText("");
        handler.sendEmptyMessageDelayed(1, 200);
        titleBack.setOnClickListener(this);
        menuButton.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    private void modifyPwd() {
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                Toast.makeText(PasswordModifyActivity.this, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PasswordModifyActivity.this, LoginActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_menu1:
                break;
            case R.id.btn_login:
                password = usernameText.getText().toString();
                String repassword = passwordText.getText().toString();
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(repassword)) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                modifyPwd();
                break;
            case R.id.text_forget_password:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyboard(getCurrentFocus());
    }
}
