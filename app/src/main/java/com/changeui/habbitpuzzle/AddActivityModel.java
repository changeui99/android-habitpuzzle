package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class AddActivityModel {
    private String initialHabit = null;
    private String newHabit = null;

    private boolean mon = true;
    private boolean tue = true;
    private boolean wed = true;
    private boolean thu = true;
    private boolean fri = true;
    private boolean sat = false;
    private boolean sun = false;

    private int currentColor = 3;

    private ArrayList<Integer> collections = new ArrayList<>();

    public AddActivityModel() {

    }

    public ArrayList<Integer> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<Integer> collections) {
        this.collections = collections;
    }

    public boolean[] getDays(){
        return new boolean[]{mon, tue, wed, thu, fri, sat, sun};
    }

    public boolean isMon() {
        return mon;
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

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }
}
