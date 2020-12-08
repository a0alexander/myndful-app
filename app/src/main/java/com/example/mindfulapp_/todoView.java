package com.example.mindfulapp_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class todoView extends AppCompatActivity implements taskAdapter.OnTaskListener  {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    FloatingActionButton fab,tool,incomplete,complete;
    taskDatabase db;
    List<tasktable> tasktables;
    ObjectAnimator fabAnimator,incompleteAnimator,completeAnimator;
    TextView mytaskTitle;
    tasktable tasktable_object_auto, tasktable_object_auto2;




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_view);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fab = findViewById(R.id.floatingActionButton);
        incomplete = findViewById(R.id.showInComplete);
        complete = findViewById(R.id.showComplete);
        tool = findViewById(R.id.toolbox);
        mytaskTitle = findViewById(R.id.myTasktitle);



        db = Room.databaseBuilder(this,taskDatabase.class,"dbtask")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_MONTH);

        tasktable_object_auto = new tasktable("15 min mindfulness breathing",false, today,
                "autotask");
        tasktable_object_auto2 = new tasktable("15 min bedtime retrospection",false,today,
                "autotask");




//            if ((tasktables.get(0).getTask_type() != "autotask" && tasktables.get(0).getDate_() != today) &&
//                    (tasktables.get(1).getTask_type() == "autotask" && tasktables.get(1).getDate_() != today)) {
//
//                db.taskDao().addTask(tasktable_object_auto);
//                db.taskDao().addTask(tasktable_object_auto2);
//
//            } else if ((tasktables.get(0).getTask_type() != "autotask" && tasktables.get(0).getDate_() != today) ||
//                    (tasktables.get(1).getTask_type() == "autotask" && tasktables.get(1).getDate_() != today)) {
//                db.taskDao().addTask(tasktable_object_auto);
//
//            } else if ((tasktables.get(0).getTask_type() == "autotask" && tasktables.get(0).getDate_() != today) ||
//                    (tasktables.get(0).getTask_type() != "autotask" && tasktables.get(1).getDate_() != today)) {
//                db.taskDao().addTask(tasktable_object_auto2);
//
//
//
//        }

     //   Toast.makeText(this, db.taskDao().getLastDate()+"", Toast.LENGTH_SHORT).show();



        tasktables = db.taskDao().getAllData();

        adapter = new taskAdapter(tasktables,this);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);











           // Toast.makeText(this, String.valueOf(tasktables.size()), Toast.LENGTH_SHORT).show();


                fabAnimator = ObjectAnimator.ofFloat(fab,"translationY",0f,-160f);
            completeAnimator = ObjectAnimator.ofFloat(incomplete,"translationY",0f,-320f);
            incompleteAnimator = ObjectAnimator.ofFloat(complete,"translationY",0f,-480f);


            fab.setVisibility(View.GONE);
        incomplete.setVisibility(View.GONE);
        complete.setVisibility(View.GONE);



        final AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
        animatorSet.setDuration(500);
        animatorSet.playTogether(fabAnimator,completeAnimator,incompleteAnimator);




        tool.setOnClickListener(new View.OnClickListener() {
            boolean toggle = true;
            @Override

            public void onClick(View v) {


                if(toggle){
                    animatorSet.start();
                    fab.setVisibility(View.VISIBLE);
                    incomplete.setVisibility(View.VISIBLE);
                    complete.setVisibility(View.VISIBLE);
                    toggle=false;
                    animatorSet.removeAllListeners();

                }
                else{
                    animatorSet.reverse();
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.setVisibility(View.GONE);
                            incomplete.setVisibility(View.GONE);
                            complete.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }

                    });

                    toggle=true;
                    tasktables = db.taskDao().getAllData();

                    adapter = new taskAdapter(tasktables,todoView.this);
                    recyclerView.hasFixedSize();
                    recyclerView.setAdapter(adapter);
                    mytaskTitle.setText("My Tasks");


                }

            }
        });





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todoView.this,addToDoItem.class);
                startActivity(intent);
                finish();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdapter1(true);
                mytaskTitle.setText("Completed Tasks");

            }
        });

        incomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdapter1(false);
                mytaskTitle.setText("Incomplete Tasks");
            }
        });







    }
    boolean enabled= false;
    @Override
    public void onTaskClick(int position) {

        int id_num = tasktables.get(position).getId();

        boolean enabled = tasktables.get(position).isTask_status();


        if(enabled){
            db.taskDao().updateStatus(false,id_num);
            tasktables.get(position).setTask_status(false);
            adapter.notifyDataSetChanged();

        }
        else{
            db.taskDao().updateStatus(true,id_num);
            tasktables.get(position).setTask_status(true);
            adapter.notifyDataSetChanged();
        }



       // Toast.makeText(this, id_num+"hey", Toast.LENGTH_SHORT).show();
        db.taskDao().setStatus(id_num,"usertask");

    }



    public void setAdapter1(boolean stat){
        tasktables = db.taskDao().getProgress(stat);

        adapter = new taskAdapter(tasktables,todoView.this);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

    }
}
