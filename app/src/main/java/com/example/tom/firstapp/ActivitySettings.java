package com.example.tom.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.tom.firstapp.utils.MyService;

public class ActivitySettings extends AppCompatActivity {

   private CheckBox CMusic, CSound;
   final String SAVED_POS = "saved_pos";
   SharedPreferences sPrefMusic;
   SharedPreferences sPrefSound;

   static public boolean bTrueMusic;
   static public boolean bTrueSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CMusic = (CheckBox) findViewById(R.id.checkMusic);
        CSound = (CheckBox) findViewById(R.id.checkSound);

        CMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    startService(new Intent(ActivitySettings.this, MyService.class));
                else
                    stopService(new Intent(ActivitySettings.this, MyService.class));
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();

        sPrefMusic = getSharedPreferences(SAVED_POS ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefMusic.edit();
        bTrueMusic = CMusic.isChecked();
        editor.putBoolean(SAVED_POS, bTrueMusic);
        editor.apply();

        sPrefSound = getSharedPreferences("savedS" ,MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sPrefSound.edit();
        bTrueSound = CSound.isChecked();
        editor1.putBoolean("savedS", bTrueSound);
        editor1.apply();

        MyService.pause();
    }

    @Override
    public void onResume(){
        super.onResume();

        sPrefMusic = getSharedPreferences(SAVED_POS ,MODE_PRIVATE);
        bTrueMusic = sPrefMusic.getBoolean(SAVED_POS, bTrueMusic);
        CMusic.setChecked(bTrueMusic);

        sPrefSound = getSharedPreferences("savedS", MODE_PRIVATE);
        bTrueSound = sPrefSound.getBoolean("savedS", bTrueSound);
        CSound.setChecked(bTrueSound);

        if (bTrueMusic) MyService.start();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ActivitySettings.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
