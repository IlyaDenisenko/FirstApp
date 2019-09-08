package com.example.tom.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tom.firstapp.utils.MyService;
import com.example.tom.firstapp.utils.Sound;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.tom.firstapp.ActivitySettings.bTrueMusic;
import static com.example.tom.firstapp.ActivitySettings.bTrueSound;

public class Quest extends AppCompatActivity {

    private Questions ExQuestions = new Questions();
    private String nCorrect;
    private int nQuestionsLenght = ExQuestions.nQuestions.length;
    int scene;
    static int mscore;

    Timer timer;
    TimerTask correctAnswerTask;
    TimerTask incorrecttAnswerTask;


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
    public void onResume(){
        super.onResume();
        if (bTrueMusic) MyService.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        MyService.pause();
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


        sPref = getSharedPreferences("saved_pos" ,MODE_PRIVATE);
        bTrueMusic = sPref.getBoolean("saved_pos", bTrueMusic);
        sPrefSound = getSharedPreferences("savedS" ,MODE_PRIVATE);
        bTrueSound = sPrefSound.getBoolean("savedS", bTrueSound);

        quize();
        countDownT.start();
    }


        public void quize(){

// Нажатия на варианты ответов
        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countDownT.cancel();

                timer = new Timer();
                correctAnswerTask = new CorrectAnswerTask();
                buttonEnabled();
                if (but1.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
                    // запускаю анимацию
//                    Animation anim ;
//                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
//                    point.startAnimation(anim);
                    but1.setBackgroundResource(R.color.green);
                    Questions.arrayChouses.clear(); // очищаю лист в ответами(чтобы ответы не смешивались)
                    timer.schedule(correctAnswerTask, 2000); //переход к следующему вопросу через 2 сек
                    // накопление очков
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    // переход в меню при неправильном ответе через 2 сек
                    but1.setBackgroundResource(R.color.colorAccent);
                    timer = new Timer();
                    incorrecttAnswerTask = new IncorrectTask();
                    timer.schedule(incorrecttAnswerTask, 2000);

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
                correctAnswerTask = new CorrectAnswerTask();
                buttonEnabled();
                if (but2.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
//                    Animation anim;
//                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
//                    point.startAnimation(anim);
                    but2.setBackgroundResource(R.color.green);
                    Questions.arrayChouses.clear();
                    timer.schedule(correctAnswerTask, 2000);
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    but2.setBackgroundResource(R.color.colorAccent);
                    timer = new Timer();
                    incorrecttAnswerTask = new IncorrectTask();
                    timer.schedule(incorrecttAnswerTask, 2000);

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
                correctAnswerTask = new CorrectAnswerTask();
                buttonEnabled();
                if (but3.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
//                    Animation anim;
//                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
//                    point.startAnimation(anim);
                    but3.setBackgroundResource(R.color.green);
                    Questions.arrayChouses.clear();
                    timer.schedule(correctAnswerTask, 2000);
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    but3.setBackgroundResource(R.color.colorAccent);
                    timer = new Timer();
                    incorrecttAnswerTask = new IncorrectTask();
                    timer.schedule(incorrecttAnswerTask, 2000);
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
                correctAnswerTask = new CorrectAnswerTask();
                buttonEnabled();
                if (but4.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
//                    Animation anim ;
//                    anim = AnimationUtils.loadAnimation(Quest.this, R.anim.mytrans);
//                    point.startAnimation(anim);
                    but4.setBackgroundResource(R.color.green);
                    Questions.arrayChouses.clear();
                    timer.schedule(correctAnswerTask, 2000);
                    mscore = mscore + 1;
                    UpdateScore(mscore);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    but4.setBackgroundResource(R.color.colorAccent);
                    timer = new Timer();
                    incorrecttAnswerTask = new IncorrectTask();
                    timer.schedule(incorrecttAnswerTask, 2000);

                    Toast toast = Toast.makeText(Quest.this, "Игра окончена. Количество правилых ответов: "  + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
            }
        });
    }
    
    public void onClickButton(){
        countDownT.cancel();

        timer = new Timer();
        correctAnswerTask = new CorrectAnswerTask();
        buttonEnabled();
    }

// Метод на все кнопки
    public void buttonEnabled(){
        if (but1.getText() == nCorrect) {
            but1.setEnabled(false);
            }
        else {
            but1.setEnabled(false);

            }
        if (but2.getText() == nCorrect) {
            but2.setEnabled(false);
        }
        else {
            but2.setEnabled(false);

        }
        if (but3.getText() == nCorrect) {
            but3.setEnabled(false);
        }
        else {
            but3.setEnabled(false);

        }
        if (but4.getText() == nCorrect) {
            but4.setEnabled(false);
        }
        else {
            but4.setEnabled(false);

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
private class CorrectAnswerTask extends TimerTask{
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
// Таймер при неправильном ответе
private class IncorrectTask extends TimerTask{
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
            incorrecttAnswerTask = new IncorrectTask();
            timer.schedule(incorrecttAnswerTask, 2000);
            buttonEnabled();
        }
    }
}

