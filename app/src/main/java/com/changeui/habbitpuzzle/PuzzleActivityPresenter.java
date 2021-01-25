package com.changeui.habbitpuzzle;

import android.util.Log;

public class PuzzleActivityPresenter {

    private PuzzleActivityModel model;
    private PuzzleActivityView view;
    private Data data;
    private DatabaseHelper dbhelper;
    private int collectionid;

    public PuzzleActivityPresenter(PuzzleActivityView view, int id) {
        this.view = view;
        collectionid = id;

        data = startActivity.data;
        model = new PuzzleActivityModel(data.getDisplayWidth(), data.getDisplayHeight(), data.getDpi());
        dbhelper = HomeFragmentPresenter.dbhelper;

        model.setImages(data.getCollecionsWithID(id).getPuzzles());
        model.setImage(data.getCollecionsWithID(id).getSub_photo());
    }

    public int getImage() {
        return model.getImage();
    }

    public int getpuzzleState(int index){
        return model.getHabitData().getPuzzle()[index / 9][index % 9];
    }

    public void setpuzzleState(int i, int j, int k){
        model.getHabitData().setPuzzleState(i, j, k);
        dbhelper.updateHabitPuzzleState(model.getHabitData().getId(), model.getHabitData().puzzleToString());

        if (model.getHabitData().isComplete()){
            data.getCollections().add(collectionid);
            dbhelper.addCollection(collectionid);
        }
    }

    public void setHabitData(long i){
        model.setHabitData(data.getHabitDataWidtID(i));
    }

    public int getImage(int i){
        return model.getImages()[i];
    }

    public int getwidth() {
        return data.getDisplayWidth();
    }

    public int getheight(){
        return data.getDisplayHeight();
    }

    public int getbgHeight(){
        return (int)(data.getDisplayHeight() - data.getDpi() * 80);
    }

    public int getNewwidth() {
        return model.getNewwidth();
    }

    public int getpuzzleWidth(){
        return (int) (model.getNewwidth() * 2 / 5) - 3;
    }

    public int getNewheight() {
        return model.getNewheight();
    }

    public int getbgMarginTop() {
        return (int) (data.getDisplayHeight() - data.getDpi() * 80 - getNewheight()) / 2 - getpuzzleWidth() / 4;
    }

    public int getbgMarginRight() {
        return ((data.getDisplayWidth() - getNewwidth()) / 2) - getpuzzleWidth() / 4;
    }

    public interface View {

    }
}
