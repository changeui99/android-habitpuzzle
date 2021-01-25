package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class CollectionSelectActivityPresenter {
    private CollectionSelectActivityModel model;
    private CollectionSelectActivityView view;
    private Data data;

    public CollectionSelectActivityPresenter(CollectionSelectActivityView view) {
        model = new CollectionSelectActivityModel();
        this.view = view;
        data = startActivity.data;

        model.setCollectionDatas(data.getCollectiondatas());
        model.setHabitDatas(data.getHabitdatas());
        model.sortData();
    }

    public ArrayList<CollectionData> getCollections(){
        return model.getCollectionDatas();
    }

    public int getWidth(){
        return (int)((data.getDisplayWidth() / 2) - 36 * data.getDpi());
    }

    public interface View {

    }
}
