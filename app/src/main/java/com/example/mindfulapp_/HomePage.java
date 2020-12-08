package com.example.mindfulapp_;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class HomePage extends AppCompatActivity {


    Button newSession, myTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        newSession = findViewById(R.id.newSession);
        myTasks = findViewById(R.id.myTasks);




        newSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        myTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, todoView.class);
                startActivity(intent);
            }
        });


        Calendar c = Calendar.getInstance();

        c.set(
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                4, 00,
                0);


        SharedPreferences prefs = getSharedPreferences("Alarm_Prefs1", MODE_PRIVATE);
        String alarm_enabled = prefs.getString("alarm_enabled", "alarm_not_set");//"No name defined" is the default value.


        if(alarm_enabled.equals("alarm_not_set")) {
            setAlarm(c.getTimeInMillis());

        }
        else if (alarm_enabled.equals("alarm_set")){
           // Toast.makeText(this, "alarm enabled already", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();

        }

        findViewById(R.id.clearprefs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("Alarm_Prefs1", MODE_PRIVATE).edit();
                editor.putString("alarm_enabled", "alarm_not_set");

                editor.apply();
            }
        });
        Button clearprefs = findViewById(R.id.clearprefs);

        prefs = getSharedPreferences("dev_prefs", MODE_PRIVATE);
        String dev_enabled = prefs.getString("devmode", "disabled");

        if(dev_enabled.equals("enabled")){

            clearprefs.setVisibility(View.VISIBLE);
        }
        else{
            clearprefs.setVisibility(View.GONE);
        }




    }


    @Override
    protected void onResume() {
        super.onResume();
        Button clearprefs = findViewById(R.id.clearprefs);

        SharedPreferences prefs = getSharedPreferences("dev_prefs", MODE_PRIVATE);
        String dev_enabled = prefs.getString("devmode", "disabled");

        if(dev_enabled.equals("enabled")){

            clearprefs.setVisibility(View.VISIBLE);
        }
        else{
            clearprefs.setVisibility(View.GONE);
        }


    }

    private void setAlarm(long timeInMillis) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(HomePage.this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePage.this ,0,intent,0);
//                alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);

        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);

       // Toast.makeText(HomePage.this, "alarm is set"+timeInMillis, Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = getSharedPreferences("Alarm_Prefs1", MODE_PRIVATE).edit();
        editor.putString("alarm_enabled", "alarm_set");

        editor.apply();

    }







    }

