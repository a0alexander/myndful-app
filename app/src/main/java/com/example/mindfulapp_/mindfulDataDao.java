package com.example.mindfulapp_;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface mindfulDataDao {


    @Insert
    public void addSession(mindfulData mindfulData);

    @Query("select * from time_table ORDER BY id DESC")
    List<mindfulData> getAllData();


    @Query("select SUM(mindful_Time) from time_table where time_for_Day=:day")
    public long get_Day_Total(int day);

    @Query("select SUM(mindful_Time) from time_table where time_for_Weeks=:week")
    public long get_Week_Total(int week);

    @Query("select SUM(mindful_Time) from time_table where time_for_Month=:month")
    public long get_Month_Total(int month);

    @Query("Delete from time_table")
    public void deleteAllData();



}

@Dao
interface  taskDao{

    @Insert
    public void addTask(tasktable tasktable);

    @Query("select * from task_table ORDER BY id DESC")
    List<tasktable> getAllData();

    @Query("update task_table set status =:status where id=:id")
    void updateStatus(boolean status, int id);


    @Query("select * from task_table where status =:stat ORDER BY id DESC ")
    List<tasktable> getProgress(boolean stat);

    @Query("select date_for_todo from task_table ORDER BY id DESC LIMIT 1")
    public int getLastDate();

    @Query("select task_type from task_table ORDER BY id DESC LIMIT 1")
    public String getTaskType();

    @Query("update task_table set task_type=:tasktype where id=:id")
    public void setStatus(int id, String tasktype);




}
