package com.changeui.habbitpuzzle;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentPresenter {
    private HomeFragmentModel model;
    private Data data;
    public static DatabaseHelper dbhelper;
    private HomeFragmentView view;

    public HomeFragmentPresenter(HomeFragmentView view, DatabaseHelper dbhelper) {
        this.model = new HomeFragmentModel();
        data = startActivity.data;
        this.dbhelper = dbhelper;
        this.view = view;
    }

    public ArrayList<HabitData> getTodays(){
        return data.today();
    }

    public ArrayList<HabitData> getOthers(){
        return data.others();
    }

    public void updateHabitPuzzleState(long id, String s){
        dbhelper.updateHabitPuzzleState(id, s);
    }

    public void addData(String initialhabit, String newhabit, int color, boolean[] days, String startday, ArrayList<Integer> collections){
        ArrayList<String> tmep = new ArrayList<>();
        HabitData tempdata = new HabitData(data.getHabitdatas().size() + 1 ,initialhabit, newhabit , color, days, startday, tmep);
        tempdata.setCollections(collections);
        data.addHabitdata(tempdata);
        dbhelper.addData(initialhabit, newhabit , color, days, startday, tempdata.getCollectionsAsString(), tempdata.puzzleToString());
    }

    public void update(long id, String s){
        dbhelper.updateSuccessDays(id, s);
    }

    public interface View {
        void updateDatas();
    }
}
