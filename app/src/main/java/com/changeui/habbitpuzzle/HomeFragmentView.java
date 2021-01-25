package com.changeui.habbitpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.adapter.HomeRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragmentView extends Fragment implements HomeFragmentPresenter.View {

    private HomeFragmentPresenter presenter;

    private CardView addButton;
    private ImageView no_today, no_others;
    private RecyclerView recyclerView, recyclerView2;
    private DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

    private HomeRecyclerViewAdapter adapter, adapter2;

    public HomeFragmentView(DatabaseHelper dbhelper) {
        presenter = new HomeFragmentPresenter(this, dbhelper);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        addButton = v.findViewById(R.id.addButton);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView2 = v.findViewById(R.id.recyclerView2);
        no_today = v.findViewById(R.id.no_today);
        no_others = v.findViewById(R.id.no_others);

        updateDatas();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddActivityView.class);
                startActivityForResult(intent, 1234);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234){
            if (resultCode == Activity.RESULT_OK){
                String initialHabit = data.getStringExtra("initialHabit");
                String newHabit = data.getStringExtra("newHabit");
                int color = data.getIntExtra("color", 0);
                boolean[] days = data.getBooleanArrayExtra("days");
                ArrayList<Integer> collections = data.getIntegerArrayListExtra("collections");

                presenter.addData(initialHabit, newHabit, color, days, df.format(Calendar.getInstance().getTime()), collections);
                updateDatas();
            }
        }
    }

    @Override
    public void updateDatas() {
        ArrayList<HabitData> today = presenter.getTodays();
        ArrayList<HabitData> others = presenter.getOthers();

        if (today.size() == 0){
            no_today.setVisibility(View.VISIBLE);
        } else {
            no_today.setVisibility(View.GONE);
        }

        if (others.size() == 0){
            no_others.setVisibility(View.VISIBLE);
        } else {
            no_others.setVisibility(View.GONE);
        }

        adapter = new HomeRecyclerViewAdapter(today, getActivity().getApplicationContext(), getActivity(), true,
                df.format(Calendar.getInstance().getTime()), presenter);
        adapter2 = new HomeRecyclerViewAdapter(others, getActivity().getApplicationContext(), getActivity(), false,
                df.format(Calendar.getInstance().getTime()), presenter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<HabitData> today = presenter.getTodays();
        ArrayList<HabitData> others = presenter.getOthers();

        if (today.size() == 0){
            no_today.setVisibility(View.VISIBLE);
        } else {
            no_today.setVisibility(View.GONE);
        }

        if (others.size() == 0){
            no_others.setVisibility(View.VISIBLE);
        } else {
            no_others.setVisibility(View.GONE);
        }

        adapter = new HomeRecyclerViewAdapter(today, getActivity().getApplicationContext(), getActivity(), true,
                df.format(Calendar.getInstance().getTime()), presenter);
        adapter2 = new HomeRecyclerViewAdapter(others, getActivity().getApplicationContext(), getActivity(), false,
                df.format(Calendar.getInstance().getTime()), presenter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
