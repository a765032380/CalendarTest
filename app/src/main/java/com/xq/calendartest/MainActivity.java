package com.xq.calendartest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xq.calendartest.utils.SharePreferences;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements  View.OnClickListener{


    public static final int REQUEST_CODE = 1;
    TextView tv_get_date,tv_start_time,tv_end_time;

    SharePreferences isPreferences;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
          isPreferences=new SharePreferences(this);
         initViews();

    }

    private void initViews(){
        tv_get_date = (TextView) findViewById(R.id.tv_get_date);
        tv_get_date.setOnClickListener(this);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);

        tv_start_time.setText("入住时间:"+isPreferences.getSp().getInt("start_year",0)+"年"+isPreferences.getSp().getInt("start_month",0)+"月"+isPreferences.getSp().getInt("start_day",0)+"日");
        tv_end_time.setText("离开时间:"+isPreferences.getSp().getInt("end_year",0)+"年"+isPreferences.getSp().getInt("end_month",0)+"月"+isPreferences.getSp().getInt("end_day",0)+"日");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Calendar calendar = Calendar.getInstance();

            int  start_year = data.getIntExtra("start_year" ,calendar.get(Calendar.YEAR));
            int start_month = data.getIntExtra("start_month", calendar.get(Calendar.MONTH)+1);
            int start_day = data.getIntExtra("start_day" , calendar.get(Calendar.DAY_OF_MONTH));

            int end_year = data.getIntExtra("end_year" ,calendar.get(Calendar.YEAR));
            int end_month = data.getIntExtra("end_month" , calendar.get(Calendar.MONTH)+1);
            int end_day = data.getIntExtra("end_day" , calendar.get(Calendar.DAY_OF_MONTH)+1);

            tv_start_time.setText("入住时间:"+start_year+"年"+start_month+"月"+start_day+"日");
            tv_end_time.setText("离开时间:"+end_year+"年"+end_month+"月"+end_day+"日");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_date:

                startActivityForResult(new Intent(MainActivity.this, MonthTimeActivity.class),REQUEST_CODE);

                break;
        }
    }
}



