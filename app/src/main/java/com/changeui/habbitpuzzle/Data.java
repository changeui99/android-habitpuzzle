package com.changeui.habbitpuzzle;

import android.util.DisplayMetrics;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Data {
    private int displayHeight;
    private int displayWidth;
    private DisplayMetrics displayMetrics;
    private Calendar calendar;
    public ArrayList<HabitData> habitdatas = new ArrayList<HabitData>();
    public ArrayList<String> customHabit = new ArrayList<>();
    public ArrayList<Integer> collections = new ArrayList<>();
    public ArrayList<CollectionData> collectiondatas = new ArrayList<>();

    public Data(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;

        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;

        calendar = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String date = df.format(Calendar.getInstance().getTime());

        collectiondatas.add(new CollectionData(1001, "Happy Halloween!", "Halloween has come to Puzzle Family. Let's find out what kind of makeup our friends have prepared this time!",
                R.drawable.habitpuzzle_1001, R.drawable.habitpuzzle_1001_s,
                new int[]{
                        R.drawable.puzzle1001_1, R.drawable.puzzle1001_2, R.drawable.puzzle1001_3, R.drawable.puzzle1001_4, R.drawable.puzzle1001_5, R.drawable.puzzle1001_6, R.drawable.puzzle1001_7, R.drawable.puzzle1001_8, R.drawable.puzzle1001_9, R.drawable.puzzle1001_10,
                        R.drawable.puzzle1001_11, R.drawable.puzzle1001_12, R.drawable.puzzle1001_13, R.drawable.puzzle1001_14, R.drawable.puzzle1001_15, R.drawable.puzzle1001_16, R.drawable.puzzle1001_17, R.drawable.puzzle1001_18, R.drawable.puzzle1001_19, R.drawable.puzzle1001_20,
                        R.drawable.puzzle1001_21, R.drawable.puzzle1001_22, R.drawable.puzzle1001_23, R.drawable.puzzle1001_24, R.drawable.puzzle1001_25, R.drawable.puzzle1001_26, R.drawable.puzzle1001_27, R.drawable.puzzle1001_28, R.drawable.puzzle1001_29, R.drawable.puzzle1001_30,
                        R.drawable.puzzle1001_31, R.drawable.puzzle1001_32, R.drawable.puzzle1001_33, R.drawable.puzzle1001_34, R.drawable.puzzle1001_35, R.drawable.puzzle1001_36, R.drawable.puzzle1001_37, R.drawable.puzzle1001_38, R.drawable.puzzle1001_39, R.drawable.puzzle1001_40,
                        R.drawable.puzzle1001_41, R.drawable.puzzle1001_42, R.drawable.puzzle1001_43, R.drawable.puzzle1001_44, R.drawable.puzzle1001_45
                }));
        collectiondatas.add(new CollectionData(2001, "Daily Life on Company", "Puzzle Family joined the company. Let's see what they look like at the company.", R.drawable.habitpuzzle_2001, R.drawable.habitpuzzle_2001_s,
                new int[]{
                        R.drawable.puzzle2001_1, R.drawable.puzzle2001_2, R.drawable.puzzle2001_3, R.drawable.puzzle2001_4, R.drawable.puzzle2001_5, R.drawable.puzzle2001_6, R.drawable.puzzle2001_7, R.drawable.puzzle2001_8, R.drawable.puzzle2001_9, R.drawable.puzzle2001_10,
                        R.drawable.puzzle2001_11, R.drawable.puzzle2001_12, R.drawable.puzzle2001_13, R.drawable.puzzle2001_14, R.drawable.puzzle2001_15, R.drawable.puzzle2001_16, R.drawable.puzzle2001_17, R.drawable.puzzle2001_18, R.drawable.puzzle2001_19, R.drawable.puzzle2001_20,
                        R.drawable.puzzle2001_21, R.drawable.puzzle2001_22, R.drawable.puzzle2001_23, R.drawable.puzzle2001_24, R.drawable.puzzle2001_25, R.drawable.puzzle2001_26, R.drawable.puzzle2001_27, R.drawable.puzzle2001_28, R.drawable.puzzle2001_29, R.drawable.puzzle2001_30,
                        R.drawable.puzzle2001_31, R.drawable.puzzle2001_32, R.drawable.puzzle2001_33, R.drawable.puzzle2001_34, R.drawable.puzzle2001_35, R.drawable.puzzle2001_36, R.drawable.puzzle2001_37, R.drawable.puzzle2001_38, R.drawable.puzzle2001_39, R.drawable.puzzle2001_40,
                        R.drawable.puzzle2001_41, R.drawable.puzzle2001_42, R.drawable.puzzle2001_43, R.drawable.puzzle2001_44, R.drawable.puzzle2001_45
                }));
        collectiondatas.add(new CollectionData(3001, "Travel to Saipan", "Puzzle Family went on a trip. Let's find out where they go. (Hint : ocean)", R.drawable.habitpuzzle_3001, R.drawable.habitpuzzle_3001_s,
                new int[]{
                        R.drawable.puzzle3001_1, R.drawable.puzzle3001_2, R.drawable.puzzle3001_3, R.drawable.puzzle3001_4, R.drawable.puzzle3001_5, R.drawable.puzzle3001_6, R.drawable.puzzle3001_7, R.drawable.puzzle3001_8, R.drawable.puzzle3001_9, R.drawable.puzzle3001_10,
                        R.drawable.puzzle3001_11, R.drawable.puzzle3001_12, R.drawable.puzzle3001_13, R.drawable.puzzle3001_14, R.drawable.puzzle3001_15, R.drawable.puzzle3001_16, R.drawable.puzzle3001_17, R.drawable.puzzle3001_18, R.drawable.puzzle3001_19, R.drawable.puzzle3001_20,
                        R.drawable.puzzle3001_21, R.drawable.puzzle3001_22, R.drawable.puzzle3001_23, R.drawable.puzzle3001_24, R.drawable.puzzle3001_25, R.drawable.puzzle3001_26, R.drawable.puzzle3001_27, R.drawable.puzzle3001_28, R.drawable.puzzle3001_29, R.drawable.puzzle3001_30,
                        R.drawable.puzzle3001_31, R.drawable.puzzle3001_32, R.drawable.puzzle3001_33, R.drawable.puzzle3001_34, R.drawable.puzzle3001_35, R.drawable.puzzle3001_36, R.drawable.puzzle3001_37, R.drawable.puzzle3001_38, R.drawable.puzzle3001_39, R.drawable.puzzle3001_40,
                        R.drawable.puzzle3001_41, R.drawable.puzzle3001_42, R.drawable.puzzle3001_43, R.drawable.puzzle3001_44, R.drawable.puzzle3001_45
                }));
    }

    public ArrayList<Integer> getCollections() {
        return collections;
    }

    public ArrayList<CollectionData> getCollectiondatas() {
        return collectiondatas;
    }

    public void setCollectiondatas(ArrayList<CollectionData> collectiondatas) {
        this.collectiondatas = collectiondatas;
    }

    public CollectionData getCollecionsWithID(int id){
        for (int i = 0; i < collectiondatas.size(); i++){
            if (id == collectiondatas.get(i).getId()){
                return collectiondatas.get(i);
            }
        }

        return null;
    }

    public HabitData getHabitDataWidtID(long id){
        for (int i = 0; i < habitdatas.size(); i++){
            if (id == habitdatas.get(i).getId()){
                return habitdatas.get(i);
            }
        }

        return null;
    }

    public void setCollections(ArrayList<Integer> collections) {
        this.collections = collections;
    }

    public ArrayList<String> getCustomHabit() {
        return customHabit;
    }

    public void setCustomHabit(ArrayList<String> customHabit) {
        this.customHabit = customHabit;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public ArrayList<HabitData> getHabitdatas() {
        return habitdatas;
    }

    public void setHabitdatas(ArrayList<HabitData> habitdatas) {
        this.habitdatas = habitdatas;
    }

    public void addHabitdata(HabitData habitData){
        this.habitdatas.add(habitData);
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public float getDpi(){
        return displayMetrics.density;
    }

    public ArrayList<HabitData> today(){
        ArrayList<HabitData> temp = new ArrayList<>();

        for (int i = 0; i < habitdatas.size(); i++){
            if (habitdatas.get(i).isToday(getDayofWeek())){
                temp.add(habitdatas.get(i));
            }
        }

        return temp;
    }

    public ArrayList<HabitData> others(){
        ArrayList<HabitData> temp = new ArrayList<>();

        for (int i = 0; i < habitdatas.size(); i++){
            if (!habitdatas.get(i).isToday(getDayofWeek())){
                temp.add(habitdatas.get(i));
            }
        }

        return temp;
    }

    public int getPage() {
        return (calendar.get(Calendar.YEAR) - 2010) * 12 + calendar.get(Calendar.MONTH);
    }

    public int getDayofWeek(){
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            case 1:
                return 6;
        }

        return 0;
    }
}
