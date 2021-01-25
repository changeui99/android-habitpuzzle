package com.changeui.habbitpuzzle;

import android.util.Log;

import java.util.ArrayList;

public class CollectionFragmentPresenter {

    private CollectionFragmentModel model;
    private CollectionFragmentView view;
    private Data data;

    public CollectionFragmentPresenter(CollectionFragmentView view) {
        this.model = new CollectionFragmentModel();
        this.data = startActivity.data;
        this.view = view;

        model.setCollections(data.getCollectiondatas());
        model.setHabitDatas(data.getHabitdatas());
        model.sortData();
    }

    public ArrayList<CollectionData> getProgress() {
        return model.getProgress();
    }

    public ArrayList<CollectionData> getCollections(){
        return model.collections;
    }

    public int uiWidth(){
        return (int)((data.getDisplayWidth() / 2) - 36 * data.getDpi());
    }

    public boolean isFinished(int id){
        if (data.getCollections().contains(id)){
            return true;
        } else
            return false;
    }

    public long getHabitId(int id){
        ArrayList<HabitData> temparray = data.getHabitdatas();

        for (int i = 0; i < temparray.size(); i++) {
            if (temparray.get(i).getCollections().contains(id)){
                return temparray.get(i).getId();
            }
        }

        return 0;
    }

    public interface View {

    }
}
