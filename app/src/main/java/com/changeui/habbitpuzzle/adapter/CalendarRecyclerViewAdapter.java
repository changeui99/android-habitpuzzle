package com.changeui.habbitpuzzle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private int width, fordf;
    private float dip;
    private int startday, endday;
    private String[] days = new String[]{"S", "M", "T", "W", "T", "F", "S"};
    private ArrayList<String> successdays = new ArrayList<>();
    private int color, colordrawable;

    public CalendarRecyclerViewAdapter(Context context, int width, float dip, int startday, int endday, ArrayList<String> successdays, int fordf, int color) {
        this.context = context;
        this.width = width;
        this.dip = dip;
        this.startday = startday;
        this.endday = endday;
        this.successdays = successdays;
        this.fordf = fordf;
        this.color = color;

        if (color == 0){
            colordrawable = context.getResources().getColor(R.color.colorsubTheme1, null);
        } else if (color == 1){
            colordrawable = context.getResources().getColor(R.color.colorsubTheme2, null);
        } else if (color == 2){
            colordrawable = context.getResources().getColor(R.color.colorsubTheme3, null);
        } else {
            colordrawable = context.getResources().getColor(R.color.colorsubTheme4, null);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendarrecylerview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int width = (int) ((this.width - 64 * this.dip) / 7);
        int start = getDayofWeek(startday);
        ArrayList<TextView> dayUIs = new ArrayList<>();

        int y = fordf / 12;
        int m = fordf % 12;

        dayUIs.add(holder.day1);
        dayUIs.add(holder.day2);
        dayUIs.add(holder.day3);
        dayUIs.add(holder.day4);
        dayUIs.add(holder.day5);
        dayUIs.add(holder.day6);
        dayUIs.add(holder.day7);

        if (position == 0){
            for (int i = 0; i < dayUIs.size(); i++){
                dayUIs.get(i).setText(days[i]);
                dayUIs.get(i).setTextColor(Color.GRAY);
            }
        } else if (position == 1){
            for (int i = start; i < 7; i++){
                dayUIs.get(i).setText(Integer.toString(i - start + 1));
                if (successdays.contains(getdateformat(y, m + 1, i - start + 1))) {
                    dayUIs.get(i).setBackgroundColor(colordrawable);
                }
            }
        } else {
            for (int i = 0; i < 7; i++){
                int tempday = 1 - start + i + (position - 1) * 7;
                if (tempday <= endday){
                    dayUIs.get(i).setText(Integer.toString(tempday));
                    if (successdays.contains(getdateformat(y, m + 1, tempday))) {
                        dayUIs.get(i).setBackgroundColor(colordrawable);
                    }
                }
            }
        }

        for (int i = 0; i < dayUIs.size(); i++){
            dayUIs.get(i).getLayoutParams().height = width;
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView day1, day2, day3, day4, day5, day6, day7;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            day1 = itemView.findViewById(R.id.day1);
            day2 = itemView.findViewById(R.id.day2);
            day3 = itemView.findViewById(R.id.day3);
            day4 = itemView.findViewById(R.id.day4);
            day5 = itemView.findViewById(R.id.day5);
            day6 = itemView.findViewById(R.id.day6);
            day7 = itemView.findViewById(R.id.day7);
        }
    }

    public int getDayofWeek(int i){
        switch (i){
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 1:
                return 0;
        }

        return 0;
    }

    public String getdateformat(int y, int m, int d){
        String temp = "";

        temp += Integer.toString(2010 + y) + ".";

        if (m < 10){
            temp += "0";
        }

        temp += Integer.toString(m) + ".";

        if (d < 10){
            temp += "0";
        }

        temp += Integer.toString(d);

        return temp;
    }
}
