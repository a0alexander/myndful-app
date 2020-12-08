package com.example.mindfulapp_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class developerSettings extends AppCompatActivity {


    Switch devSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_settings);



        devSwitch = findViewById(R.id.devModeswitch);



        final SharedPreferences.Editor editor = getSharedPreferences("dev_prefs", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("dev_prefs", MODE_PRIVATE);
        String alarm_enabled = prefs.getString("devmode", "disabled");//"No name defined" is the default value.


        if(alarm_enabled.equals("enabled")){

            devSwitch.setChecked(true);

        }
        else {
            devSwitch.setChecked(false);
        }







        devSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
//
                    editor.putString("devmode","enabled");
                    editor.apply();


                }

                else{
//                    findViewById(R.id.deleteAllDataBtn).setVisibility(View.GONE);
//                    findViewById(R.id.resetBtn).setVisibility(View.GONE);
//                    findViewById(R.id.todoButton).setVisibility(View.GONE);
                    editor.putString("devmode","disabled");
                    editor.apply();


                }

            }
        });


    }
}
