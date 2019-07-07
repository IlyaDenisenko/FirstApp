package com.example.tom.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class ActivitySettings extends AppCompatActivity {

   private CheckBox CMusic;
   private CheckBox CSound;
   final String SAVED_POS = "saved_pos";
   SharedPreferences sPref;
   SharedPreferences sPrefSound;

   static public boolean bTrueM;
   static public boolean bTrueS;

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

        sPref = getSharedPreferences(SAVED_POS ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        bTrueM = CMusic.isChecked();
        editor.putBoolean(SAVED_POS, bTrueM);
        editor.apply();

        sPrefSound =getSharedPreferences("savedS" ,MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sPrefSound.edit();
        bTrueS = CSound.isChecked();
        editor1.putBoolean("savedS", bTrueS);
        editor1.apply();
    }

    @Override
    public void onResume(){
        super.onResume();

        sPref = getSharedPreferences(SAVED_POS ,MODE_PRIVATE);
        bTrueM = sPref.getBoolean(SAVED_POS,  bTrueM);
        CMusic.setChecked(bTrueM);

        sPrefSound = getSharedPreferences("savedS", MODE_PRIVATE);
        bTrueS = sPrefSound.getBoolean("savedS", bTrueS);
        CSound.setChecked(bTrueS);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ActivitySettings.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
