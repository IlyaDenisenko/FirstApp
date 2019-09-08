package com.example.tom.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tom.firstapp.utils.MyService;
import com.example.tom.firstapp.utils.Sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.tom.firstapp.ActivitySettings.bTrueMusic;
import static com.example.tom.firstapp.ActivitySettings.bTrueSound;
import static com.example.tom.firstapp.Quest.mscore;

public class MainActivity extends AppCompatActivity {
    // Элементы экрана
    TextView  score;
    ImageView point, imageQuestView;
    Button but1, but2, but3, but4;
    ProgressBar PB;

    // Переменные
    int scene, questions;
    public String nCorrect;
    Random r = new Random();

    // Достижения
    static HashSet<String> recList = new HashSet<>();
    Set<String> ret = new HashSet<>();


    Timer timer;
    TimerTask Mytask; // переход при правильном ответе
    TimerTask Mytask2; // переход при не правильном ответе
    CountDownT countDownT = new CountDownT(17000, 500);

    SharedPreferences sPref, sPrefSound;
    static SharedPreferences sPrefPicture;
    public static final String SAVED_PICTURE = "saved_picture";


    public static ArrayList<String> List = new ArrayList();

    String [][] otvets = {
            {"Поппи", "Лулу", "Фиора", "Тимо"},
            {"Карма", "Жанна", "Катарина", "Кейл"},
            {"Кейл", "Фиора", "Джейс", "Джакс"},
            {"Жанна", "Эш", "Кейтлин", "Карма"},
            {"Джейс", "Дариус", "Дрейвен", "Зак"},
            {"Ка'Зикс", "Чо'Гат", "Рек'Сай", "Вел'Коз"},
            {"Ког'Мао", "Зерат", "Маокай", "Таам Кенч"},
            {"Зайра", "Элиза", "Орианна", "Квинн"},
            {"Ренгар", "Варвик", "Мальфит", "Ноктюрн"},
            {"Эзреаль", "Экко", "Ясуо", "Люциан"},
            {"Диана", "Моргана", "Кассиопея", "Джинкс"},
            {"Гангпланк", "Джейс", "Грейвз", "Ренектон"},
            {"Джин", "Дрейвен", "Грагас", "Люциан"},
            {"Мальфит", "Галио", "Кассадин", "Йорик"},
            {"Нидали", "Вай", "Орианна", "Синдра"},
            {"Квинн", "Рек'Сай", "Моргана", "Леона"},
            {"Треш", "Люциан", "Удир", "Твич"},
            {"Зерат", "Вел'Коз", "Вейгар", "Зиггс"},
            {"Вел'Коз", "Иверн", "Кеннен", "Корки"},
            {"Фиора", "Кейл", "Ирелия", "Камилла"},
            {"Грейвз", "Джарван IV", "Дрейвен", "Гарен"},
            {"Джакс", "Гекарим", "Иверн", "Картус"},
            {"Люкс", "Моргана", "Лиссандра", "Ле Блан"},
            {"Нами", "Камилла", "Калиста", "Нидали"},
            {"Шако", "Мальзахар", "Мордекайзер", "Наутилус"},
            {"Удир", "Олаф", "Раммус", "Тарик"},
            {"Доктор Мундо", "Джакс", "Сион", "Профессор Мундо"},
            {"Дариус", "Дрейвен", "Грейвз", "Джарван IV"},
            {"Дрейвен", "Люциан", "Древен", "Грейвз"},
            {"Кассиопея", "Ле Блан", "Талия", "Люкс"},
            {"Ле Блан", "Леона", "Мисс Фортуна", "Энни"},
            {"Моргана", "Кассиопея", "Калиста", "Иллаой"},
            {"Ривен", "Ирелия", "Зайра", "Диана"},
            {"Сион", "Доктор Мундо", "Клед", "Гнар"},
            {"Владимир", "Виктор", "Сергей", "Влад"},
            {"Чо'Гат", "Рек'Сай", "Ка'Зикс", "Вел'Коз"},
            {"Люциан", "Гарен", "Джин", "Владимир"},
            {"Лулу", "Тристана", "Тимо", "Нуну"},
            {"Сивир", "Кейтлин", "Эш", "Тристана"},
            {"Вай", "Вейн", "Ирелия", "Фиора"},
            {"Зак", "Шен", "Зед", "Браум"},
            {"Зиггс", "Тимо", "Вейгар", "Зерат"},
            {"Зилеан", "Райз", "Скарнер", "Экко"},
            {"Аурелион Сол", "Бард", "Вел'Коз", "Гекарим"},
            {"Браум", "Дариус", "Бард", "Грагас"},
            {"Грагас", "Браум", "Йорик", "Олаф"},
            {"Картус", "Хеймердингер", "Фиддлстикс", "Мальзахар"},
            {"Корки", "Рамбл", "Гнар", "Кеннен"},
            {"Наутилус", "Блицкранк", "Треш", "Таам Кенч"},
            {"Ноктюрн", "Рек'Сай", "Ка'Зикс", "Ренгар"},
            {"Свейн", "Сион", "Синджед", "Ургот"},
            {"Тарик", "Эзреаль", "Браум", "Триндамир"},
            {"Экко", "Зилеан", "Хеймердингер", "Корки"},
            {"Джинкс", "Кейтлин", "Вай", "Эш"},
            {"Ли Син", "Мастер Йи", "Талон", "Шен"},
            {"Седжуани", "Лиссандра", "Эш", "Синдра"},
            {"Сорака", "Карма", "Жанна", "Нами"},
            {"Триндамир", "Дариус", "Дрейвен", "Олаф"},
            {"Вейгар", "Зиггс", "Тимо", "Физз"},
            {"Вейн", "Эш", "Кейтлин", "Сивир"},
            {"Гекарим", "Волибир", "Клед", "Ренгар"},
            {"Бренд", "Райз", "Бард", "Зерат"},
            {"Калиста", "Камилла", "Катарина", "Кассиопея"},
            {"Лиссандра", "Кассиопея", "Синдра", "Нидали"},
            {"Синджед", "Сион", "Свейн", "Фиддлстикс"},
            {"Таам Кенч", "Браум", "Дариус", "Сион"},
            {"Твистед Фэйт", "Рэйкан", "Твич", "Тарик"},
            {"Кассадин", "Картус", "Джакс", "Кеннен"},
            {"Рэйкан", "Браум", "Свейн", "Экко"},
            {"Шая", "Эш", "Сона", "Кай'Са"},
            {"Синдра", "Зои", "Лиссандра", "Сивир"},
            {"Ясуо", "Ривен", "Хасаги", "Талон"},
            {"Фиддлстикс", "Хеймердингер", "Мордекайзер", "Картус"},
            {"Мисс Фортуна", "Кейтлин", "Вай", "Джинкс"},
            {"Камилла", "Фиора", "Кейл", "Квинн"},
            {"Леона", "Фиора", "Ле Блан", "Люкс"},
            {"Шен", "Шако", "Мастер Йи", "Сион"},
            {"Талия", "Синдра", "Энни", "Карма"},
            {"Варус", "Люциан", "Дрейвен", "Каин"},
            {"Азир", "Джарван IV", "Галио", "Гарен"},
            {"Акали", "Катарина", "Ирелия", "Вейн"},
            {"Анивия", "Эвелинн", "Нидали", "Талия"},
            {"Бард", "Браум", "Алистар", "Иверн"},
            {"Физз", "Фиддлстикс", "Тимо", "Вейгар"},
            {"Клед", "Гнар", "Амуму", "Нуну"},
            {"Твич", "Раммус", "Клед", "Зак"},
            {"Рек'Сай", "Ка'Зикс", "Чо'Гат", "Вел'Коз"},
            {"Элиза", "Эвелинн", "Энни", "Экко"},
            {"Кейтлин", "Джинкс", "Вай", "Мисс Фортуна"},
            {"Мальзахар", "Кассадин", "Вел'Коз", "Зерат"},
            {"Пантеон", "Гарен", "Дариус", "Ургот"},
            {"Ренектон", "Насус", "Варвик", "Ренгар"},
            {"Скарнер", "Гекарим", "Вуконг", "Зак"},
            {"Сона", "Сорака", "Карма", "Жанна"},
            {"Трандл", "Вуконг", "Треш", "Насус"},
            {"Кеннен", "Рамбл", "Гнар", "Раммус"},
            {"Мастер Йи", "Шен", "Вуконг", "Зед"},
            {"Нуну", "Лулу", "Наутилус", "Удир"},
            {"Рамбл", "Гнар", "Тимо", "Райз"},
            {"Раммус", "Удир", "Рамбл", "Маокай"},
            {"Зед", "Ясуо", "Ли Син", "Ксин Жао"},
            {"Энни", "Синдра", "Экко", "Талия"},
            {"Галио", "Гарен", "Гекарим", "Маокай"},
            {"Ирелия", "Фиора", "Иллаой", "Камилла"},
            {"Катарина", "Акали", "Кай'Са", "Калиста"},
            {"Маокай", "Иверн", "Галио", "Мальфит"},
            {"Райз", "Азир", "Рамбл", "Физз"},
            {"Эвелинн", "Элиза", "Шивана", "Ирелия"},
            {"Ургот", "Удир", "Таам Кенч", "Орн"},
            {"Ари", "Камилла", "Ле Блан", "Нами"},
            {"Атрокс", "Рааст", "Каин", "Варус"},
            {"Блицкранк", "Нуну", "Виктор", "Галио"},
            {"Виктор", "Владимир", "Джейс", "Зиггс"},
            {"Ксин Жао", "Джакс", "Мастер Йи", "Каин"},
            {"Кай'Са", "Вейн", "Кейтлин", "Джинкс"},
            {"Киндред", "Кейтлин", "Калиста", "Ирелия"},
            {"Варвик", "Ренгар", "Алистар", "Волибир"},
            {"Гарен", "Дариус", "Джарван IV", "Грагас"},
            {"Хеймердингер", "Зиггс", "Тимо", "Виктор"},
            {"Йорик", "Ургот", "Трандл", "Сион"},
            {"Каин", "Атрокс", "Рааст", "Ясуо"},
            {"Насус", "Ренектон", "Варвик", "Азир"},
            {"Олаф", "Трандл", "Грагас", "Грейвз"},
            {"Шивана", "Элиза", "Эвелинн", "Шая"},
            {"Эш", "Энни", "Вейн", "Кейтлин"},
            {"Иллаой", "Седжуани", "Ривен", "Фиора"},
            {"Мордекайзер", "Грагас", "Картус", "Маокай"},
            {"Орианна", "Синдра", "Ари", "Диана"},
            {"Орн", "Маокай", "Волибир", "Мордекайзер"},
            {"Тимо", "Гнар", "Рамбл", "Физз"},
            {"Волибир", "Орн", "Ренгар", "Варвик"},
            {"Вуконг", "Волибир", "Варвик", "Гнар"},
            {"Амуму", "Нуну", "Лулу", "Энни"},
            {"Гнар", "Тимо", "Рамбл", "Грагас"},
            {"Джарван IV", "Гарен", "Дариус", "Ксин Жао"},
            {"Зои", "Энни", "Экко", "Талия"},
            {"Алистар", "Вуконг", "Ренгар", "Ренектон"},
            {"Иверн", "Маокай", "Зак", "Гекарим"},
            {"Талон", "Зед", "Шен", "Владимир"},
            {"Тристана", "Эш", "Лулу", "Тимо"},
            {"Пайк", "Гангпланк", "Ли Син", "Наутилус"}

    };
    String [] trueOtvets = {"Поппи", "Карма", "Кейл", "Жанна", "Джейс", "Ка'Зикс",
            "Ког'Мао", "Зайра", "Ренгар", "Эзреаль", "Диана",
            "Гангпланк", "Джин", "Мальфит", "Нидали", "Квинн",
            "Треш", "Зерат", "Вел'Коз", "Фиора", "Грейвз",
            "Джакс", "Люкс", "Нами", "Шако", "Удир",
            "Доктор Мундо", "Дариус", "Дрейвен", "Кассиопея",
            "Ле Блан", "Моргана", "Ривен", "Сион", "Владимир",
            "Чо'Гат", "Люциан", "Лулу", "Сивир", "Вай",
            "Зак", "Зиггс", "Зилеан", "Аурелион Сол", "Браум",
            "Грагас", "Картус", "Корки", "Наутилус", "Ноктюрн",
            "Свейн", "Тарик", "Экко", "Джинкс", "Ли Син",
            "Седжуани", "Сорака", "Триндамир", "Вейгар", "Вейн",
            "Гекарим","Бренд", "Калиста", "Лиссандра", "Синджед",
            "Таам Кенч", "Твистед Фэйт", "Кассадин", "Рэйкан", "Шая",
            "Синдра", "Ясуо", "Фиддлстикс", "Мисс Фортуна", "Камилла",
            "Леона", "Шен", "Талия", "Варус", "Азир",
            "Акали", "Анивия", "Бард", "Физз", "Клед",
            "Твич", "Рек'Сай", "Элиза", "Кейтлин", "Мальзахар",
            "Пантеон", "Ренектон", "Скарнер", "Сона", "Трандл",
            "Кеннен", "Мастер Йи", "Нуну", "Рамбл", "Раммус", "Зед",
            "Энни", "Галио", "Ирелия", "Катарина", "Маокай",
            "Райз", "Эвелинн", "Ургот", "Ари", "Атрокс",
            "Блицкранк", "Виктор", "Ксин Жао", "Кай'Са", "Киндред",
            "Варвик", "Гарен", "Хеймердингер", "Йорик", "Каин",
            "Насус", "Олаф", "Шивана", "Эш", "Иллаой",
            "Мордекайзер", "Орианна", "Орн", "Тимо", "Волибир",
            "Вуконг", "Амуму", "Гнар", "Джарван IV", "Зои",
            "Алистар", "Иверн", "Талон", "Тристана", "Пайк"
    };

    int[] imagesList = {
            R.drawable.poppin, R.drawable.karma, R.drawable.kayle, R.drawable.janna, R.drawable.jayce, R.drawable.kaziks,
            R.drawable.kogmaw, R.drawable.zayra, R.drawable.rengar, R.drawable.ezreal, R.drawable.diana,
            R.drawable.gankplank, R.drawable.jin, R.drawable.malphit, R.drawable.nidalee, R.drawable.quinn,
            R.drawable.tresh, R.drawable.xerat, R.drawable.velkoz, R.drawable.fiora, R.drawable.gravz,
            R.drawable.jax, R.drawable.lux, R.drawable.nami, R.drawable.shako, R.drawable.udyr,
            R.drawable.dr_mundo, R.drawable.darius, R.drawable.draven, R.drawable.kassiopeia,
            R.drawable.leblan, R.drawable.morgana, R.drawable.riven, R.drawable.sion, R.drawable.vladimir,
            R.drawable.chogat, R.drawable.lucian, R.drawable.lulu, R.drawable.sivir, R.drawable.vi,
            R.drawable.zak, R.drawable.ziggs, R.drawable.zilean, R.drawable.aurelion, R.drawable.braum,
            R.drawable.gragas, R.drawable.kartus, R.drawable.korki, R.drawable.nautilus, R.drawable.nocturn,
            R.drawable.svein, R.drawable.tarik, R.drawable.ekko, R.drawable.jinx, R.drawable.lee,
            R.drawable.senjuani, R.drawable.soraka, R.drawable.trindamire, R.drawable.veigar, R.drawable.vein,
            R.drawable.hecarim, R.drawable.brand, R.drawable.kalista, R.drawable.lissandra, R.drawable.singet,
            R.drawable.taamkench, R.drawable.tw, R.drawable.kassadin, R.drawable.rakan, R.drawable.xayah,
            R.drawable.sindra, R.drawable.yasuo, R.drawable.fiddlesticks, R.drawable.fortuna, R.drawable.kamilla,
            R.drawable.leona, R.drawable.shen, R.drawable.taliyah, R.drawable.varus, R.drawable.azir,
            R.drawable.akali, R.drawable.anivia, R.drawable.bard, R.drawable.fizz, R.drawable.kled,
            R.drawable.twitch, R.drawable.reksai, R.drawable.eliza, R.drawable.caitlyn, R.drawable.malzahar,
            R.drawable.panteon, R.drawable.renekton, R.drawable.skarner, R.drawable.sona, R.drawable.trandl,
            R.drawable.kennen, R.drawable.masteryi, R.drawable.nunu, R.drawable.ramble, R.drawable.rammus, R.drawable.zed,
            R.drawable.annie, R.drawable.galio, R.drawable.irelia, R.drawable.katarina, R.drawable.maokai,
            R.drawable.ryze, R.drawable.evelynn, R.drawable.urgot, R.drawable.ahri, R.drawable.atroks,
            R.drawable.blitzkrank, R.drawable.victor, R.drawable.xin, R.drawable.kaisa, R.drawable.kindred,
            R.drawable.warwik, R.drawable.garen, R.drawable.heim, R.drawable.iorik, R.drawable.kain,
            R.drawable.nasus, R.drawable.olaf, R.drawable.shivana, R.drawable.ashe, R.drawable.ilaoy,
            R.drawable.mordekaizer, R.drawable.orianna, R.drawable.orn, R.drawable.teemo, R.drawable.volibear,
            R.drawable.wukong, R.drawable.amumu, R.drawable.gnar, R.drawable.jarvan, R.drawable.zoe,
            R.drawable.alistar, R.drawable.ivern, R.drawable.talon, R.drawable.tristana, R.drawable.payk
    };

    @Override
    public void onBackPressed(){
      finish();
        Intent intent = new Intent(MainActivity.this, Menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        mscore = 0;
        if (timer != null) {
            timer.cancel();
        }
        countDownT.cancel();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageQuestView = (ImageView) findViewById(R.id.imageQuest);
        but1 = (Button) findViewById(R.id.button1);
        but2 = (Button) findViewById(R.id.button2);
        but3 = (Button) findViewById(R.id.button3);
        but4 = (Button) findViewById(R.id.button4);
        score = (TextView)findViewById(R.id.Score);
        point = (ImageView)findViewById(R.id.imageScore);
        PB = (ProgressBar) findViewById(R.id.progressBar);

        Quize();
        UpdateQuestion(r.nextInt(trueOtvets.length));
        countDownT.start();


        sPref = getSharedPreferences("saved_pos" ,MODE_PRIVATE);
        bTrueMusic = sPref.getBoolean("saved_pos", bTrueMusic);
        sPrefSound = getSharedPreferences("savedS" ,MODE_PRIVATE);
        bTrueSound = sPrefSound.getBoolean("savedS", bTrueSound);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (bTrueMusic) MyService.start();
        LoadS();
    }

    @Override
    public void onPause(){
        super.onPause();
        MyService.pause();
    }

    @Override
    public void onStop(){
        super.onStop();
        SaveS();
    }


    public int getQuest(int a){
        questions = imagesList[a];
        return questions;
    }
    public String getChouses1(int a) {
        List.clear();
        for (int i = 0; i <= 3; i++)
            List.add(i, otvets[a][i]);
        Collections.shuffle(List);
        return List.get(0);
    }
    public String getChouses2(){
        return List.get(1);
    }
    public String getChouses3(){
        return List.get(2);
    }
    public String getChouses4(){
        return List.get(3);
    }
    public String getTrue(int a){ return trueOtvets[a]; }



    public void Quize(){
        scene = r.nextInt(5);

        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countDownT.cancel();
                timer = new Timer();
                Button();
                if (but1.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
                    Animation anim;
                    anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Mytask = new Mytask();
                    timer.schedule(Mytask, 2000);
                    List.clear();
                    mscore = mscore + 1;
                    Updatescore(mscore);
                    recList.add(nCorrect);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    Toast toast = Toast.makeText(MainActivity.this, "Игра окончена. Правильных ответов: " + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                    timer = new Timer();
                    Mytask2 = new Mytask2();
                    timer.schedule(Mytask2, 2000);
                }
            }
        });
        but2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                timer = new Timer();
                Button();
                countDownT.cancel();
                if (but2.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
                    Animation anim;
                    anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Mytask = new Mytask();
                    timer.schedule(Mytask, 2000);
                    List.clear();
                    mscore = mscore +1;
                    Updatescore(mscore);
                    recList.add(nCorrect);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    Toast toast = Toast.makeText(MainActivity.this, "Игра окончена. Правильных ответов: " + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                    timer = new Timer();
                    Mytask2 = new Mytask2();
                    timer.schedule(Mytask2, 2000);
                }
            }
        });
        but3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                timer = new Timer();
                Button();
                countDownT.cancel();
                if (but3.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
                    Animation anim;
                    anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Mytask = new Mytask();
                    timer.schedule(Mytask, 2000);
                    List.clear();
                    mscore = mscore +1;
                    Updatescore(mscore);
                    recList.add(nCorrect);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    Toast toast = Toast.makeText(MainActivity.this, "Игра окончена. Правильных ответов: " + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                    timer = new Timer();
                    Mytask2 = new Mytask2();
                    timer.schedule(Mytask2, 2000);
                }
            }
        });
        but4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                timer = new Timer();
                Button();
                countDownT.cancel();
                if (but4.getText() == nCorrect){
                    if (bTrueSound)
                        Sound.playSound(Menu.soundTrue);
                    Animation anim;
                    anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mytrans);
                    point.startAnimation(anim);
                    Mytask = new Mytask();
                    timer.schedule(Mytask, 2000);
                    List.clear();
                    mscore = mscore +1;
                    Updatescore(mscore);
                    recList.add(nCorrect);
                }
                else {
                    if (bTrueSound)
                        Sound.playSound(Menu.soundFalse);
                    Toast toast = Toast.makeText(MainActivity.this, "Игра окончена. Правильных ответов: " + mscore, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                    timer = new Timer();
                    Mytask2 = new Mytask2();
                    timer.schedule(Mytask2, 2000);
                }
            }
        });
    }
    void Button(){
        if (but1.getText()==nCorrect)
            but1.setEnabled(false);
        else {
            but1.setEnabled(false);
            but1.setVisibility(View.INVISIBLE);
        }
        if (but2.getText()==nCorrect)
            but2.setEnabled(false);
        else {
            but2.setEnabled(false);
            but2.setVisibility(View.INVISIBLE);
        }
        if (but3.getText()==nCorrect)
            but3.setEnabled(false);
        else {
            but3.setEnabled(false);
            but3.setVisibility(View.INVISIBLE);
        }
        if (but4.getText()==nCorrect)
            but4.setEnabled(false);
        else {
            but4.setEnabled(false);
            but4.setVisibility(View.INVISIBLE);
        }
    }

    public void UpdateQuestion(int num){
        imageQuestView.setImageResource(getQuest(num));
        but1.setText(getChouses1(num));
        but2.setText(getChouses2());
        but3.setText(getChouses3());
        but4.setText(getChouses4());
        nCorrect = getTrue(num);
    }

// Метод набора очков
  private void Updatescore(int point){score.setText("" + mscore);}

    public void SaveS(){
        sPrefPicture = getSharedPreferences(SAVED_PICTURE, MODE_PRIVATE);
        SharedPreferences.Editor edPicture = sPrefPicture.edit();
        edPicture.putStringSet("Picture", recList);
        edPicture.apply();
}

    public void LoadS(){
        sPrefPicture = getSharedPreferences(SAVED_PICTURE, MODE_PRIVATE);
        ret = sPrefPicture.getStringSet("Picture", new HashSet<String>());
            recList.addAll(ret);
    }

    private class Mytask extends TimerTask{
        @Override
                public void run() {
            switch (scene){
                case 0:
                case 1:
                case 2:Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    break;
                case 3:
                case 4:
                case 5:Intent inte = new Intent(MainActivity.this, Quest.class);
                    inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inte);
                    finish();
                    break;
            }
        }
    }

    private class Mytask2 extends TimerTask{
        @Override
        public void run(){
            Intent intent = new Intent(MainActivity.this, Menu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            PB.setProgress((int)(l/1000));
        }
        @Override
        public void onFinish(){
            Toast toast = Toast.makeText(MainActivity.this, "Игра окончена. Правильных ответов: " + mscore, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
            timer = new Timer();
            Mytask2 = new Mytask2();
            timer.schedule(Mytask2, 2000);
            Button();
        }
    }
    }





