package com.changeui.habbitpuzzle;

public class PuzzleActivityModel {

    int width, height;
    float dip;
    int newwidth, newheight;
    int dbmargin = 36;
    private int[] images;
    private HabitData habitData;
    private int image;

    public PuzzleActivityModel(int width, int height, float dip) {
        this.width = width;
        this.height = height;
        this.dip = dip;

        newwidth = (int) (width - dbmargin * 2 * dip);
        newheight = (int) (height - (80 + dbmargin * 2) * dip);

        if (newwidth * 610 / 344 <= newheight){
            newheight = (int) (newwidth * 610 / 344);
        } else {
            newwidth = (int) (newheight * 344 / 610);
        }
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public HabitData getHabitData() {
        return habitData;
    }

    public void setHabitData(HabitData habitData) {
        this.habitData = habitData;
    }

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
    }

    public int getNewwidth() {
        return newwidth;
    }

    public int getNewheight() {
        return newheight;
    }
}
