package com.example.tom.firstapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.tom.firstapp.ActivitySettings.bTrueM;
import static com.example.tom.firstapp.ActivitySettings.bTrueS;

public class Quest extends AppCompatActivity {

    private Questions ExQuestions = new Questions();
    private String nCorrect;
    private int nQuestionsLenght = ExQuestions.nQuestions.length;
    int scene;
    static int mscore;

    Timer timer;
    TimerTask Mytask;
    TimerTask Mytask2;

    SoundPool sound;
    int SoundTrue;
    int SoundFalse;

    Button but1, but2, but3, but4;
    TextView text,score;
    Random r;
    ImageView point;
    ProgressBar PB;

    CountDownT countDownT = new CountDownT(10000, 500);
    SharedPreferences sPref;
    SharedPreferences sPrefSound;


    // Возврат в меню кнопкой "Назад"
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Quest.this, Menu.class);
        startActivity(intent);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mscore = 0;
        if (timer != null) {
            timer.cancel();
        }
        countDownT.cancel();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        r = new Random();

        but1 = (Button) findViewById(R.id.button1);
        but2 = (Button) findViewById(R.id.button2);
        but3 = (Button) findViewById(R.id.button3);
        but4 = (Button) findViewById(R.id.button4);
        text = (TextView) findViewById(R.id.textView2);
        point = (ImageView) findViewById(R.id.imageView2);
        score = (TextView) findViewById(R.id.Score);
        PB = (ProgressBar) findViewById(R.id.progressBar);
        scene = r.nextInt(5);

        UpdateQuest(r.nextInt(nQuestionsLenght));

       if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        }  else {
            // Для новых устройств
            createNewSoundPool();
        }

        sPref = getSharedPreferences("saved_pos" ,MODE_PRIVATE);
        bTrueM = sPref.getBoolean("saved_pos",  bTrueM);
        sPrefSound = getSharedPreferences("savedS" ,MODE_PRIVATE);
        bTrueS = sPrefSound.getBoolean("savedS", bTrueS);

        quize();

        countDownT.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes aa = new AudioAttributes.Builder().
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
        sound = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(aa).build();
        SoundTrue = sound.load(this, R.raw.ring, 1);
        SoundFalse = sound.load(this, R.raw.crack, 1);
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        sound = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        SoundTrue = sound.load(this, R.raw.ring, 1);
        SoundFalse = sound.load(this, R.raw.crack, 1);
    }

        public void quize(){

// Нажатия на варианты ответов
        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countDownT.cancel();

                timer = new Timer();
                Mytask = new MyTask();
                Button();
                if (but1.getText() == nCorrect){
                    if (bTrueS)
                    sound.play(SoundTrue, 1,1,1,0,1);
                    // запускаю анимацию
                    Animation anim ;
                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Questions.Numbers.clear(); // очищаю лист в ответами(чтобы ответы не смешивались)
                    timer.schedule(Mytask, 2000); //переход к следующему вопросу через 2 сек
                    // накопление очков
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueS)
                    sound.play(SoundFalse, 1,1,1,0,1);
                    // переход в меню при неправильном ответе через 2 сек
                    timer = new Timer();
                    Mytask2 = new MyTask2();
                    timer.schedule(Mytask2, 2000);

                  Toast toast = Toast.makeText(Quest.this, "Игра окончена. Количество правилых ответов: "  + mscore , Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
            }
        });

        but2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countDownT.cancel();

                timer = new Timer();
                Mytask = new MyTask();
                Button();
                if (but2.getText() == nCorrect){
                    if (bTrueS)
                    sound.play(SoundTrue, 1,1,1,0,1);
                    Animation anim;
                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Questions.Numbers.clear();
                    timer.schedule(Mytask, 2000);
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueS)
                    sound.play(SoundFalse, 1,1,1,0,1);
                    timer = new Timer();
                    Mytask2 = new MyTask2();
                    timer.schedule(Mytask2, 2000);

                    Toast toast = Toast.makeText(Quest.this, "Игра окончена. Количество правилых ответов: "  + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
            }
        });

        but3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countDownT.cancel();

                timer = new Timer();
                Mytask = new MyTask();
                Button();
                if (but3.getText() == nCorrect){
                    if (bTrueS)
                    sound.play(SoundTrue, 1,1,1,0,1);
                    Animation anim;
                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Questions.Numbers.clear();
                    timer.schedule(Mytask, 2000);
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueS)
                    sound.play(SoundFalse, 1,1,1,0,1);
                    timer = new Timer();
                    Mytask2 = new MyTask2();
                    timer.schedule(Mytask2, 2000);

                    Toast toast = Toast.makeText(Quest.this, "Игра окончена. Количество правилых ответов: "  + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
            }
        });
        but4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countDownT.cancel();

                timer = new Timer();
                Mytask = new MyTask();
                Button();
                if (but4.getText() == nCorrect){
                    if (bTrueS)
                    sound.play(SoundTrue, 1,1,1,0,1);
                    Animation anim ;
                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Questions.Numbers.clear();
                    timer.schedule(Mytask, 2000);
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueS)
                    sound.play(SoundFalse, 1,1,1,0,1);
                    timer = new Timer();
                    Mytask2 = new MyTask2();
                    timer.schedule(Mytask2, 2000);

                    Toast toast = Toast.makeText(Quest.this, "Игра окончена. Количество правилых ответов: "  + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
            }
        });
    }


// Метод на все кнопки
    public void Button(){
        if (but1.getText() == nCorrect) {
            but1.setEnabled(false);
            }
        else {
            but1.setEnabled(false);
            but1.setVisibility(View.INVISIBLE);
            }
        if (but2.getText() == nCorrect) {
            but2.setEnabled(false);
        }
        else {
            but2.setEnabled(false);
            but2.setVisibility(View.INVISIBLE);
        }
        if (but3.getText() == nCorrect) {
            but3.setEnabled(false);
        }
        else {
            but3.setEnabled(false);
            but3.setVisibility(View.INVISIBLE);
        }
        if (but4.getText() == nCorrect) {
            but4.setEnabled(false);
        }
        else {
            but4.setEnabled(false);
            but4.setVisibility(View.INVISIBLE);
        }
    }


    private void UpdateQuest (int num){
        // присвоение текста кнопкам "ответ"
        text.setText(ExQuestions.getQuestions(num));
        but1.setText(ExQuestions.getChouses1(num));
        but2.setText(ExQuestions.getChouses2());
        but3.setText(ExQuestions.getChouses3());
        but4.setText(ExQuestions.getChouses4());
        // правильный ответ
        nCorrect = ExQuestions.getCorrect(num);
    }
// Количество правильных ответов
    public void UpdateScore(int point){
        score.setText("" + mscore);
    }

// Таймер при правильном ответе
private class MyTask extends TimerTask{
    @Override
    public void run(){
        switch (scene){
            case 2:
            case 1:
            case 0: Intent inte = new Intent(Quest.this, Quest.class);
                inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inte);
                finish();
                break;
            case 3:
            case 4:
            case 5: Intent intent = new Intent(Quest.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}
// Таймер при не правильном ответе
private class MyTask2 extends TimerTask{
    @Override
    public void run(){
        Intent intent = new Intent(Quest.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
    private class CountDownT extends CountDownTimer{
        private CountDownT(long Mill, long TimerGaps){
            super(Mill, TimerGaps);
        }
        @Override
        public void onTick(long l){
            PB.setProgress((int) (l/1000));
        }
        @Override
        public void onFinish(){
            Toast toast = Toast.makeText(Quest.this, "Игра окончена. Правильных ответов: " + mscore, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
            timer = new Timer();
            Mytask2 = new MyTask2();
            timer.schedule(Mytask2, 2000);
            Button();
        }
    }
}

