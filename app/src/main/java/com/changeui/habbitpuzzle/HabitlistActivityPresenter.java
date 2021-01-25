package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class HabitlistActivityPresenter {
    private HabitlistActivityModel model;
    private HabitlistActivityView view;
    private Data data;
    private DatabaseHelper dbhelper;

    public HabitlistActivityPresenter(HabitlistActivityView view) {
        model = new HabitlistActivityModel();
        dbhelper = HomeFragmentPresenter.dbhelper;
        this.view = view;
        data = startActivity.data;

        model.setCustom(data.getCustomHabit());
    }

    public void addHabit(String s){
        model.addCustom(s);
        dbhelper.addHbit(s);
        view.updateAdapter();
    }

    public ArrayList<String> everydayTitles(){
        return model.getEveryday_title();
    }

    public ArrayList<String> week5Titles(){
        return model.getWeek5();
    }

    public ArrayList<String> week2Titles(){
        return model.getWeek2();
    }

    public ArrayList<String> customTitle() {
        return model.getCustom();
    }

    public void updateCurrentSelected(int i){
        model.setSelecedMode(i);
    }

    public int getCurrentSelected() {
        return model.getSelecedMode();
    }

    public interface View {
        void updateSelected(int i);
        void updateAdapter();
    }
}
