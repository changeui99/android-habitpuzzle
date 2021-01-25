package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class CollectionFragmentModel {

    private ArrayList<HabitData> habitDatas = new ArrayList<>();
    public ArrayList<CollectionData> progress = new ArrayList<>();
    public ArrayList<CollectionData> collections = new ArrayList<>();

    public CollectionFragmentModel() {

    }

    public ArrayList<HabitData> getHabitDatas() {
        return habitDatas;
    }

    public void setHabitDatas(ArrayList<HabitData> habitDatas) {
        this.habitDatas = habitDatas;
    }

    public ArrayList<CollectionData> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<CollectionData> collections) {
        this.collections = collections;
    }

    public ArrayList<CollectionData> getProgress() {
        return progress;
    }

    public void sortData(){
        ArrayList<CollectionData> temp = new ArrayList<>();

        for (int i = 0; i < collections.size(); i++){
            boolean isin = false;

            for (int j = 0; j < habitDatas.size(); j++){
                if (!habitDatas.get(j).isComplete()){
                    ArrayList<Integer> temparray = habitDatas.get(j).getCollections();
                    if (temparray.get(temparray.size() - 1) == collections.get(i).getId()){
                        isin = true;
                        break;
                    }
                }
            }

            if (!isin){
                temp.add(collections.get(i));
            } else {
                progress.add(collections.get(i));
            }
        }

        setCollections(temp);
    }
}
