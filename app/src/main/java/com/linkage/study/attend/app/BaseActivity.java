package com.linkage.study.attend.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

import com.linkage.study.attend.R;
import com.linkage.study.attend.data.db.User;
import com.linkage.study.attend.ui.LoginActivity;
import com.linkage.study.attend.utils.StateBarTranslucentUtils;
import com.linkage.study.attend.widget.AlertDialog;
import com.linkage.study.attend.widget.LoadingDialog;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by cunguoyao on 2017/7/29.
 */

public class BaseActivity extends SwipeBackActivity {

    private DbManager dbManager;
    private User account;
    private LoadingDialog loadingDialog;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        dbManager = BaseApplication.getInstance().getDbManager();
        try {
            account = getDbManager().selector(User.class)
                    .where("default_account", "=", 1)
                    .orderBy("last_login_time", true).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(false);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        //mSwipeBackLayout.setEdgeSize(200);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        StateBarTranslucentUtils.setStatusBarFontDark(this, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!BaseApplication.getInstance().getUnSetStateBarColorPage().contains(this.getClass().getName())) {
            StateBarTranslucentUtils.setStateBarColor(this, R.color.white);
        }
    }

    public DbManager getDbManager() {
        return dbManager;
    }

    public User getAccount() {
        try {
            account = getDbManager().selector(User.class)
                    .where("default_account", "=", 1)
                    .orderBy("last_login_time", true).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return account;
    }

    //显示虚拟键盘
    public static void showInputMethod(Context context, View view) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }
    //隐藏虚拟键盘
    public static void hideKeyboard(View v){
        InputMethodManager imm = ( InputMethodManager) v.getContext( ).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken() , 0 );
        }
    }

    public void showLoginDialog(final Context context) {
        AlertDialog loginDialog = new AlertDialog(this);
        loginDialog.builder().setTitle("提示").setMsg("需要登录才能使用此功能")
                .setCancelable(true).setNegativeButton("取消", null)
                .setPositiveButton("立即登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }

    public void showLoading() {
        if(loadingDialog != null && loadingDialog.isShowing())return;
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    public void dismissLoading() {
        if(loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
    }
}
