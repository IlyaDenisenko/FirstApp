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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.*;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

import static com.example.tom.firstapp.ActivitySettings.bTrueM;
import static com.example.tom.firstapp.ActivitySettings.bTrueS;
import static com.example.tom.firstapp.Quest.mscore;

public class Menu extends AppCompatActivity {

    private InterstitialAd mInterstitial;

    int scene;
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

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
       }  else {
            // Для новых устройств
            createNewSoundPool();
        }

        // проверка CheckBox на музыке
        sPref = getSharedPreferences("saved_pos" ,MODE_PRIVATE);
        bTrueM = sPref.getBoolean("saved_pos",  bTrueM);
        sPrefSound = getSharedPreferences("savedS" ,MODE_PRIVATE);
        bTrueS = sPrefSound.getBoolean("savedS", bTrueS);

        marketing();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes aa = new AudioAttributes.Builder().
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
        mSoundpool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(aa).build();
        soundCheck = mSoundpool.load(this, R.raw.snap, 1);
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundpool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundCheck = mSoundpool.load(this, R.raw.snap, 1);
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
        startService(new Intent(Menu.this, MyService.class));
        if (!bTrueM)
            stopService(new Intent(Menu.this, MyService.class));
        // Звук
        if (!bTrueS)
            mSoundpool.stop(mStreamID);

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
        settingsBut = (Button)findViewById(R.id.exit);
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
                        if (bTrueS)
                      mStreamID = mSoundpool.play(soundCheck, 1, 1, 1, 0, 1);
                        switch (scene){
                            case 2:
                            case 1:
                            case 0: Intent inte = new Intent(Menu.this, Quest.class);
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
                        if (bTrueS)
                            mStreamID = mSoundpool.play(soundCheck, 1, 1, 1, 0, 1);
                        Intent in = new Intent(Menu.this, ActivitySettings.class);
                        startActivity(in);
                        finish();
                        SaveS();
                    }
                });

        recordBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bTrueS)
                    mStreamID = mSoundpool.play(soundCheck, 1, 1, 1, 0, 1);
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