package com.changeui.habbitpuzzle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewPagerViewHolder> {

    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> successdays = new ArrayList<>();
    private Context context;
    private int width;
    private DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
    private Calendar calendar;
    private float dip;
    private int color;

    public CalendarAdapter(Context context, int width, float dip, ArrayList<String> successdays, int color) {
        this.context = context;
        this.width = width;
        this.dip = dip;
        this.successdays = successdays;
        this.color = color;

        try {
            calendar = Calendar.getInstance();
            calendar.setTime(df.parse("2010.01.01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendar_layout, parent, false);

        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        try {
            calendar = Calendar.getInstance();
            calendar.setTime(df.parse("2010.01.01"));
            calendar.add(Calendar.MONTH, position);
            holder.date_text.setText(getMonth(position % 12) + " " + Integer.toString(2010 + position / 12));

            CalendarRecyclerViewAdapter adapter = new CalendarRecyclerViewAdapter(context, width, dip, calendar.get(Calendar.DAY_OF_WEEK), calendar.getActualMaximum(Calendar.DATE), successdays, position, color);
            holder.recyclerview.setAdapter(adapter);
            holder.recyclerview.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 240;
    }

    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {

        TextView date_text;
        RecyclerView recyclerview;

        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);

            date_text = itemView.findViewById(R.id.date_text);
            recyclerview = itemView.findViewById(R.id.recyclerview);
        }
    }

    private String getMonth(int i){
        if (i == 0) {
            return "January";
        } else if (i == 1){
            return "February";
        } else if (i == 2){
            return "March";
        } else if (i == 3){
            return "April";
        } else if (i == 4){
            return "May";
        } else if (i == 5){
            return "June";
        } else if (i == 6){
            return "July";
        } else if (i == 7){
            return "August";
        } else if (i == 8){
            return "September";
        } else if (i == 9){
            return "October";
        } else if (i == 10){
            return "November";
        } else {
            return "December";
        }
    }
}
