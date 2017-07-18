package com.xq.calendartest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xq.calendartest.entity.DayTimeEntity;
import com.xq.calendartest.entity.MonthTimeEntity;
import com.xq.calendartest.entity.UpdataCalendar;
import com.xq.calendartest.utils.SharePreferences;

import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/7/17.
 */
public class MonthTimeActivity extends Activity {
private ImageButton back;
private TextView startTime;          //开始时间
private TextView stopTime;           //结束时间

private RecyclerView reycycler;
private MonthTimeAdapter adapter;
private ArrayList<MonthTimeEntity> datas;


public static DayTimeEntity startDay;
public static DayTimeEntity stopDay;

SharePreferences isPreferences;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPreferences = new SharePreferences(this);

        initView();
        initData();

        EventBus.getDefault().register(this);


        }

private void initData() {
        startDay = new DayTimeEntity(0,0,0,0);
        stopDay = new DayTimeEntity(-1,-1,-1,-1);
        datas = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;

        datas.add(new MonthTimeEntity(year,month));                //


        c.add(Calendar.MONTH,1);

        for (int i=0;i<3;i++){
                MonthTimeEntity monthTimeEntity = new MonthTimeEntity();
                if(month>=12){//明年
                        year = c.get(Calendar.YEAR)+1;
                        month = 1;
                        monthTimeEntity.setYear(year);
                        monthTimeEntity.setMonth(month);
                }else{
                        month = month +1;
                        monthTimeEntity.setYear(year);
                        monthTimeEntity.setMonth(month);
                }

                datas.add(monthTimeEntity);

        }

        adapter = new MonthTimeAdapter(datas, MonthTimeActivity.this);
        reycycler.setAdapter(adapter);

        adapter.setOnDayItemClickListener(new MonthTimeAdapter.OnDayItemClickListener() {
                @Override
                public void OnDayItemClick(View view, int position) {
                        Toast.makeText(MonthTimeActivity.this , "选择日期合法" , Toast.LENGTH_SHORT).show();


                        isPreferences.updateSp("start_month_position" , MonthTimeActivity.startDay.getMonthPosition());
                        isPreferences.updateSp("start_day_position" , MonthTimeActivity.startDay.getDayPosition());
                        isPreferences.updateSp("end_month_position" , MonthTimeActivity.stopDay.getMonthPosition());
                        isPreferences.updateSp("end_day_position" , MonthTimeActivity.stopDay.getDayPosition());

                        isPreferences.updateSp("start_year" , startDay.getYear());
                        isPreferences.updateSp("start_month" ,startDay.getMonth());
                        isPreferences.updateSp("start_day" , startDay.getDay());

                        isPreferences.updateSp("end_year" , stopDay.getYear());
                        isPreferences.updateSp("end_month" , stopDay.getMonth());
                        isPreferences.updateSp("end_day" , stopDay.getDay());


                        Intent i = new Intent();
                        i.putExtra("start_year" , startDay.getYear());
                        i.putExtra("start_month" , startDay.getMonth());
                        i.putExtra("start_day" , startDay.getDay());

                        i.putExtra("end_year" , stopDay.getYear());
                        i.putExtra("end_month" , stopDay.getMonth());
                        i.putExtra("end_day" , stopDay.getDay());
                        setResult(MainActivity.RESULT_OK, i);
                        finish();

                }
        });

        }

        private void initView() {
                startTime = (TextView) findViewById(R.id.plan_time_txt_start);
                stopTime = (TextView) findViewById(R.id.plan_time_txt_stop);

                reycycler = (RecyclerView) findViewById(R.id.plan_time_calender);
                LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,   // 上下文
                LinearLayout.VERTICAL,  //垂直布局,
                false);

                reycycler.setLayoutManager(layoutManager);

                MoveToPosition(layoutManager,reycycler,isPreferences.getSp().getInt("start_month_position",0));

                }

        //直接显示第n个item内容
        public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
                int firstItem = manager.findFirstVisibleItemPosition();
                int lastItem = manager.findLastVisibleItemPosition();
                if (n <= firstItem) {
                        mRecyclerView.scrollToPosition(n);
                } else if (n <= lastItem) {
                        int top = mRecyclerView.getChildAt(n - firstItem).getTop();
                        mRecyclerView.scrollBy(0, top);
                } else {
                        mRecyclerView.scrollToPosition(n);
                }

        }


        public void onEventMainThread(UpdataCalendar event) {

                adapter.notifyDataSetChanged();


//                if (reycycler.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || (reycycler.isComputingLayout() == false)) {
//
//                }
//                startTime.setText(startDay.getMonth()+"月"+startDay.getDay()+"日"+"\n");
//                if (stopDay.getDay() == -1) {
//                        stopTime.setText("结束"+"\n"+"时间");
//                }else{
//                        stopTime.setText(stopDay.getMonth() + "月" + stopDay.getDay() + "日" + "\n");
//                }
        }



        @Override
protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        }
        }