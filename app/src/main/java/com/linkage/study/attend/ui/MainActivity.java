package com.linkage.study.attend.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.linkage.study.attend.R;
import com.linkage.study.attend.app.BaseActivity;
import com.linkage.study.attend.app.BaseApplication;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setSwipeBackEnable(false);
    }


    private long mExitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getInstance().clearActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
