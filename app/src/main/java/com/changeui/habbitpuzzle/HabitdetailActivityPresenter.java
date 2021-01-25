package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class HabitdetailActivityPresenter {
    private HabitdetailActivityView view;
    private HabitdetailActivityModel model;
    private HabitData habitData;
    private Data data;
    private DatabaseHelper dbhelper;

    public HabitdetailActivityPresenter(HabitdetailActivityView view) {
        this.data = startActivity.data;
        this.model = new HabitdetailActivityModel();
        this.view = view;
        dbhelper = HomeFragmentPresenter.dbhelper;
    }

    public int getWidth(){
        return data.getDisplayWidth();
    }

    public float getDip(){
        return data.getDpi();
    }

    public int getPage(){
        return data.getPage();
    }

    public int getCurrentPage(){
        return model.getCurrentPage();
    }

    public void setCurrentPage(int i){
        model.setCurrentPage(i);
    }

    public void addCurrent(){
        model.setCurrentPage(model.getCurrentPage() + 1);
    }

    public void subCurrent(){
        model.setCurrentPage(model.getCurrentPage() - 1);
    }

    public void setHabitData(long id) {
        ArrayList<HabitData> temp = data.getHabitdatas();
        for (int i = 0; i < temp.size(); i++){
            if (temp.get(i).getId() == id) {
                habitData = temp.get(i);
                break;
            }
        }
    }

    public String getTitle(int id){
        return data.getCollecionsWithID(id).getTitle();
    }

    public void resetPuzzle(){
        habitData.resetPuzzle();
        dbhelper.updateHabitPuzzleState(habitData.getId(), habitData.puzzleToString());
    }

    public void addCollections(int id){
        habitData.addToCollections(id);
        dbhelper.updateHabitCollections(habitData.getId(), habitData.getCollectionsAsString());
    }

    public HabitData getHabitData(){
        return habitData;
    }

    private String getMonth(int i){
        if (i == 0) {
            return "January";
        } else if (i == 1){
            return "February";
        } else if (i == 2){
            return "March";
        } else if (i == 3){
            return "April";
        } else if (i == 4){
            return "May";
        } else if (i == 5){
            return "June";
        } else if (i == 6){
            return "July";
        } else if (i == 7){
            return "August";
        } else if (i == 8){
            return "September";
        } else if (i == 9){
            return "October";
        } else if (i == 10){
            return "November";
        } else {
            return "December";
        }
    }

    public interface View {

    }
}
