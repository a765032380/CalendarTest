package com.xq.calendartest.entity;

/**
 * Created by Administrator on 2017/7/17.
 */

public class MonthTimeEntity {

    private int year;         //该月份 属于哪一年
    private int month;        // 该月 是哪一个月份


    public MonthTimeEntity(){}

    public MonthTimeEntity(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
