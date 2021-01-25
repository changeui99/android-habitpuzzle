package com.changeui.habbitpuzzle;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class HabitData {
    long id;
    String initialHabit;
    String newHabit;
    int themeColor = 0;
    boolean[] days;
    ArrayList<String> successdays;
    String startday;
    Calendar calendar, startcalendar;
    private DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

    int daynumber = 0, weekday = 0;

    int currentDay;

    ArrayList<Integer> collections = new ArrayList<>();
    int puzzle[][] = new int[5][9];

    public HabitData(long id, String initialHabit, String newHabit, int themeColor, boolean[] days, String startday,
                     ArrayList<String> successdays) {
        this.id = id;
        this.initialHabit = initialHabit;
        this.newHabit = newHabit;
        this.themeColor = themeColor;
        this.days = days;
        this.startday = startday;
        this.successdays = successdays;

        calendar = Calendar.getInstance();
        startcalendar = Calendar.getInstance();
        currentDay = (calendar.get(Calendar.YEAR) - 2010) * 12 + calendar.get(Calendar.MONTH);

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                puzzle[i][j] = 0;
            }
        }

        for (int i = 0; i < days.length; i++){
            if (days[i]){
                weekday ++;
            }
        }

        try {
            startcalendar.setTime(df.parse(startday));

            for (int i = getDayofWeek(startcalendar.get(Calendar.DAY_OF_WEEK)); i < days.length; i++){
                if (startcalendar.getTimeInMillis() < calendar.getTimeInMillis()){
                    if (days[i]){
                        daynumber ++;
                        startcalendar.add(Calendar.DATE, 1);
                    }
                }
            }

            while (7 * 24 * 60 * 60 * 1000 + startcalendar.getTimeInMillis() < calendar.getTimeInMillis()){
                daynumber = daynumber + weekday;
                startcalendar.add(Calendar.DATE, 7);
            }

            while (startcalendar.getTimeInMillis() < calendar.getTimeInMillis()){
                if (days[getDayofWeek(startcalendar.get(Calendar.DAY_OF_WEEK))]){
                    daynumber ++;
                }
                startcalendar.add(Calendar.DATE, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean needadd(){
        if (collections.size() == 0){
            return true;
        }

        if (isComplete()){
            return true;
        }

        return false;
    }

    public boolean isComplete(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                if (puzzle[i][j] != 2){
                    return false;
                }
            }
        }

        return true;
    }

    public String getCollectionsAsString(){
        String temp = "";

        for (int i = 0; i < collections.size(); i++){
            temp += Integer.toString(collections.get(i));

            if (i != collections.size() - 1){
                temp += ",";
            }
        }

        return temp;
    }

    public int puzzleNumber(){
        int count = 0;

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                if (puzzle[i][j] != 0){
                    count++;
                }
            }
        }

        return count;
    }

    public void addPuzzle(int number){
        for (int a = 0; a < number; a++) {
            if (puzzleNumber() < 45){
                Random rand = new Random();
                ArrayList<Integer> able = new ArrayList<>();

                for (int i = 0; i < 5; i++){
                    for (int j = 0; j < 9; j++){
                        if (isAble(i, j)) {
                            able.add(i * 9 + j);
                        }
                    }
                }

                int index = able.get(rand.nextInt(able.size()));
                puzzle[index / 9][index % 9] = 1;
            }
        }
    }

    private boolean isAble(int i, int j){
        if (puzzle[i][j] != 0){
            return false;
        }

        if (i == 0 || i == 4){
            return true;
        }

        if (j == 0 || j == 8){
            return true;
        }

        if (puzzle[i-1][j] != 0 || puzzle[i+1][j] != 0 || puzzle[i][j-1] != 0 || puzzle[i][j+1] != 0) {
            return true;
        }

        return false;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    public ArrayList<Integer> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<Integer> collections) {
        this.collections = collections;
    }

    public void addToCollections(int id){
        collections.add(id);
    }

    public String puzzleToString() {
        String temp = "";

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                temp += Integer.toString(puzzle[i][j]);

                if (i != 4 || j != 8){
                    temp += ",";
                }
            }
        }

        return temp;
    }

    public void addSuccessDays(String s){
        successdays.add(s);
    }

    public ArrayList<String> getSuccessdays() {
        return successdays;
    }

    public String getSuccessdaysAsString(){
        String temp = "";

        for (int i = 0; i < successdays.size(); i++){
            temp += successdays.get(i);
            if (i != successdays.size() -1){
                temp += ",";
            }
            Log.e("test", "getSuccessdaysAsString: " + successdays.get(i));
        }

        return temp;
    }

    public void resetPuzzle(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                puzzle[i][j] = 0;
            }
        }
    }

    public int getDaynumber() {
        return daynumber;
    }

    public String getStartday() {
        return startday;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public boolean[] getDays() {
        return days;
    }

    public boolean isToday(int i){
        if (days[i]){
            return true;
        } else {
            return false;
        }
    }

    public void setPuzzleState(int i, int j, int k){
        puzzle[i][j] = k;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitialHabit() {
        return initialHabit;
    }

    public void setInitialHabit(String initialHabit) {
        this.initialHabit = initialHabit;
    }

    public String getNewHabit() {
        return newHabit;
    }

    public void setNewHabit(String newHabit) {
        this.newHabit = newHabit;
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }

    public int getDayofWeek(int i){
        switch (i){
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
