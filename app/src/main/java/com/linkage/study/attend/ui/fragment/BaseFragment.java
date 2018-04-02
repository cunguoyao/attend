package com.linkage.study.attend.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.linkage.study.attend.app.BaseApplication;
import com.linkage.study.attend.data.db.User;
import com.linkage.study.attend.ui.LoginActivity;
import com.linkage.study.attend.widget.AlertDialog;
import com.linkage.study.attend.widget.LoadingDialog;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by cunguoyao on 2017/8/13.
 */

public class BaseFragment extends Fragment {

    public DbManager dbManager;
    public User account;
    public LoadingDialog loadingDialog;

    public DbManager getDbManager() {
        return dbManager;
    }

    public User getAccount() {
        try {
            account = getDbManager().selector(User.class)
                    .where("default_account", "=", 1)
                    .and("status", "=", 1)
                    .orderBy("last_login_time", true).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            String FRAGMENTS_TAG = "android:support:fragments";
            // remove掉保存的Fragment
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        dbManager = BaseApplication.getInstance().getDbManager();
        try {
            account = getDbManager().selector(User.class)
                    .where("default_account", "=", 1)
                    .orderBy("last_login_time", true).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //修改当前 Activity 的显示模式，hideStatusBarBackground :true 全屏模式，false 着色模式
    public void setStatusBar(boolean hideStatusBarBackground, int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            FragmentActivity activity = getActivity();
            if(activity == null)return;
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (hideStatusBarBackground) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }

            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                if (hideStatusBarBackground) {
                    mChildView.setPadding(
                            mChildView.getPaddingLeft(),
                            0,
                            mChildView.getPaddingRight(),
                            mChildView.getPaddingBottom()
                    );
                } else {
                    int statusHeight = getStatusBarHeight(getActivity());
                    mChildView.setPadding(
                            mChildView.getPaddingLeft(),
                            statusHeight,
                            mChildView.getPaddingRight(),
                            mChildView.getPaddingBottom()
                    );
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FragmentActivity activity = getActivity();
            if(activity == null)return;
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hideStatusBarBackground) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.setStatusBarColor(getResources().getColor(res));
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    //get StatusBar Height
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public void showLoginDialog(final Context context) {
        AlertDialog loginDialog = new AlertDialog(context);
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

    public void showLoading(boolean cancelable) {
        if(loadingDialog != null && loadingDialog.isShowing())return;
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    public void dismissLoading() {
        if(loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
