package com.linkage.study.attend.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.linkage.study.attend.R;
import com.linkage.study.attend.app.BaseActivity;
import com.linkage.study.attend.app.BaseApplication;
import com.linkage.study.attend.ui.fragment.MainContactFragment;
import com.linkage.study.attend.ui.fragment.MainIndexFragment;
import com.linkage.study.attend.ui.fragment.MainMineFragment;
import com.linkage.study.attend.ui.fragment.MainTongjiFragment;
import com.linkage.study.attend.utils.StateBarTranslucentUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.rg_tab)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.b1)
    private RadioButton radio1;
    @ViewInject(R.id.b2)
    private RadioButton radio2;
    @ViewInject(R.id.b3)
    private RadioButton radio3;
    @ViewInject(R.id.b4)
    private RadioButton radio4;

    private MainRadioGroupListener radioGroupListener;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private MainIndexFragment mainIndexFragment;
    private MainContactFragment mainContactFragment;
    private MainTongjiFragment mainTongjiFragment;
    private MainMineFragment mainMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        fragmentManager = getSupportFragmentManager();
        radioGroupListener = new MainRadioGroupListener();
        mRadioGroup.setOnCheckedChangeListener(radioGroupListener);
        radio1.performClick();
        setSwipeBackEnable(false);
    }

    private class MainRadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            changeFragment(checkedId);
        }
    }

    public void performChecked(int id) {
        switch (id) {
            case R.id.b1:
                radio1.performClick();
                break;
            case R.id.b2:
                radio2.performClick();
                break;
            case R.id.b3:
                radio3.performClick();
                break;
            case R.id.b4:
                radio4.performClick();
                break;
        }
    }

    private void changeFragment(int btnId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        switch (btnId){
            case R.id.b1:
                if(mainIndexFragment == null){
                    mainIndexFragment = MainIndexFragment.create();
                    transaction.add(R.id.content, mainIndexFragment, MainIndexFragment.class.getSimpleName());
                }
                StateBarTranslucentUtils.setStateBarColor(this, R.color.white);
                StateBarTranslucentUtils.setStatusBarFontDark(this, true);
                currentFragment = mainIndexFragment;
                transaction.show(mainIndexFragment);
                break;
            case R.id.b2:
                if(mainContactFragment == null){
                    mainContactFragment = MainContactFragment.create();
                    transaction.add(R.id.content, mainContactFragment, MainContactFragment.class.getSimpleName());
                }
                StateBarTranslucentUtils.setStateBarColor(this, R.color.white);
                StateBarTranslucentUtils.setStatusBarFontDark(this, true);
                currentFragment = mainContactFragment;
                transaction.show(mainContactFragment);
                break;
            case R.id.b3:
                if(mainTongjiFragment == null){
                    mainTongjiFragment = MainTongjiFragment.create();
                    transaction.add(R.id.content, mainTongjiFragment, MainTongjiFragment.class.getSimpleName());
                }
                StateBarTranslucentUtils.setStateBarColor(this, R.color.white);
                StateBarTranslucentUtils.setStatusBarFontDark(this, true);
                currentFragment = mainTongjiFragment;
                transaction.show(mainTongjiFragment);
                break;
            case R.id.b4:
                if(mainMineFragment == null){
                    mainMineFragment = MainMineFragment.create();
                    transaction.add(R.id.content, mainMineFragment, MainMineFragment.class.getSimpleName());
                }
                StateBarTranslucentUtils.setStateBarColor(this, R.color.white);
                StateBarTranslucentUtils.setStatusBarFontDark(this, false);
                currentFragment = mainMineFragment;
                transaction.show(mainMineFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(currentFragment != null) {
            if(currentFragment != mainMineFragment) {
                StateBarTranslucentUtils.setStateBarColor(this, R.color.white);
                StateBarTranslucentUtils.setStatusBarFontDark(this, true);
            }else {
                StateBarTranslucentUtils.setStateBarColor(this, R.color.status_bar_fragment_mine);
                StateBarTranslucentUtils.setStatusBarFontDark(this, false);
            }
        }
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
