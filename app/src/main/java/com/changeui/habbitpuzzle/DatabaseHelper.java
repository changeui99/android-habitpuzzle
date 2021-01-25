package com.changeui.habbitpuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper {
    public class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "habitpuzzle";
        public static final String COLUMN_NAME_INITIAL = "initialhabit";
        public static final String COLUMN_NAME_NEW = "newhabit";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_DAYS = "days";
        public static final String COLUMN_NAME_STARTDAY = "startday";
        public static final String COLUMN_NAME_SUCCESSDAYS = "successdays";
        public static final String COLUMN_NAME_COLLECTIONS = "collections";
        public static final String COLUMN_NAME_PUZZLE = "puzzle";
    }

    public class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "customhabit";
        public static final String COLUMN_NAME_HABIT = "habit";
    }

    public class CollectionEntry implements BaseColumns {
        public static final String TABLE_NAME = "collection";
        public static final String COLUMN_NAME_Collection = "collectionid";
    }

    public class Database extends SQLiteOpenHelper {
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_INITIAL + " TEXT," +
                        FeedEntry.COLUMN_NAME_NEW + " TEXT," +
                        FeedEntry.COLUMN_NAME_COLOR + " INTEGER," +
                        FeedEntry.COLUMN_NAME_DAYS + " TEXT," +
                        FeedEntry.COLUMN_NAME_STARTDAY + " TEXT," +
                        FeedEntry.COLUMN_NAME_SUCCESSDAYS + " TEXT," +
                        FeedEntry.COLUMN_NAME_COLLECTIONS + " TEXT," +
                        FeedEntry.COLUMN_NAME_PUZZLE + " TEXT)";

        private static final String SQL_CREATE_ENTRIES2 =
                "CREATE TABLE IF NOT EXISTS " + HabitEntry.TABLE_NAME + " (" +
                        HabitEntry._ID + " INTEGER PRIMARY KEY," +
                        HabitEntry.COLUMN_NAME_HABIT + " TEXT)";

        private static final String SQL_CREATE_ENTRIES3 =
                "CREATE TABLE IF NOT EXISTS " + CollectionEntry.TABLE_NAME + " (" +
                        CollectionEntry._ID + " INTEGER PRIMARY KEY," +
                        CollectionEntry.COLUMN_NAME_Collection + " TEXT)";

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
        private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;
        private static final String SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS " + CollectionEntry.TABLE_NAME;

        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "HabitPuzzle.db";

        public Database(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES2);
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES2);
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES3);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_DELETE_ENTRIES2);
            db.execSQL(SQL_DELETE_ENTRIES3);
            onCreate(db);
        }
    }

    private Context context;
    private Database database;
    public boolean isfinished = false, isfinished2 = false, isfinished3 = false;

    public DatabaseHelper(Context context) {
        this.context = context;

        database = new Database(context);
    }

    public void end(){
        database.close();
    }

    public void addData(String initialHabit, String newHabit, int color, boolean[] days, String startday, String collections, String puzzle){
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_INITIAL, initialHabit);
        values.put(FeedEntry.COLUMN_NAME_NEW, newHabit);
        values.put(FeedEntry.COLUMN_NAME_COLOR, color);
        values.put(FeedEntry.COLUMN_NAME_DAYS, converToString(days));
        values.put(FeedEntry.COLUMN_NAME_STARTDAY, startday);
        values.put(FeedEntry.COLUMN_NAME_SUCCESSDAYS, "");
        values.put(FeedEntry.COLUMN_NAME_COLLECTIONS, collections);
        values.put(FeedEntry.COLUMN_NAME_PUZZLE, puzzle);

        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public void addHbit(String habit) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME_HABIT, habit);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    public void addCollection(int id) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionEntry.COLUMN_NAME_Collection, id);

        long newRowId = db.insert(CollectionEntry.TABLE_NAME, null, values);
    }

    public ArrayList<HabitData> getHabitData(){
        ArrayList<HabitData> tempDatas = new ArrayList<HabitData>();
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            long  itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry._ID));
            String initialhabit = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_INITIAL));
            String newhabit = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_NEW));
            int color = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_COLOR));
            String days = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DAYS));
            String startday = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_STARTDAY));
            String successdays = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUCCESSDAYS));
            String collections = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_COLLECTIONS));
            String puzzle = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_PUZZLE));

            HabitData tempdata = new HabitData(itemId, initialhabit, newhabit, color, convertToDays(days), startday, convertToArray(successdays));
            tempdata.setCollections(convertCollections(collections));
            tempdata.setPuzzle(convertPuzzle(puzzle));

            Log.e("test", "getHabitData: " + tempdata.puzzleToString());

            tempDatas.add(tempdata);
        }
        cursor.close();
        isfinished = true;

        return tempDatas;
    }

    public ArrayList<String> getHabitList(){
        ArrayList<String> tempDatas = new ArrayList<String>();
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            long  itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HabitEntry._ID));
            String habit = cursor.getString(cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_HABIT));

            tempDatas.add(habit);
        }

        cursor.close();
        isfinished2 = true;

        return tempDatas;
    }

    public ArrayList<Integer> getCollections() {
        ArrayList<Integer> tempDatas = new ArrayList<Integer>();
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query(
                CollectionEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            long  itemId = cursor.getLong(cursor.getColumnIndexOrThrow(CollectionEntry._ID));
            int finishedid = cursor.getInt(cursor.getColumnIndexOrThrow(CollectionEntry.COLUMN_NAME_Collection));

            tempDatas.add(finishedid);
        }

        cursor.close();
        isfinished3 = true;

        return tempDatas;
    }

    public void updateSuccessDays(long id, String s){
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_SUCCESSDAYS, s);

        db.update(FeedEntry.TABLE_NAME, values, "_id=" + id, null);
    }

    public void updateHabitCollections(long id, String collectionID){
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_COLLECTIONS, collectionID);

        db.update(FeedEntry.TABLE_NAME, values, "_id=" + id, null);
    }

    public void updateHabitPuzzleState(long id, String puzzlestate) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_PUZZLE, puzzlestate);

        db.update(FeedEntry.TABLE_NAME, values, "_id=" + id, null);
    }

    public boolean isIsfinished() {
        return (isfinished && isfinished2 && isfinished3);
    }

    public String converToString(boolean[] days){
        String temp = "";

        for (int i = 0; i < days.length; i++){
            if (days[i]){
                temp += "0";
            } else {
                temp += "1";
            }

            if (i != days.length - 1){
                temp += "9";
            }
        }

        return temp;
    }

    public boolean[] convertToDays(String s){
        String[] tmep = s.split("9");
        boolean array[] = new boolean[7];

        for (int i = 0; i < tmep.length; i++){
            Log.e("array", tmep[i]);

            if (tmep[i].equals("0")){
                array[i] = true;
            } else {
                array[i] = false;
            }
        }

        return array;
    }

    public ArrayList<String> convertToArray(String s){
        ArrayList<String> temp = new ArrayList<>();

        if (s.equals("")){
            return temp;
        }

        temp.addAll(Arrays.asList(s.split(",")));
        return temp;
    }

    public ArrayList<Integer> convertCollections(String s) {
        ArrayList<Integer> collections = new ArrayList<>();
        if (!s.equals("")){
            String[] temp = s.split(",");

            for (int i = 0; i < temp.length; i++){
                collections.add(Integer.parseInt(temp[i]));
            }
        }

        return collections;
    }

    public int[][] convertPuzzle(String s) {
        int temp[][] = new int[5][9];
        String[] temps = s.split(",");

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                temp[i][j] = Integer.parseInt(temps[i * 9 + j]);
            }
        }

        return temp;
    }
}
