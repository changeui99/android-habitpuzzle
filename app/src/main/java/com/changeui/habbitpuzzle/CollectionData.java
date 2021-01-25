package com.changeui.habbitpuzzle;

import java.util.ArrayList;

public class CollectionData {
    private int id;
    private String title, sub_title;
    private int main_photo, sub_photo;
    private int[] puzzles;

    public CollectionData(int id, String title, String sub_title, int main_photo, int sub_photo, int[] puzzles) {
        this.id = id;
        this.title = title;
        this.sub_title = sub_title;
        this.main_photo = main_photo;
        this.sub_photo = sub_photo;
        this.puzzles = puzzles;
    }

    public int[] getPuzzles() {
        return puzzles;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public int getMain_photo() {
        return main_photo;
    }

    public int getSub_photo() {
        return sub_photo;
    }
}
