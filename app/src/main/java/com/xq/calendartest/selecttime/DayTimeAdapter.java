package com.xq.calendartest.selecttime;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xq.calendartest.R;
import com.xq.calendartest.entity.DayTimeEntity;
import com.xq.calendartest.entity.UpdataCalendar;
import com.xq.calendartest.utils.SharePreferences;

import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

import static com.xq.calendartest.MonthTimeActivity.startDay;
import static com.xq.calendartest.MonthTimeActivity.stopDay;

/**
 * Created by Administrator on 2017/7/17.
 */

public class DayTimeAdapter extends RecyclerView.Adapter<DayTimeViewHolder>{

     public interface  onItemClickListener{
         void onItemCallback(View view ,int position );
     }

    onItemClickListener mOnItemClickListener;

    SharePreferences isPreferences;
    public void setOnItemCliCkListener(onItemClickListener OnItemClickListener){
        this.mOnItemClickListener = OnItemClickListener;
    }

        private ArrayList<DayTimeEntity> days;
        private Context context;

        public DayTimeAdapter(ArrayList<DayTimeEntity> days, Context context) {
            this.days = days;
            this.context = context;
            isPreferences = new SharePreferences(context);
        }

        @Override
        public DayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            DayTimeViewHolder ret = null;
            // 不需要检查是否复用，因为只要进入此方法，必然没有复用
            // 因为RecyclerView 通过Holder检查复用
            View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_selectday, parent, false);
            ret = new DayTimeViewHolder(v);

            return ret;
        }

        @Override
        public void onBindViewHolder(final DayTimeViewHolder holder, final int position) {
            final DayTimeEntity dayTimeEntity = days.get(position);
            //显示日期
            if (dayTimeEntity.getDay()!=0) {
                holder.select_txt_day.setText(dayTimeEntity.getDay() + "");
                holder.select_ly_day.setEnabled(true);
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH)+1;
                if(month == dayTimeEntity.getMonth() &&  day > dayTimeEntity.getDay() ){
                    holder.select_txt_day.setTextColor(Color.parseColor("#B5B5B5"));
                    holder.select_ly_day.setEnabled(false);
                }else{
                    holder.select_txt_day.setTextColor(Color.parseColor("#555555"));

                    holder.select_ly_day.setEnabled(true);
                }
                if ((position+1)%7==1||(position+1)%7==0){
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.weekend));
                }
            }else{
                holder.select_ly_day.setEnabled(false);
            }




//            if(isPreferences.getSp().getInt("start_month_position" , -1)!=-1 && isPreferences.getSp().getInt("start_day_position" , -1)!=-1
//                    && isPreferences.getSp().getInt("end_month_position" , -1) !=-1 && isPreferences.getSp().getInt("end_month_position" , -1)!=-1){
//
//                EventBus.getDefault().post(new UpdataCalendar()); // 发消息刷新适配器，目的为了显示日历上各个日期的背景颜色
//
//                holder.select_ly_day.setBackgroundResource(R.color.blue);
//            }


            holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(isPreferences.getSp().getInt("start_day_position", -1)!=-1 && isPreferences.getSp().getInt("end_day_position" , -1)!=-1){

//                        Log.d("TAG" ,"时间下标："+ isPreferences.getSp().getInt("start_month_position" , -1)+","+isPreferences.getSp().getInt("start_day_position" , -1)
//                                +", "+ isPreferences.getSp().getInt("end_month_position" , -1) +"," + isPreferences.getSp().getInt("end_day_position" , -1)
//                                +"," +isPreferences.getSp().getInt("start_day" , 0)+" , "+isPreferences.getSp().getInt("end_day" , 0) );

                        startDay.setDay(0);           // 该item 天数的 年月日等信息  赋给  开始日期
                        startDay.setMonth(0);
                        startDay.setYear(0);
                        startDay.setMonthPosition(0);
                        startDay.setDayPosition(0);
                        stopDay.setDay(0);
                        stopDay.setMonth(0);
                        stopDay.setYear(-1);
                        stopDay.setMonthPosition(0);
                        stopDay.setDayPosition(0);
                        isPreferences.updateSp("start_day_position" , -1);
                        isPreferences.updateSp("end_day_position" , -1);


                        isPreferences.updateSp("start_year" , 0);
                        isPreferences.updateSp("start_month" ,0);
                        isPreferences.updateSp("start_day" , 0);

                        isPreferences.updateSp("end_year" , 0);
                        isPreferences.updateSp("end_month" , 0);
                        isPreferences.updateSp("end_day" , 0);

                    }

                    if (startDay.getYear() == 0 ){          // 第一次点击开始的位置，因为开始默认参数是 0,0,0,0
                        startDay.setDay(dayTimeEntity.getDay());           // 该item 天数的 年月日等信息  赋给  开始日期
                        startDay.setMonth(dayTimeEntity.getMonth());
                        startDay.setYear(dayTimeEntity.getYear());
                        startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                        startDay.setDayPosition(position);
                    }else if(startDay.getYear()>0 && stopDay.getYear() ==-1){      //已经点击了开始 ，点击结束位置，（默认结束位置-1,-1,-1,-1 说明还没有点击结束位置）
                        if (dayTimeEntity.getYear()> startDay.getYear()) {
                            //如果选中的年份大于开始的年份，说明结束日期肯定大于开始日期 ，合法的 ，将该item的天数的 信息  赋给 结束日期
                            stopDay.setDay(dayTimeEntity.getDay());
                            stopDay.setMonth(dayTimeEntity.getMonth());
                            stopDay.setYear(dayTimeEntity.getYear());
                            stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                            stopDay.setDayPosition(position);
                            mOnItemClickListener.onItemCallback(holder.itemView,position);

                        }else if (dayTimeEntity.getYear() == startDay.getYear()){
                            //如果选中的年份 等于 选中的年份
                            if (dayTimeEntity.getMonth()> startDay.getMonth()){
                                //如果改item的天数的月份大于开始日期的月份，说明结束日期肯定大于开始日期 ，合法的 ，将该item的天数的 信息  赋给 结束日期
                                stopDay.setDay(dayTimeEntity.getDay());
                                stopDay.setMonth(dayTimeEntity.getMonth());
                                stopDay.setYear(dayTimeEntity.getYear());
                                stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                stopDay.setDayPosition(position);
                                mOnItemClickListener.onItemCallback(holder.itemView,position);

                            }else if(dayTimeEntity.getMonth() == startDay.getMonth()){
                                //年份月份 都相等
                                if (dayTimeEntity.getDay() > startDay.getDay()){
                                    //判断天数 ，如果 该item的天数的 日子大于等于 开始日期的 日子 ，说明结束日期合法的 ，将该item的天数的 信息  赋给 结束日期
                                    stopDay.setDay(dayTimeEntity.getDay());
                                    stopDay.setMonth(dayTimeEntity.getMonth());
                                    stopDay.setYear(dayTimeEntity.getYear());
                                    stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                    stopDay.setDayPosition(position);
                                    mOnItemClickListener.onItemCallback(holder.itemView,position);
                                }else{
                                    //天数小与初始  从新选择开始  ，结束日期重置，开始日期为当前的位置的天数的信息
                                    startDay.setDay(dayTimeEntity.getDay());
                                    startDay.setMonth(dayTimeEntity.getMonth());
                                    startDay.setYear(dayTimeEntity.getYear());
                                    startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                    startDay.setDayPosition(position);
                                    stopDay.setDay(-1);
                                    stopDay.setMonth(-1);
                                    stopDay.setYear(-1);
                                    stopDay.setMonthPosition(-1);
                                    stopDay.setDayPosition(-1);
                                }
                            }else {
                                //选中的月份 比开始日期的月份还小，说明 结束位置不合法，结束日期重置，开始日期为当前的位置的天数的信息
                                startDay.setDay(dayTimeEntity.getDay());
                                startDay.setMonth(dayTimeEntity.getMonth());
                                startDay.setYear(dayTimeEntity.getYear());
                                startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                startDay.setDayPosition(position);
                                stopDay.setDay(-1);
                                stopDay.setMonth(-1);
                                stopDay.setYear(-1);
                                stopDay.setMonthPosition(-1);
                                stopDay.setDayPosition(-1);
                            }

                        }else{
                            //选中的年份 比开始日期的年份还小，说明 结束位置不合法，结束日期重置，开始日期为当前的位置的天数的信息
                            startDay.setDay(dayTimeEntity.getDay());
                            startDay.setMonth(dayTimeEntity.getMonth());
                            startDay.setYear(dayTimeEntity.getYear());
                            startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                            startDay.setDayPosition(position);
                            stopDay.setDay(-1);
                            stopDay.setMonth(-1);
                            stopDay.setYear(-1);
                            stopDay.setMonthPosition(-1);
                            stopDay.setDayPosition(-1);
                        }
                    }else if(startDay.getYear()>0 && startDay.getYear()>1){      //已经点击开始和结束   第三次点击 ，重新点击开始
                        startDay.setDay(dayTimeEntity.getDay());
                        startDay.setMonth(dayTimeEntity.getMonth());
                        startDay.setYear(dayTimeEntity.getYear());
                        startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                        startDay.setDayPosition(position);
                        stopDay.setDay(-1);
                        stopDay.setMonth(-1);
                        stopDay.setYear(-1);
                        stopDay.setMonthPosition(-1);
                        stopDay.setDayPosition(-1);
                    }

                    notifyDataSetChanged();
//                    EventBus.getDefault().post(new UpdataCalendar()); // 发消息刷新适配器，目的为了显示日历上各个日期的背景颜色
                }
            });


            if(isPreferences.getSp().getInt("start_month_position" , -1)!=-1 && isPreferences.getSp().getInt("start_day_position" , -1)!=-1
                    && isPreferences.getSp().getInt("end_month_position" , -1) !=-1 && isPreferences.getSp().getInt("end_day_position" , -1)!=-1){

                startDay.setYear(isPreferences.getSp().getInt("start_year" , -1));
                startDay.setMonth(isPreferences.getSp().getInt("start_month" , -1));
                startDay.setDay(isPreferences.getSp().getInt("start_day" , -1));
                startDay.setMonthPosition(isPreferences.getSp().getInt("start_month_position" , -1));
                startDay.setDayPosition(isPreferences.getSp().getInt("start_day_position" , -1));

                stopDay.setYear(isPreferences.getSp().getInt("end_year" , -1));
                stopDay.setMonth(isPreferences.getSp().getInt("end_month" , -1));
                stopDay.setDay(isPreferences.getSp().getInt("end_day" , -1));
                stopDay.setMonthPosition(isPreferences.getSp().getInt("end_month_position" , -1));
                stopDay.setDayPosition(isPreferences.getSp().getInt("end_day_position" , -1));


//                EventBus.getDefault().post(new UpdataCalendar()); // 发消息刷新适配器，目的为了显示日历上各个日期的背景颜色

            }



            if (startDay.getYear()== dayTimeEntity.getYear() && startDay.getMonth() == dayTimeEntity.getMonth() && startDay.getDay() == dayTimeEntity.getDay()
                    && stopDay.getYear()== dayTimeEntity.getYear() && stopDay.getMonth() == dayTimeEntity.getMonth() && stopDay.getDay() == dayTimeEntity.getDay() ){
                //开始和结束同一天
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_startstop);

            }
            else if (startDay.getYear()== dayTimeEntity.getYear() && startDay.getMonth() == dayTimeEntity.getMonth() && startDay.getDay() == dayTimeEntity.getDay()){
                //该item是 开始日期
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_start);
                holder.select_type.setText("入住");
                holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));

            }else if(stopDay.getYear()== dayTimeEntity.getYear() && stopDay.getMonth() == dayTimeEntity.getMonth() && stopDay.getDay() == dayTimeEntity.getDay()){
                //该item是 结束日期
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_stop);
                holder.select_type.setText("离开");
                holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
            }else if(dayTimeEntity.getMonthPosition()>= startDay.getMonthPosition() && dayTimeEntity.getMonthPosition()<= stopDay.getMonthPosition()){
                //处于开始和结束之间的点
                if (dayTimeEntity.getMonthPosition()== startDay.getMonthPosition()&& dayTimeEntity.getMonthPosition()== stopDay.getMonthPosition()){
                    //开始和结束是一个月份
                    if (dayTimeEntity.getDay()> startDay.getDay() && dayTimeEntity.getDay() < stopDay.getDay()) {
                        holder.select_ly_day.setBackgroundResource(R.color.select);
                    }else{
                        holder.select_ly_day.setBackgroundResource(R.color.white);
                    }
                }else if(startDay.getMonthPosition() != stopDay.getMonthPosition()){
                    if (dayTimeEntity.getDay()!=0) {
                        // 日期和 开始 不是一个月份
                        if (dayTimeEntity.getMonthPosition() == startDay.getMonthPosition() && dayTimeEntity.getDay() > startDay.getDay()) {
                            //和初始相同月  天数往后
                            holder.select_ly_day.setBackgroundResource(R.color.select);
                        } else if (dayTimeEntity.getMonthPosition() == stopDay.getMonthPosition() && dayTimeEntity.getDay() < stopDay.getDay()) {
                            //和结束相同月   天数往前
                            holder.select_ly_day.setBackgroundResource(R.color.select);
                        } else if (dayTimeEntity.getMonthPosition() != startDay.getMonthPosition() && dayTimeEntity.getMonthPosition() != stopDay.getMonthPosition()) {
                            //和 开始结束都不是同一个月
                            holder.select_ly_day.setBackgroundResource(R.color.select);
                        } else {
                            holder.select_ly_day.setBackgroundResource(R.color.white);
                        }
                    }
                }

            }else{
                holder.select_ly_day.setBackgroundResource(R.color.white);
            }

        }

        @Override
        public int getItemCount() {
            int ret = 0;
            if (days!=null){
                ret = days.size();
            }
            return ret;
        }
}
