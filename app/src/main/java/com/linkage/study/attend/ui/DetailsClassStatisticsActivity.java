package com.linkage.study.attend.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.zxing.common.StringUtils;
import com.linkage.study.attend.R;
import com.linkage.study.attend.app.BaseApplication;
import com.linkage.study.attend.data.db.AttendanceDetail;
import com.linkage.study.attend.data.db.AttendanceNum;
import com.linkage.study.attend.data.db.ClassRoom;
import com.linkage.study.attend.widget.CircularImage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsClassStatisticsActivity extends Activity implements
        OnClickListener
{
    public static final String TAG = DetailsClassStatisticsActivity.class.getSimpleName();
    
    private List<String> names;
    
    private List<String> schools;
    
    private int selectorNum = 0;
    
    private Button left;
    
    private Button right;
    
    private TextView title;
    
    private List<ClassRoom> mClassRooms;
    
    private String[] clazzs;
    
    private long clazzId;
    
    private Intent intent;
    
    private String clazzIdIntent;
    
    private ListView list_detail;
    
    private List<AttendanceDetail> attendanceNums;
    
    private DetailApdater adapter;
    
//    private EditText edit_student;
    
    private String type = "";
    
    private String flag;
    
    private List<AttendanceDetail> data;
    
//    private ImageView clear_edit;
    
    private String schoolId;
    
    private List<AttendanceNum> attNums;
    
    private FrameLayout contanct_search_frame_layout;
    
    private TextView classs;
    
    private ListView list_searchView;
    
    private DetailApdater detailadapter;
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_details);
        intent = getIntent();
        clazzIdIntent = intent.getStringExtra("classId");
        type = intent.getStringExtra("type");
        schoolId = intent.getStringExtra("schoolId");
        flag = intent.getStringExtra("flag");
        if (null == flag)
        {
            flag = "";
        }
        if (flag.equals("4"))
        {
            
            attNums = (List<AttendanceNum>) intent.getSerializableExtra("list");
            if (null != attNums)
            {
                schools = new ArrayList<>();
                for (int i = 0; i < attNums.size(); i++)
                {
                    schools.add(attNums.get(i).getSchoolName());
//                    System.out.println(attNums.get(i).getSchoolName());
                }
                
            }
        }
        list_detail = (ListView) findViewById(R.id.list_detail);
        list_searchView = (ListView) findViewById(R.id.list_search);
        left = (Button) findViewById(R.id.goto_left);
        right = (Button) findViewById(R.id.goto_right);
        title = (TextView) findViewById(R.id.title);
        classs = (TextView) findViewById(R.id.classs);
//        clear_edit = (ImageView) findViewById(R.id.kaoqin_clear_edit);
//        edit_student = (EditText) findViewById(R.id.edit_student);
        
        list_detail.setVisibility(View.VISIBLE);
        list_searchView.setVisibility(View.GONE);
        if (flag.equals("4"))
        {
            classs.setText("老师");
//            edit_student.setHint("搜索老师");
        }
//        clear_edit.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                edit_student.setText("");
////                edit_student.requestFocus();
////                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
////                inputManager.restartInput(edit_student);
//
//            }
//        });
//        contanct_search_frame_layout = (FrameLayout) findViewById(R.id.kaoqin_search_frame_layout);
        
//        contanct_search_frame_layout.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View arg0)
//            {
//                contanct_search_frame_layout.setVisibility(View.GONE);
////                edit_student.setFocusable(true);
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        });
        
//        edit_student.addTextChangedListener(new TextWatcher()
//        {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count)
//            {
//                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//
//                filterData(s.toString());
//                if (s.length() > 0)
//                {
//                    clear_edit.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    clear_edit.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after)
//            {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//                edit_student.setFocusable(true);
//            }
//        });
        
        attendanceNums = new ArrayList<>();
        data = new ArrayList<>();
        detailadapter = new DetailApdater(this, data);
        list_detail.setAdapter(detailadapter);
        adapter = new DetailApdater(this, attendanceNums);
        list_searchView.setAdapter(adapter);
        findViewById(R.id.back).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                finish();
            }
        });
        
        left.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (selectorNum > 0)
                {
                    selectorNum -= 1;
                    if (selectorNum < 0)
                    {
                        selectorNum = 0;
                    }
//                    edit_student.setText("");
                    if (flag.equals("4"))
                    {
                        schoolId = attNums.get(selectorNum).getSchoolId();
                        changeTitleTag(left, right);
//                        changeSelectedName(names.get(selectorNum));
                        //						 onRefreshInfo(clazzId, 1);
                        initData("");
                    }
                    else
                    {
                        
                        clazzId = mClassRooms.get(selectorNum).getId();
                        onRefreshInfo(clazzId, 1);
                    }
                }
//                ClickLogUtil.getInstance(DetailsClassStatisticsActivity.this).saveClickLog(ICheckingOnWorkPageEvent.CKClassLeftBtn, "向左切换班级按钮", "", "", "");
            }
        });
        
        right.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (selectorNum < names.size() - 1)
                {
                    selectorNum += 1;
                    if (selectorNum >= names.size())
                    {
                        selectorNum = names.size() - 1;
                    }
//                    edit_student.setText("");
                    if (flag.equals("4"))
                    {
                        schoolId = attNums.get(selectorNum).getSchoolId();
                        changeTitleTag(left, right);
//                        changeSelectedName(names.get(selectorNum));
                        //						 onRefreshInfo(clazzId, 1);
                        initData("");
                    }
                    else
                    {
                        
                        clazzId = mClassRooms.get(selectorNum).getId();
                        onRefreshInfo(clazzId, 1);
                    }
                }
//                ClickLogUtil.getInstance(DetailsClassStatisticsActivity.this).saveClickLog(ICheckingOnWorkPageEvent.CKClassRightBtn, "向右切换班级按钮", "", "", "");
            }
            
        });
        initClassRoom();
    }
    
    @SuppressLint("DefaultLocale")
    public void filterData(String filterStr)
    {
        List<AttendanceDetail> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr))
        {
            filterDateList = attendanceNums;
            if (list_searchView != null)
            {
                list_detail.setVisibility(View.VISIBLE);
                list_searchView.setVisibility(View.GONE);
            }
        }
        else
        {
            if (list_searchView != null)
            {
                list_detail.setVisibility(View.GONE);
                list_searchView.setVisibility(View.VISIBLE);
            }
            filterDateList.clear();
            for (AttendanceDetail contact : attendanceNums)
            {
                String name = contact.getUserName();
//                if (name.toUpperCase().indexOf(filterStr.toString()
//                        .toUpperCase()) != -1
//                        || CharacterParser.getInstance()
//                                .getSelling(name)
//                                .toUpperCase()
//                                .contains(filterStr.toString().toUpperCase()))
//                {
//                    filterDateList.add(contact);
//                }
            }
        }
        
        // 根据a-z进行排序
        //		Collections.sort(filterDateList, new PinyinComparator());
        adapter.setDatas(filterDateList);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
//        new LoadContacts().execute();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing())
        {
//            BaseApplication.getInstance().cancelPendingRequests(TAG);
        }
    }
    
    private void initClassRoom()
    {

    }
    
//    private class LoadContacts extends AsyncTask<Integer, Void, Boolean>
//    {
//        boolean isReset = false;
//        
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//        }
//        
//        @Override
//        protected Boolean doInBackground(Integer... params)
//        {
//            DataHelper helper = getDBHelper();
//            DataHelper.getHelper(DetailsClassStatisticsActivity.this);
//            String loginName = BaseApplication.getInstance()
//                    .getDefaultAccount()
//                    .getLoginname();
//            try
//            {
//                QueryBuilder<ClassRoom, Integer> classroomBuilder = helper.getClassRoomData()
//                        .queryBuilder();
//                classroomBuilder.where().eq("loginName", loginName);
//                // mClassRooms = classroomBuilder.query();
//                List<ClassRoom> newClassRoom = classroomBuilder.query();
//                // 如果班级数为空或者班级数量与新获取到得不同，那么就刷新界面
//                if (mClassRooms == null
//                        || (newClassRoom != null && mClassRooms.size() != newClassRoom.size()))
//                {
//                    mClassRooms = newClassRoom;
//                    isReset = true;
//                }
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        
//        @Override
//        protected void onPostExecute(Boolean result)
//        {
//            try
//            {
//                if (isReset)
//                {
//                    if (mClassRooms == null)
//                    {
//                        mClassRooms = new ArrayList<ClassRoom>();
//                    }
//                    if (mClassRooms.size() > 0)
//                    {
//                        clazzs = new String[mClassRooms.size()];
//                        names = new ArrayList<String>();
//                        
//                        clazzId = mClassRooms.get(selectorNum).getId();
//                        
//                        //						attenAdapter.setClazzId(clazzId);
//                        for (int i = 0; i < mClassRooms.size(); i++)
//                        {
//                            clazzs[i] = mClassRooms.get(i).getName();
//                            names.add(StringUtils.isEmptyOrNull(mClassRooms.get(i)
//                                    .getName()) ? "无名班级" : mClassRooms.get(i)
//                                    .getName());
//                        }
//                        // setTitleMoreName(names, new TabOnClickListener());
//                        setTitleSelecedName(names);
//                        
//                        // refreshKqCountData(today);
//                        if (names != null && names.size() > 0
//                                && names.size() > 1)
//                        {
//                            refreshLayout(0, true);
//                            // m_contentPager
//                            // .setOnPageChangeListener(new
//                            // TabPageChangeListener());
//                        }
//                        else
//                        {
//                            refreshLayout(0, false);
//                        }
//                    }
//                    else
//                    {
//                        
//                        title.setText("考勤");
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        
//    }
    
    private void refreshLayout(int temp, boolean b)
    {
        selectorNum = temp;
        
        if (flag.equals("4"))
        {
            names.clear();
            if (null != schools && schools.size() > 0)
            {
                names.addAll(schools);
                
                for (int i = 0; i < attNums.size(); i++)
                {
                    if (attNums.get(i).getSchoolId().equals(schoolId))
                    {
                        selectorNum = i;
                    }
                }
                
            }
//            changeSelectedName(names.get(selectorNum));
            changeTitleTag(left, right);
            initData("");
        }
        else
        {
            if (null == clazzIdIntent || "".equals(clazzIdIntent))
            {
                clazzId = mClassRooms.get(selectorNum).getId();
            }
            else
            {
                clazzId = Long.parseLong(clazzIdIntent);
                for (int i = 0; i < mClassRooms.size(); i++)
                {
                    if (mClassRooms.get(i).getId() == clazzId)
                    {
                        selectorNum = i;
                    }
                }
                
                clazzIdIntent = null;
            }
//            changeSelectedName(names.get(selectorNum));
            onRefreshInfo(clazzId, 1);
        }
    }
    
    private void onRefreshInfo(long clazzId, int topIndex)
    {
        changeTitleTag(left, right);
//        changeSelectedIcon(mClassRooms.get(selectorNum).getPlatform());
        if (Integer.parseInt(mClassRooms.get(selectorNum).getPlatform()) == 1)
        {
            
//            changeSelectedName(names.get(selectorNum) + "("
//                    + mClassRooms.get(selectorNum).getId() + ")");
        }
        else
        {
//            changeSelectedName(names.get(selectorNum));
        }
        this.clazzId = clazzId;
        
        initData(clazzId + "");
    }
    
    private void changeTitleTag(Button left, Button right)
    {
        if (selectorNum == 0)
        {
            left.setBackgroundResource(R.drawable.left1);
        }
        else
        {
            left.setBackgroundResource(R.drawable.left2);
        }
        if (selectorNum == names.size() - 1)
        {
            right.setBackgroundResource(R.drawable.right1);
        }
        else
        {
            right.setBackgroundResource(R.drawable.right2);
        }
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
//            case R.id.seacher:
                
//                break;
            
            default:
                break;
        }
        
    }
    
    class DetailApdater extends BaseAdapter
    {
        
        private Context context;
        
        private List<AttendanceDetail> list;
        
        public DetailApdater(Context context, List<AttendanceDetail> list)
        {
            this.context = context;
            this.list = list;
        }
        
        @Override
        public int getCount()
        {
            
            return list.size();
        }
        
        public void setDatas(List<AttendanceDetail> contactList)
        {
            
            list = contactList;
            notifyDataSetChanged();
        }
        
        @Override
        public Object getItem(int position)
        {
            
            return list.get(position);
        }
        
        public void addAll(List<AttendanceDetail> list, boolean append)
        {
            
            this.list = list;
            notifyDataSetChanged();
        }
        
        @Override
        public long getItemId(int position)
        {
            
            return position;
        }
        
        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                //				context
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.statistics_detail_item, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.total_day);
                holder.textView2 = (TextView) convertView.findViewById(R.id.push_carded);
                holder.textView3 = (TextView) convertView.findViewById(R.id.ask_Leave);
                holder.textView4 = (TextView) convertView.findViewById(R.id.absence);
                holder.textView5 = (TextView) convertView.findViewById(R.id.noabsence);
                holder.item_name1 = (TextView) convertView.findViewById(R.id.item_name1);
                holder.user_avater = (CircularImage) convertView.findViewById(R.id.user_avater);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.get(position).getUserName().equals("null"))
            {
                
                holder.item_name1.setText("");
            }
            else
            {
                holder.item_name1.setText(list.get(position).getUserName());
            }
            holder.textView1.setText(list.get(position).getTotalDays());
            holder.textView2.setText(list.get(position).getComeNum());
            holder.textView3.setText(list.get(position).getVacateNum());
            holder.textView4.setText(list.get(position).getNocomeNum());
            holder.textView5.setText(list.get(position).getAttendanceRate());
//            imageLoader.displayImage(list.get(position).getPicture(),
//                    holder.user_avater,
//                    BaseApplication.getInstance().defaultOptions);
            return convertView;
        }
        
    }
    
    class ViewHolder
    {
        TextView textView1;
        
        TextView textView2;
        
        TextView textView3;
        
        TextView textView4;
        
        TextView textView5;

        TextView item_name1;
        
        CircularImage user_avater;
    }
    
    private void initData(String classId)
    {
        
//        ProgressDialogUtils.showProgressDialog("", this);
//
//        HashMap<String, String> params = new HashMap<String, String>();
//
//        params.put("commandtype", "getAttendanceInfo");
//        params.put("userType", BaseApplication.getInstance().getDefUserType());
//        params.put("type", type);
//        if (flag.equals("4"))
//        {
//
//            params.put("schoolId", schoolId);
//            params.put("attendanceType", "2");
//
//        }
//        else
//        {
//
//            params.put("classId", classId);
//            params.put("attendanceType", "1");
//        }
//        //	  params.put("attendanceType", "1");
//
//        WDJsonObjectRequest mRequest = new WDJsonObjectRequest(
//                Consts.SERVER_URL + "/v2/getAttendanceInfo",
//                Request.Method.POST, params, true,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        ProgressDialogUtils.dismissProgressBar();
        try {
            if (null != attendanceNums) {
                attendanceNums.clear();
                data.clear();
            }
            String str = "{\"ret\":0,\"totalAttendanceRate\":\"23.00%\",\"data\":[{\"schoolId\":0,\"schoolName\":null,\"classId\":10605548,\"totalNum\":28,\"isCheck\":0,\"comeNum\":0,\"vacateNum\":0,\"nocomeNum\":28,\"attendanceRateValue\":0.0,\"attendanceRate\":\"0.00%\",\"className\":\"幼班幼班1班\"}],\"msg\":\"成功\"}";
            JSONObject response = new JSONObject(str);
            System.out.println(response);
            if (response.optInt("ret") == 0) {
                List<AttendanceDetail> details = AttendanceDetail.paserAll(response);
                System.out.println(details.size());
                if (null != details && details.size() > 0) {

                    data.addAll(details);
                    attendanceNums.addAll(details);
                    adapter.notifyDataSetChanged();
                    detailadapter.notifyDataSetChanged();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
//                        else
//                        {
//                            T.showShort(DetailsClassStatisticsActivity.this,
//                                    response.optString("msg"));
//                        }
//                    }
//                }, new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError arg0)
//                    {
//                        ProgressDialogUtils.dismissProgressBar();
//
//                        StatusUtils.handleError(arg0,
//                                DetailsClassStatisticsActivity.this);
//                    }
//                });
//        BaseApplication.getInstance().addToRequestQueue(mRequest,
//                TAG);
        
    }

}
