package com.changeui.habbitpuzzle;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.adapter.HabitListRecyclerViewAdapter;

import java.util.ArrayList;

public class HabitlistActivityView extends AppCompatActivity implements HabitlistActivityPresenter.View {

    private HabitlistActivityPresenter presenter;

    private TextView select1, select2, select3, select4;
    private RecyclerView recyclerView;

    private ImageView cancelButton;

    private HabitListRecyclerViewAdapter everyday_adapter;
    private HabitListRecyclerViewAdapter week5_adapter;
    private HabitListRecyclerViewAdapter week2_adapter;
    private HabitListRecyclerViewAdapter custom_adapter;

    private ArrayList<TextView> selects = new ArrayList<TextView>();
    private ArrayList<HabitListRecyclerViewAdapter> adapters = new ArrayList<HabitListRecyclerViewAdapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitlist_activity);

        presenter = new HabitlistActivityPresenter(this);

        select1 = findViewById(R.id.select1);
        select2 = findViewById(R.id.select2);
        select3 = findViewById(R.id.select3);
        select4 = findViewById(R.id.select4);

        recyclerView = findViewById(R.id.recyclerView);

        cancelButton = findViewById(R.id.cancelButton);

        everyday_adapter = new HabitListRecyclerViewAdapter(this, this, presenter.everydayTitles(), 0, presenter);
        week5_adapter = new HabitListRecyclerViewAdapter(this, this, presenter.week5Titles(), 1, presenter);
        week2_adapter = new HabitListRecyclerViewAdapter(this, this, presenter.week2Titles(), 2, presenter);
        custom_adapter = new HabitListRecyclerViewAdapter(this, this, presenter.customTitle(), 3, presenter);

        recyclerView.setAdapter(everyday_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        makeViewLists();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelected(0);
            }
        });

        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelected(1);
            }
        });

        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelected(2);
            }
        });

        select4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelected(3);
            }
        });
    }

    public void makeViewLists(){
        selects.add(select1);
        selects.add(select2);
        selects.add(select3);
        selects.add(select4);

        adapters.add(everyday_adapter);
        adapters.add(week5_adapter);
        adapters.add(week2_adapter);
        adapters.add(custom_adapter);
    }

    @Override
    public void updateSelected(int i) {
        if (presenter.getCurrentSelected() != i){
            selects.get(i).setBackgroundResource(R.drawable.selectedhabit_tag_bg);
            selects.get(presenter.getCurrentSelected()).setBackgroundResource(R.drawable.selecthabit_tag_bg);

            recyclerView.setAdapter(adapters.get(i));

            presenter.updateCurrentSelected(i);
        }
    }

    @Override
    public void updateAdapter() {
        adapters.remove(custom_adapter);
        custom_adapter = new HabitListRecyclerViewAdapter(this, this, presenter.customTitle(), 3, presenter);
        adapters.add(custom_adapter);

        recyclerView.setAdapter(adapters.get(3));
    }
}