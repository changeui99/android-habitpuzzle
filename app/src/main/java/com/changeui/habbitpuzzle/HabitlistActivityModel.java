package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class HabitlistActivityModel {

    private int selecedMode = 0;

    private ArrayList<String> everyday_title = new ArrayList<String>();
    private ArrayList<String> week5 = new ArrayList<String>();
    private ArrayList<String> week2 = new ArrayList<String>();
    private ArrayList<String> custom = new ArrayList<String>();

    public HabitlistActivityModel() {
        everyday_title.add("wake up");
        everyday_title.add("eat breakfast");
        everyday_title.add("eat lunch");
        everyday_title.add("eat dinner");
        everyday_title.add("brush teeth");
        everyday_title.add("take shower");
        everyday_title.add("walk my dog");
        everyday_title.add("set alarm for tomorrow");
        everyday_title.add("after a lecture ended");
        everyday_title.add("watching Netfilx");

        week5.add("go to work");
        week5.add("after work");
        week5.add("change into outfit");
        week5.add("get on bus/subway/car");
        week5.add("go to school");
        week5.add("after school");

        week2.add("Groceries Shopping");
        week2.add("Church / Cathedral");
        week2.add("Waste sorting");
        week2.add("Group study");
        week2.add("Club activities");
        week2.add("watch TV program");
    }

    public void setCustom(ArrayList<String> custom) {
        this.custom = custom;
    }

    public void addCustom(String s){
        custom.add(s);
    }

    public ArrayList<String> getEveryday_title() {
        return everyday_title;
    }

    public ArrayList<String> getWeek5() {
        return week5;
    }

    public ArrayList<String> getWeek2() {
        return week2;
    }

    public ArrayList<String> getCustom() {
        return custom;
    }

    public int getSelecedMode() {
        return selecedMode;
    }

    public void setSelecedMode(int selecedMode) {
        this.selecedMode = selecedMode;
    }
}
