package com.linkage.study.attend.ui.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkage.study.attend.R;
import com.linkage.study.attend.data.db.AttendanceNum;
import com.linkage.study.attend.ui.DetailsClassStatisticsActivity;
import com.linkage.study.attend.widget.CommonDatePickerDialog;
import com.mining.app.zxing.view.PerRateView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

@SuppressLint("NewApi")
public class Today1Fragment extends Fragment {
	private View view;
	private PerRateView perRateView;
	private ListView statis;
	private ArrayList<AttendanceNum> list;
	private StatisticsApdater statisticsApdater;
	String TAG="TodayFragment";
	private TextView to_Days;
	private ImageView left_today;
	private ImageView right_today;
	private Calendar cal;
	private Calendar cal2;
	private SimpleDateFormat date;
	private String flag="";
	private TextView school_class;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		view=	inflater.inflate(R.layout.fragment_today,null);
		RelativeLayout alldata = (RelativeLayout)view.findViewById(R.id.all_Date);
		alldata.setVisibility(View.GONE);
	    flag=getActivity().getIntent().getStringExtra("flag");
		if(null==flag){
			flag="";
		}
		perRateView = (PerRateView) view.findViewById(R.id.perRateView); 
		to_Days=(TextView)view.findViewById(R.id.to_Days);
		left_today=(ImageView)view.findViewById(R.id.left_today);
		right_today=(ImageView)view.findViewById(R.id.right_today);
		date = new SimpleDateFormat("yyyy年MM月dd日");
		school_class=(TextView)view.findViewById(R.id.school_class);
//		if(BaseApplication.getInstance().getDefaultAccount().getUserType()==4&&flag.equals("4"))
		 if(flag.equals("4")){
			 school_class.setText("学校");
		 }else{
			 school_class.setText("班级");
		 }
		to_Days.setText(date.format(new Date()));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal2 = Calendar.getInstance();
		cal2.add(Calendar.MONTH, 0);
		to_Days.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new CommonDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						if (year == cal.get(YEAR)
								&& monthOfYear == cal.get(MONTH)) {
							cal2.setTime(new Date());
						} else {
							cal2.set(year, monthOfYear, 1); // 默认当月1号

						}
						try {
							//					if(cal2.getTime().compareTo(sdf2.parse(sdf2.format(new Date())))>0){
							//						Toast.makeText(getActivity(), "选择无效", Toast.LENGTH_SHORT).show();
							//					}else{
							to_Days.setText(date.format(cal2.getTime()));	
							initData("1",cal2.getTime());
							//					}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}, cal2.get(cal2.YEAR), cal2.get(cal2.MONTH), cal2
						.get(cal2.DAY_OF_MONTH),0).show();
			}
		});
		left_today.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String string=to_Days.getText().toString();
				String string2="";
				try {
					string2=subDay(string,-1);
					left_today.setImageResource(R.drawable.left2);
					right_today.setImageResource(R.drawable.right2);
					to_Days.setText(string2);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				try {
					initData("1",date.parse(string2));
				} catch (ParseException e) {

					e.printStackTrace();
				}
			}
		});
		right_today.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String string=to_Days.getText().toString();
				try {
					String string2=subDay(string,1);
					Date dato=	date.parse(string2);
					Date dat02=new Date();
					int i = dato.compareTo(date.parse(date.format(dat02)));				
					if(i>0){	
						right_today.setImageResource(R.drawable.right1);
						//				Toast.makeText(getActivity(), "点不了了", Toast.LENGTH_SHORT).show();
					}else{
						if(i==0){
							right_today.setImageResource(R.drawable.right1);
							to_Days.setText(string2);
							initData("1",date.parse(string2));
						}else {

							right_today.setImageResource(R.drawable.right2);
							to_Days.setText(string2);
							initData("1",date.parse(string2));
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		statis = (ListView) view.findViewById(R.id.statis);
		list=new ArrayList<AttendanceNum>();

		statisticsApdater=new StatisticsApdater(getActivity(), list);
		statis.setAdapter(statisticsApdater);
		initData("1",new Date());
		return view;


	}

	private String subDay(String date, int add) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);  

		rightNow.add(Calendar.DATE, add);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);

		return reStr;  
	}  

	class StatisticsApdater extends BaseAdapter {

		private Context context;
		private List<AttendanceNum> list;
		public StatisticsApdater(Context context, List<AttendanceNum> list) {
			this.context=context;
			this.list=list;
		}
		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Object getItem(int position) {

			return list.get(position);
		}

		public void   addAll(List<AttendanceNum> list, boolean append) {
			if (append) {
				if (this.list != null)
					this.list.addAll(list);
				else
					this.list = list;
			}
			this.list = list;
			notifyDataSetChanged();
		}
		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=	LayoutInflater.from(context).inflate(R.layout.statistics_item, null);
				holder.textView1=  (TextView) convertView.findViewById(R.id.classs);
				holder.textView2=  (TextView) convertView.findViewById(R.id.attanded);
				holder.textView3=  (TextView) convertView.findViewById(R.id.attanding);
				holder.textView4=  (TextView) convertView.findViewById(R.id.attandRate);
				holder.textView5=  (TextView) convertView.findViewById(R.id.click_detail);
				holder.bg = (LinearLayout) convertView.findViewById(R.id.click_bg);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
		
				
				
	
//				if(BaseApplication.getInstance().getDefaultAccount().getUserType()==4&&flag.equals("4"))
					if(flag.equals("4"))
				{
					if(list.get(position).getSchoolName().equals("null")){
						holder.textView1.setText("");
					}else {
						System.out.println(list.get(position).getSchoolName()+"      jb.optString(schoolName)");
						holder.textView1.setText(list.get(position).getSchoolName());
					}

				}else{
					if(list.get(position).getClassName().equals("null")){
						holder.textView1.setText("");
					}else {
						
						holder.textView1.setText(list.get(position).getClassName());
					}
				}
			
			holder.textView2.setText(list.get(position).getTotalNum());
			holder.textView3.setText(list.get(position).getComeNum());
			holder.textView4.setText(list.get(position).getAttendanceRateValue()+"%");
			holder.bg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent=	new Intent(getActivity(), DetailsClassStatisticsActivity.class);
//					intent.putExtra("classId", list.get(position).getClassId());
//					intent.putExtra("flag", flag);
//	                 Bundle bundle=new Bundle();
//					bundle.putSerializable("list",(Serializable)list);//序列化,要注意转化(Serializable)
//					intent.putExtras(bundle);//发送数据
//					intent.putExtra("schoolId", list.get(position).getSchoolId());
					startActivity(intent);

				}
			});
			return convertView;
		}

	}

	class ViewHolder {
		TextView textView1;
		LinearLayout bg;
		TextView textView2;
		TextView textView3;
		TextView textView4;
		TextView textView5;
	}
	private void initData(String type, Date date) {

     try {
     	String str = "{\"ret\":0,\"totalAttendanceRate\":\"23.00%\",\"data\":[{\"schoolId\":0,\"schoolName\":null,\"classId\":10605548,\"totalNum\":28,\"isCheck\":0,\"comeNum\":0,\"vacateNum\":0,\"nocomeNum\":28,\"attendanceRateValue\":0.0,\"attendanceRate\":\"0.00%\",\"className\":\"幼班幼班1班\"}],\"msg\":\"成功\"}";
		 JSONObject response = new JSONObject(str);

		 if (response.optInt("ret") == 0) {
			 List<AttendanceNum> attendanceNums = AttendanceNum.paserAll(response);
			 System.out.println(attendanceNums.size());
			 String totalAttendanceRate = response.optString("totalAttendanceRate");
			 if (null != attendanceNums && attendanceNums.size() > 0) {
				 statisticsApdater.addAll(attendanceNums, false);
				 String[] numRate = totalAttendanceRate.split("%");
				 float score = Float.parseFloat(numRate[0]);
				 perRateView.setMaxCount(100.0f);
				 perRateView.setCurrentCount(score);


				 perRateView.setScore(totalAttendanceRate);
			 }

		 }
	 }catch (Exception e){
     	e.printStackTrace();
	 }



	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
	}

}
