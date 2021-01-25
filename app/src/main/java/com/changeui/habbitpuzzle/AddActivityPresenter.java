package com.changeui.habbitpuzzle;

import android.content.Intent;
import android.widget.TextView;

public class AddActivityPresenter {

    private AddActivityModel model;
    private AddActivityView view;
    private Data data;

    public AddActivityPresenter(AddActivityView view) {
        this.view = view;
        this.model = new AddActivityModel();
        data = startActivity.data;
    }

    public void updateInitial(String s){
        model.setInitialHabit(s);
    }

    public void updateNew(String s){
        model.setNewHabit(s);
    }

    public Intent makeIntent(){
        Intent intent = new Intent();
        intent.putExtra("initialHabit", model.getInitialHabit());
        intent.putExtra("newHabit", model.getNewHabit());
        intent.putExtra("color", model.getCurrentColor());
        intent.putExtra("days", model.getDays());
        intent.putIntegerArrayListExtra("collections", model.getCollections());

        return intent;
    }

    public void addCollections(int id){
        model.getCollections().add(id);
    }

    public boolean isAllChecked(){
        if (model.getInitialHabit() != null && model.getNewHabit() != null){
            return true;
        } else {
            return false;
        }
    }

    public boolean isAllunChecked(){
        if (!model.isMon() && !model.isFri() && !model.isTue() && !model.isThu() && !model.isFri() && !model.isSat() && !model.isSun()){
            return false;
        } else {
            return true;
        }
    }

    public boolean updateDays(int i){
        if (i == 0){
            model.setMon(!model.isMon());
            return model.isMon();
        } else if (i == 1){
            model.setTue(!model.isTue());
            return model.isTue();
        } else if (i == 2){
            model.setWed(!model.isWed());
            return model.isWed();
        } else if (i == 3){
            model.setThu(!model.isThu());
            return model.isThu();
        } else if (i == 4){
            model.setFri(!model.isFri());
            return model.isFri();
        } else if (i == 5){
            model.setSat(!model.isSat());
            return model.isSat();
        } else {
            model.setSun(!model.isSun());
            return model.isSun();
        }
    }

    public void updateCurrentColor(int i){
        model.setCurrentColor(i);
    }

    public boolean isColorChanged(int i){
        if (i == model.getCurrentColor()){
            return false;
        } else {
            return true;
        }
    }

    public int getCurrentColor(){
        return model.getCurrentColor();
    }

    public interface View {
        void updateToCurrentSettings(int color);
        void updateDaysBG(boolean tf, TextView textView);
    }
}
