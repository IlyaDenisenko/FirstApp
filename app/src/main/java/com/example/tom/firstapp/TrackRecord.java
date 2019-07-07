package com.example.tom.firstapp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.HashSet;
import java.util.Set;

import static com.example.tom.firstapp.MainActivity.recList;
import static com.example.tom.firstapp.MainActivity.sPrefPicture;
import static com.example.tom.firstapp.Menu.mscore2;

public class TrackRecord extends AppCompatActivity {

    ScrollView scroll;
    TextView Point10, Point50, Point100, Point300, Champion10, Champion50, Champion100, Champion141;
    int HeightImage = 0, WidthImage = 0;
    Set<String> ret;

    final String SAVED_PICTURE = "saved_picture";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_record);
          LoadS();

        scroll = (ScrollView)findViewById(R.id.myscroll);
        Point10 =  (TextView)findViewById(R.id.point10);
        Point50 =  (TextView)findViewById(R.id.point50);
        Point100 =  (TextView)findViewById(R.id.point100);
        Point300 =  (TextView)findViewById(R.id.point300);
        Champion10 =  (TextView)findViewById(R.id.champion10);
        Champion50 =  (TextView)findViewById(R.id.champion50);
        Champion100 =  (TextView)findViewById(R.id.champion100);
        Champion141 =  (TextView)findViewById(R.id.champion141);

        Point10.setText("Набрать 10 очков   " + mscore2 + "/10" );
        Point50.setText("Набрать 50 очков   " + mscore2 + "/50" );
        Point100.setText("Набрать 100 очков   " + mscore2 + "/100" );
        Point300.setText("Набрать 300 очков   " + mscore2 + "/300" );
        Champion10.setText("Узнать 10 чемпионов   " + recList.size() + "/10");
        Champion50.setText("Узнать 50 чемпионов   " + recList.size() + "/50");
        Champion100.setText("Узнать 100 чемпионов   " + recList.size() + "/100");
        Champion141.setText("Узнать 141 чемпиона   " + recList.size() + "/141");

        HeightImage = Point10.getLineHeight();
        WidthImage = HeightImage;

        Drawable image = getResources().getDrawable(R.drawable.galochka);
        image.setBounds( 0, 0, HeightImage, WidthImage);
        if (mscore2 >= 10){
            Point10.setCompoundDrawables(null, null, image, null );
            Point10.setText("Набрать 10 очков   10/10");}
        if (mscore2 >= 50){
            Point50.setCompoundDrawables(null, null, image, null );
            Point50.setText("Набрать 50 очков   50/50");}
        if (mscore2 >= 100){
            Point100.setCompoundDrawables(null, null, image, null );
            Point100.setText("Набрать 100 очков   100/100");}
        if (mscore2 >= 300){
            Point300.setCompoundDrawables(null, null, image, null );
            Point300.setText("Набрать 300 очков   300/300"); }
        if (recList.size() >= 10){
            Champion10.setCompoundDrawables(null, null, image, null );
            Champion10.setText("Узнать 10 чемпионов   10/10");}
        if (recList.size() >= 50){
            Champion50.setCompoundDrawables(null, null, image, null );
            Champion50.setText("Узнать 50 чемпионов   50/50"); }
        if (recList.size() >= 100){
            Champion100.setCompoundDrawables(null, null, image, null );
            Champion100.setText("Узнать 100 чемпионов   100/100"); }
        if (recList.size() >= 141){
            Champion141.setCompoundDrawables(null, null, image, null );
            Champion141.setText("Узнать 141 чемпиона   141/141");}
    }

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onBackPressed(){
        finish();
    }

     public void LoadS(){
        sPrefPicture = getSharedPreferences(SAVED_PICTURE, MODE_PRIVATE);
        ret = sPrefPicture.getStringSet("Picture", new HashSet<String>());
            recList.addAll(ret);
        }
}
