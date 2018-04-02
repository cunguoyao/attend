package com.linkage.study.attend.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.linkage.study.attend.ui.LoginActivity;
import com.linkage.study.attend.widget.AlertDialog;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

/**
 * Created by cunguoyao on 2017/9/18.
 */

public abstract class HttpCallback implements Callback.CommonCallback<String> {

    private Context context;
    public abstract void success(String s);
    public abstract void error(Throwable ex, String msg, boolean isOnCallback);
    public abstract void cancel(CancelledException e);
    public void finished(){}

    public HttpCallback(Context context) {
        this.context = context;
    }

    public void onTokenFail() {
        finished();
        AlertDialog loginDialog = new AlertDialog(context);
        loginDialog.builder().setTitle("提示").setMsg("登录信息可能已经失效，请重新登录")
                .setCancelable(true).setNegativeButton("取消", null)
                .setPositiveButton("立即登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }).show();
    }

    @Override
    public void onSuccess(String s) {
        LogUtil.e("---onSuccess-----" + s);
        try {
            JSONObject json = new JSONObject(s);
            if ("succ".equals(json.optString("rsp"))) {
                success(s);
            }else {
                if("100002".equals(json.optString("res"))) {
                    onTokenFail();
                }else {
                    error(null, json.optString("msg"), true);
                }
            }
        }catch (Exception e) {
            onError(e, true);
        }
    }

    @Override
    public void onError(Throwable throwable, boolean b) {
        error(throwable, "", b);
    }

    @Override
    public void onCancelled(CancelledException e) {
        cancel(e);
    }

    @Override
    public void onFinished() {

    }
}
