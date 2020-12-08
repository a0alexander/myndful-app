package com.example.mindfulapp_;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import android.widget.Toast;

import androidx.room.Room;

import java.util.Calendar;


public class MyAlarm extends BroadcastReceiver {

    taskDatabase db;

    tasktable tasktable_object_auto, tasktable_object_auto2;




    @Override
    public void onReceive(Context context, Intent intent) {
        db = Room.databaseBuilder(context,taskDatabase.class,"dbtask")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_MONTH);

        tasktable_object_auto = new tasktable("15 min mindfulness breathing",false, today,
                "autotask");
        tasktable_object_auto2 = new tasktable("15 min bedtime retrospection",false,today,
                "autotask");

        //Toast.makeText(context, "new", Toast.LENGTH_SHORT).show();

        if(db.taskDao().getLastDate()!=today && db.taskDao().getTaskType().equals("usertask")){

            db.taskDao().addTask(tasktable_object_auto);
            db.taskDao().addTask(tasktable_object_auto2);


        }













    }
}
