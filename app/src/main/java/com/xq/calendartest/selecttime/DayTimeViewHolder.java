package com.xq.calendartest.selecttime;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xq.calendartest.R;

/**
 * Created by Administrator on 2017/7/17.
 */

public class DayTimeViewHolder extends RecyclerView.ViewHolder {
    public TextView select_txt_day,select_type;      //日期文本
    public LinearLayout select_ly_day;       //父容器 ， 用于点击日期

    public DayTimeViewHolder(View itemView) {
        super(itemView);
        select_ly_day = (LinearLayout) itemView.findViewById(R.id.select_ly_day);
        select_txt_day = (TextView) itemView.findViewById(R.id.select_txt_day);
        select_type=itemView.findViewById(R.id.select_type);
    }

}
