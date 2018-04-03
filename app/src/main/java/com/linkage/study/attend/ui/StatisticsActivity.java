package com.linkage.study.attend.ui;


import android.annotation.SuppressLint;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.linkage.study.attend.R;
import com.linkage.study.attend.ui.fragment.Today1Fragment;
import com.linkage.study.attend.ui.fragment.TodayFragment;


@SuppressLint("NewApi")
public class StatisticsActivity extends Activity implements OnClickListener {
	String TAG = "StatisticsActivity";
	private FragmentManager fm;
	private FragmentTransaction ft;
	private RelativeLayout month_f;
	private RelativeLayout week_f;
	private RelativeLayout today_f;
	private TodayFragment todayFragment;
	private Today1Fragment weekFragment;
	private Today1Fragment monthFragment2;
	private ImageView tab_w;
	private ImageView tab_m;
	private ImageView tab_t;
	private Intent intent;
	private String flag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_attendance);
//		setTitleBg();
//		changeBg(2);
		intent = getIntent();
		flag = intent.getStringExtra("flag");

		intent.putExtra("flag", flag);
		initViews();
		initEvents();
//		Consts.isRecipe = false;
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
//		perRateView = (PerRateView) this.findViewById(R.id.perRateView); 

//		Consts.isRecipe = false;
		todayFragment = new TodayFragment();
		weekFragment = new Today1Fragment();
		monthFragment2 = new Today1Fragment();
		fm = this.getFragmentManager();
		ft = fm.beginTransaction();
		ft.replace(R.id.main, todayFragment);
		ft.commit();


	}

	private void initViews() {
		month_f = (RelativeLayout) findViewById(R.id.month_f);
		week_f = (RelativeLayout) findViewById(R.id.week_f);
		today_f = (RelativeLayout) findViewById(R.id.today_f);
		tab_w = (ImageView) findViewById(R.id.tab_w);
		tab_t = (ImageView) findViewById(R.id.tab_t);
		tab_m = (ImageView) findViewById(R.id.tab_m);

	}

	private void initEvents() {

		month_f.setOnClickListener(this);
		week_f.setOnClickListener(this);
		today_f.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {
		ft = fm.beginTransaction();
		switch (v.getId()) {
			case R.id.today_f:
				ft.replace(R.id.main, todayFragment);
				tab_t.setBackgroundColor(getResources().getColor(R.color.bs_rate1));
				tab_w.setBackgroundColor(getResources().getColor(R.color.white));
				tab_m.setBackgroundColor(getResources().getColor(R.color.white));
				break;
			case R.id.week_f:
				tab_t.setBackgroundColor(getResources().getColor(R.color.white));
				tab_w.setBackgroundColor(getResources().getColor(R.color.bs_rate1));
				tab_m.setBackgroundColor(getResources().getColor(R.color.white));
				ft.replace(R.id.main, weekFragment);
				break;
			case R.id.month_f:
				tab_t.setBackgroundColor(getResources().getColor(R.color.white));
				tab_w.setBackgroundColor(getResources().getColor(R.color.white));
				tab_m.setBackgroundColor(getResources().getColor(R.color.bs_rate1));
				ft.replace(R.id.main, monthFragment2);
				break;

			default:
				break;
		}
		ft.commit();
	}


}
