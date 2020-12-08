package com.example.mindfulapp_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MainActivity extends AppCompatActivity {



    Chronometer chronometer;
    boolean running;
    Button startButton;
    Button pauseButton;
    Button saveButton;
    Button resetButton;
    Button deleteAlldata;
    elapsedTime offset = new elapsedTime();
    public static mindfulDatabase mindDb;
    mindfulData mindObject;

    TextView dayTotalTime;
    TextView weektime;
    TextView monthtime;
    ObjectAnimator cardViewAnimator,chronoAnimator,chronoAnimatorX,clockAnimatorY,
                    chronoAnimatorY,
                    clockAnimator,clockAnimatorX;
    Button todoButton,devButton;



    View cardviews;
    View timeContainer;

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("dev_prefs", MODE_PRIVATE);
        String alarm_enabled = prefs.getString("devmode", "disabled");//"No name defined" is the default value.


        if(alarm_enabled.equals("enabled")){

            deleteAlldata.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            todoButton.setVisibility(View.VISIBLE);
           // Toast.makeText(this, "enabled", Toast.LENGTH_SHORT).show();
        }
        else if (alarm_enabled.equals("disabled")){
            deleteAlldata.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
            todoButton.setVisibility(View.GONE);

           // Toast.makeText(this, "disabled", Toast.LENGTH_SHORT).show();

        }

        else{
            deleteAlldata.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
            todoButton.setVisibility(View.GONE);
           // Toast.makeText(this, "default", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer1);
        startButton = findViewById(R.id.startBtn);
        pauseButton =findViewById(R.id.pauseBtn);
        saveButton =findViewById(R.id.saveBtn);
        resetButton = findViewById(R.id.resetBtn);
        deleteAlldata = findViewById(R.id.deleteAllDataBtn);
        dayTotalTime = findViewById(R.id.daytimeTextView);
        weektime = findViewById(R.id.weektimeTextView);
        monthtime = findViewById(R.id.monthtimeTextView);
        todoButton = findViewById(R.id.todoButton);
        devButton = findViewById(R.id.devmodeButton);
        running = false;
        offset.time=0;

        mindDb = Room.databaseBuilder(this,mindfulDatabase.class,"db")
                .allowMainThreadQueries().build();

        displayCounters();

        cardviews =findViewById(R.id.linearLayout);
        timeContainer=findViewById(R.id.gifImageView);

        cardviews.animate().translationY(-500f);
        timeContainer.animate().translationY(-250f).scaleX(1.5f).scaleY(1.5f);
        chronometer.animate().translationY(-250f).scaleX(1.5f).scaleY(1.5f);






        cardViewAnimator= ObjectAnimator.ofFloat(cardviews, "translationY",0f,-500f);
        cardViewAnimator.setDuration(700);


        clockAnimator = ObjectAnimator.ofFloat(timeContainer, "translationY",0f,-250f);
        clockAnimatorX = ObjectAnimator.ofFloat(timeContainer,"scaleX",1f,1.5f);
        clockAnimatorY = ObjectAnimator.ofFloat(timeContainer,"scaleY",1f,1.5f);
        clockAnimator .setDuration(700);



        chronoAnimator = ObjectAnimator.ofFloat(chronometer, "translationY",0f,-250f);
        chronoAnimatorX = ObjectAnimator.ofFloat(chronometer,"scaleX",1f,1.5f);
        chronoAnimatorY = ObjectAnimator.ofFloat(chronometer,"scaleY",1f,1.5f);
        chronoAnimatorY.setDuration(700);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AnticipateOvershootInterpolator());

            checkDate();






        final GifDrawable gifDrawable1;


        final getGif1 gt1 = new getGif1();

        try {
            gt1.gifDrawable =  new GifDrawable(getResources(), R.drawable.abc);
            timeContainer.setBackground(gt1.gifDrawable);
            gt1.gifDrawable.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final AnimationListener anim1  = new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {

                if(loopNumber==gt1.gifDrawable.getCurrentLoop())
                    gt1.gifDrawable.pause();
            }
        };


        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,todoView.class);
                startActivity(intent);


            }
        });










        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!running){
                    chronometer.setBase(SystemClock.elapsedRealtime()-offset.time);
                    chronometer.start();
                    running = true;
                    pauseButton.setEnabled(true);

                    saveButton.setEnabled(true);


                    animatorSet.playTogether(cardViewAnimator,clockAnimator,clockAnimatorX,clockAnimatorY
                    ,chronoAnimator,chronoAnimatorX,chronoAnimatorY);
                    gt1.gifDrawable.removeAnimationListener(anim1);
                    gt1.gifDrawable.setSpeed(1f);
                    gt1.gifDrawable.start();
                    devButton.setVisibility(View.VISIBLE);
                   if(cardviews.getY()!=-500f)
                    animatorSet.start();




                }
            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    chronometer.stop();
                    offset.time = SystemClock.elapsedRealtime()-chronometer.getBase();
                    running=false;
                    saveButton.setEnabled(true);
                    animatorSet.reverse();
                    gt1.gifDrawable.setSpeed(3f);



                    gt1.gifDrawable.addAnimationListener(anim1);
                    devButton.setVisibility(View.INVISIBLE);






                }
            }
        });


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                offset.time=0;
                running=false;
                saveButton.setEnabled(true);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offset.time!=0) {
                    chronometer.stop();
                    updateView();
                    startButton.setEnabled(true);
                    animatorSet.reverse();
                    gt1.gifDrawable.stop();

                }
                else{
                    chronometer.stop();

                    offset.time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    updateView();
                    startButton.setEnabled(true);
                    animatorSet.reverse();
                    gt1.gifDrawable.stop();
                }



            }
        });

        deleteAlldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mindDb.mindDao().deleteAllData();
            }
        });

        devButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, developerSettings.class);
                startActivity(intent);
            }
        });







    }
    public class elapsedTime{

        long time;

    }

    public void updateView(){

        running = false;
        chronometer.setBase(SystemClock.elapsedRealtime());
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        Log.d("tag",week+"");

        mindObject = new mindfulData(offset.time,day,week,month);


        MainActivity.mindDb.mindDao().addSession(mindObject);
        long dayTotalmillis = MainActivity.mindDb.mindDao().get_Day_Total(day);
        long weekTotalmillis = MainActivity.mindDb.mindDao().get_Week_Total(week);
        long monthTotalmillis = MainActivity.mindDb.mindDao().get_Month_Total(month);





        dayTotalTime.setText(getTimeString(dayTotalmillis));
        weektime.setText(getTimeString(weekTotalmillis));
        monthtime.setText(getTimeString(monthTotalmillis));

        offset.time = 0;
        saveButton.setEnabled(false);








    }

    public String getTimeString(long dayTotalmillis){


        String timeString = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(dayTotalmillis),
                TimeUnit.MILLISECONDS.toMinutes(dayTotalmillis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(dayTotalmillis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(dayTotalmillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dayTotalmillis)));

        return  timeString;

    }


    public void displayCounters(){
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        long dayTotalmillis = MainActivity.mindDb.mindDao().get_Day_Total(day);
        long weekTotalmillis = MainActivity.mindDb.mindDao().get_Week_Total(week);
        long monthTotalmillis = MainActivity.mindDb.mindDao().get_Month_Total(month);



        dayTotalTime.setText(getTimeString(dayTotalmillis));
        weektime.setText(getTimeString(weekTotalmillis));
        monthtime.setText(getTimeString(monthTotalmillis));

    }

    public GifDrawable getGif(){

        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.abc);
            timeContainer.setBackground(gifDrawable);

            return gifDrawable;
        } catch (IOException e) {
            e.printStackTrace();
        }
return null;

    }


    public class getGif1{

        GifDrawable gifDrawable;



    }

    public void checkDate(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);


        if(day==1){

            mindDb.mindDao().deleteAllData();
        }

    }

}
