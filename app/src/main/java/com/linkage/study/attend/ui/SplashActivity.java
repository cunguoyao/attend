package com.linkage.study.attend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.linkage.study.attend.R;
import com.linkage.study.attend.app.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    private MyCountDownTimer mc;
    @ViewInject(R.id.num_count_down)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mc = new MyCountDownTimer(2000, 1000);
        mc.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, StatisticsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
        setSwipeBackEnable(false);
    }
    private Handler handler=new Handler();
    /**
     * 继承 CountDownTimer 防范
     *
     * 重写 父类的方法 onTick() 、 onFinish()
     */
    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         * 表示以毫秒为单位 倒计时的总数
         *
         * 例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         * 表示 间隔 多少微秒 调用一次 onTick 方法
         *
         * 例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void onFinish() {
            tv.setText("0s");
        }
        public void onTick(long millisUntilFinished) {
            tv.setText("" + millisUntilFinished / 1000 + "s");
        }
    }
}
