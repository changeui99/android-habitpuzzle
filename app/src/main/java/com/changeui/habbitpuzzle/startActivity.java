package com.changeui.habbitpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class startActivity extends AppCompatActivity {

    public static Data data = null;
    private DatabaseHelper dbhelper = null;
    private ImageView logo, start_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        data = new Data(getApplicationContext().getResources().getDisplayMetrics());

        logo = findViewById(R.id.logo);
        start_image = findViewById(R.id.start_image);

        logo.getLayoutParams().width = (int) (data.getDisplayWidth() * 0.45);
        start_image.getLayoutParams().height = (int) (data.getDisplayHeight() * 0.5);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    dbhelper = new DatabaseHelper(getApplicationContext());

                    ArrayList<HabitData> tempDatas = dbhelper.getHabitData();
                    ArrayList<String> habitList = dbhelper.getHabitList();
                    ArrayList<Integer> collections = dbhelper.getCollections();
                    data.setHabitdatas(tempDatas);
                    data.setCustomHabit(habitList);
                    data.setCollections(collections);

                    while (data == null || dbhelper == null || !dbhelper.isIsfinished()){
                        Thread.sleep(1000);
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivityView.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        dbhelper.end();
        super.onDestroy();
    }
}