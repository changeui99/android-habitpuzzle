package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class CollectionSelectActivityModel {

    private ArrayList<HabitData> habitDatas = new ArrayList<>();
    private ArrayList<CollectionData> collectionDatas = new ArrayList<>();

    public CollectionSelectActivityModel() {

    }

    public ArrayList<CollectionData> getCollectionDatas() {
        return collectionDatas;
    }

    public void setCollectionDatas(ArrayList<CollectionData> collectionDatas) {
        this.collectionDatas = collectionDatas;
    }

    public ArrayList<HabitData> getHabitDatas() {
        return habitDatas;
    }

    public void setHabitDatas(ArrayList<HabitData> habitDatas) {
        this.habitDatas = habitDatas;
    }

    public void sortData(){
        ArrayList<CollectionData> temp = new ArrayList<>();

        for (int i = 0; i < collectionDatas.size(); i++){
            boolean isin = false;

            for (int j = 0; j < habitDatas.size(); j++){
                if (habitDatas.get(j).getCollections().contains(collectionDatas.get(i).getId())){
                    isin = true;
                    break;
                }
            }

            if (!isin){
                temp.add(collectionDatas.get(i));
            }
        }

        setCollectionDatas(temp);
    }
}
