package com.example.tom.firstapp;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.*;

import com.example.tom.firstapp.utils.MyService;
import com.example.tom.firstapp.utils.Sound;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

import static com.example.tom.firstapp.ActivitySettings.bTrueMusic;
import static com.example.tom.firstapp.ActivitySettings.bTrueSound;
import static com.example.tom.firstapp.Quest.mscore;

public class Menu extends AppCompatActivity {

    private InterstitialAd mInterstitial;

    int scene;
    static int soundTrue, soundFalse;
    static public int mscore2;
    int marketingInt = 0;
    Random r;
    Button startBut, settingsBut, recordBut;
    TextView record;

    SoundPool mSoundpool;
    int soundCheck;
    int mStreamID;

    static SharedPreferences sPref;
    SharedPreferences sPrefSound, marketPref;
    final String SAVED_SCORE = "saved_score";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);
        LoadS();
        Butto();
        sPref = getPreferences(MODE_PRIVATE);


        // проверка CheckBox на музыке
        sPref = getSharedPreferences("saved_pos" ,MODE_PRIVATE);
        bTrueMusic = sPref.getBoolean("saved_pos", bTrueMusic);
        sPrefSound = getSharedPreferences("savedS" ,MODE_PRIVATE);
        bTrueSound = sPrefSound.getBoolean("savedS", bTrueSound);

        initSound();
        marketing();
    }

    public void initSound(){
        Sound.enabledSoundPool();
        soundTrue = Sound.loadSound(this, R.raw.ring);
        soundFalse = Sound.loadSound(this, R.raw.crack);
        soundCheck = Sound.loadSound(this, R.raw.snap);
    }


    private void marketing(){
          // Реклама
        MobileAds.initialize(this, "ca-app-pub-1637031839701018~6118990605");
        mInterstitial = new InterstitialAd(this); // выделили место под рекламу
        mInterstitial.setAdUnitId("ca-app-pub-1637031839701018/3026510133"); // созданный рекламный блок
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitial.loadAd(adRequest); // Загружаем рекламное объявление
        // этот блок отвечает за появление рекламы на экране
        marketingInt++;

            mInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (mInterstitial.isLoaded()) {
                        if (marketingInt % 9 == 0) {
                            mInterstitial.show();
                            // Блок кнопок во время рекламы
                            startBut.setEnabled(false);
                            settingsBut.setEnabled(false);
                        }
                    }
                }
            });
    }

    @Override
    public void onBackPressed(){
        openQuitDialog();
    }

    @Override
    public void onResume(){
        super.onResume();
        // Музыка
        if (bTrueMusic)
            startService(new Intent(Menu.this, MyService.class));
        if (!bTrueMusic)
            stopService(new Intent(Menu.this, MyService.class));
        // Звук
        if (!bTrueSound)
            mSoundpool.stop(mStreamID);
    }

    @Override
    public void onPause(){
        super.onPause();
        MyService.pause();
    }

// Окно с выходом
    private void openQuitDialog(){
        AlertDialog.Builder quit = new AlertDialog.Builder(this);
        quit.setTitle("Вы точно хотите уйти в АФК?");
        quit.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               stopService(new Intent(Menu.this, MyService.class));
               SaveS();
               finish();
            }
        });
           quit.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
               }
           })   ;
        AlertDialog alert = quit.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.BLACK);
    }

    public void Butto(){
        r = new Random();
        scene = r.nextInt(5);
        startBut = (Button)findViewById(R.id.button);
        settingsBut = (Button)findViewById(R.id.settings);
        recordBut = (Button)findViewById(R.id.recBut);
        record = (TextView)findViewById(R.id.record);


        // Рекорд
        if (mscore2==mscore)
            record.setText("Рекорд: " + mscore2);
        if(mscore>mscore2){
            mscore2=mscore;
            record.setText("Рекорд: " + mscore2);}
        if (mscore<mscore2)
            record.setText("Рекорд: " + mscore2);


        // запуск новой игры
        startBut.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (bTrueSound)
                     Sound.playSound(soundCheck);
                        switch (scene){
                            case 2:
                            case 1:
                            case 0:
                                Intent inte = new Intent(Menu.this, Quest.class);

                               startActivity(inte);
                                mscore = 0;
                                finish();
                                SaveS();
                                break;
                            case 3:
                            case 4:
                            case 5: Intent intent = new Intent(Menu.this, MainActivity.class);
                                startActivity(intent);
                                mscore = 0;
                                finish();
                                SaveS();
                                break;
                        }
                    }
                }
        );

        settingsBut.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (bTrueSound)
                           // mStreamID = mSoundpool.play(soundCheck, 1, 1, 1, 0, 1);
                        Sound.playSound(soundCheck);
                        Intent in = new Intent(Menu.this, ActivitySettings.class);
                        startActivity(in);
                        finish();
                        SaveS();
                    }
                });

        recordBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bTrueSound)
                   // mStreamID = mSoundpool.play(soundCheck, 1, 1, 1, 0, 1);
                Sound.playSound(soundCheck);
                Intent recordIntent = new Intent(Menu.this, TrackRecord.class);
                startActivity(recordIntent);
                SaveS();
            }
        });
        }

        public void SaveS(){
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putInt(SAVED_SCORE, mscore2);
            ed.apply();

            marketPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor edito = marketPref.edit();
            edito.putInt("MARKETING_INT", marketingInt);
            edito.apply();
        }

        protected void LoadS(){
            sPref = getPreferences(MODE_PRIVATE);
            mscore2 = sPref.getInt(SAVED_SCORE, mscore2);

            marketPref = getPreferences(MODE_PRIVATE);
            marketingInt = marketPref.getInt("MARKETING_INT", marketingInt);
        }
        }