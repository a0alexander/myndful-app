package com.example.mindfulapp_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class addToDoItem extends AppCompatActivity {

    TextView itemDescription;
    Button addButton, cancelButton;
    taskDatabase db_task;
    tasktable tasktable_object;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_item);


        itemDescription = findViewById(R.id.item_Description);
        addButton =findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);


        db_task = Room.databaseBuilder(this,taskDatabase.class,"dbtask")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        c = Calendar.getInstance();






        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = itemDescription.getText().toString();
                int day = c.get(Calendar.DAY_OF_MONTH);

                tasktable_object = new tasktable(a,false,day,"usertask");

                db_task.taskDao().addTask(tasktable_object);

                //Toast.makeText(addToDoItem.this, "added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(addToDoItem.this, todoView.class);
                startActivity(intent);
                finish();




            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addToDoItem.this, todoView.class);
                startActivity(intent);
                finish();
            }
        });







    }
}
