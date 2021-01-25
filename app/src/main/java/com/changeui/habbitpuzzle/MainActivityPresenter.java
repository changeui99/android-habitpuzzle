package com.changeui.habbitpuzzle;

public class MainActivityPresenter {
    private MainActivityModel model;
    private Data data;
    private DatabaseHelper dbhelper;
    private MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        this.model = new MainActivityModel();
        this.data = startActivity.data;
        this.view = view;
        dbhelper = new DatabaseHelper(view.getApplicationContext());
    }

    public boolean isCurrent(int i){
        if (i == model.getCurrent_position()){
            return true;
        } else {
            return false;
        }
    }

    public DatabaseHelper getDbhelper() {
        return dbhelper;
    }

    public void end(){
        dbhelper.end();
    }

    public void setCurrent(int i){
        model.setCurrent_position(i);
    }

    public int getCurrent(){
        return model.getCurrent_position();
    }

    //view data
    public float navFalseWH(){
        return 40 * data.getDpi();
    }

    public interface View{
        void setInitialViews();
        void navClicked(int i);
    }
}
