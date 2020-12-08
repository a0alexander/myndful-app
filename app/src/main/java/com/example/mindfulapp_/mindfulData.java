package com.example.mindfulapp_;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_table")
public class mindfulData {


    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "mindful_Time")
    private long mindfulTime;

    @ColumnInfo(name = "time_for_Day")
    private int dayLog;

    @ColumnInfo(name = "time_for_Weeks")
    private int weekLog;

    @ColumnInfo(name = "time_for_Month")
    private int monthLog;

    public mindfulData(long mindfulTime, int dayLog, int weekLog, int monthLog) {
        this.mindfulTime = mindfulTime;
        this.dayLog = dayLog;
        this.weekLog = weekLog;
        this.monthLog = monthLog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMindfulTime() {
        return mindfulTime;
    }

    public void setMindfulTime(long mindfulTime) {
        this.mindfulTime = mindfulTime;
    }

    public int getDayLog() {
        return dayLog;
    }

    public void setDayLog(int dayLog) {
        this.dayLog = dayLog;
    }

    public int getWeekLog() {
        return weekLog;
    }

    public void setWeekLog(int weekLog) {
        this.weekLog = weekLog;
    }

    public int getMonthLog() {
        return monthLog;
    }

    public void setMonthLog(int monthLog) {
        this.monthLog = monthLog;
    }
}




//second database code for the table

@Entity(tableName = "task_table")
class tasktable{

    @PrimaryKey(autoGenerate = true)
    int id;




    @ColumnInfo(name = "task_desc")
    private String task_desc;


    @ColumnInfo(name = "status")
    private boolean task_status;

    @ColumnInfo(name = "date_for_todo")
    private int date_;

    @ColumnInfo(name = "task_type")
    private String task_type;


    public tasktable(String task_desc, boolean task_status, int date_, String task_type) {
        this.task_desc = task_desc;
        this.task_status = task_status;
        this.date_ = date_;
        this.task_type = task_type;
    }

    public int getDate_() {
        return date_;
    }

    public void setDate_(int date_) {
        this.date_ = date_;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public boolean isTask_status() {
        return task_status;
    }

    public void setTask_status(boolean task_status) {
        this.task_status = task_status;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }







}



