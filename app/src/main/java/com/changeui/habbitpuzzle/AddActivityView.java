package com.changeui.habbitpuzzle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivityView extends AppCompatActivity implements AddActivityPresenter.View {

    private AddActivityPresenter presenter;

    private CardView initalHabit, newHabit;
    private TextView initialHabitText, newHabitText;

    private ImageView cancelButton;
    private CardView addButton, cardview;
    private TextView mon, tue, wed, thu, fri, sat, sun, rewardtext;
    private ConstraintLayout color1, color2, color3, color4;

    private ArrayList<ConstraintLayout> colors = new ArrayList<ConstraintLayout>();
    private ArrayList<Integer> color_selected_icon = new ArrayList<Integer>();
    private ArrayList<Integer> color_icon = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        presenter = new AddActivityPresenter(this);
        initalHabit = findViewById(R.id.initialHabit);
        newHabit = findViewById(R.id.newHabit);

        initialHabitText = findViewById(R.id.initialHabitText);
        newHabitText = findViewById(R.id.newHabitText);

        cancelButton = findViewById(R.id.cancelButton);
        addButton = findViewById(R.id.addButton);
        cardview = findViewById(R.id.cardview);

        mon = findViewById(R.id.monday);
        tue = findViewById(R.id.tuesday);
        wed = findViewById(R.id.wednesday);
        thu = findViewById(R.id.thursday);
        fri = findViewById(R.id.friday);
        sat = findViewById(R.id.saturday);
        sun = findViewById(R.id.sunday);
        rewardtext = findViewById(R.id.rewardtext);

        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);

        makeViewLists();

        initalHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HabitlistActivityView.class);
                startActivityForResult(intent, 1001);
            }
        });

        newHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HabitlistActivityView.class);
                startActivityForResult(intent, 1002);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.isAllChecked()){
                    if (!presenter.isAllunChecked()){
                        Toast.makeText(AddActivityView.this, "please check more than one day.", Toast.LENGTH_SHORT).show();
                    } else {
                        setResult(Activity.RESULT_OK, presenter.makeIntent());
                        finish();
                    }
                } else {
                    Toast.makeText(AddActivityView.this, "please check your initialhabit and newhabit.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(0), mon);
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(1), tue);
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(2), wed);
            }
        });

        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(3), thu);
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(4), fri);
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(5), sat);
            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaysBG(presenter.updateDays(6), sun);
            }
        });

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToCurrentSettings(0);
            }
        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToCurrentSettings(1);
            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToCurrentSettings(2);
            }
        });

        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToCurrentSettings(3);
            }
        });

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CollectionSelectActivityView.class);
                startActivityForResult(intent, 1201);
            }
        });
    }

    private void makeViewLists(){
        colors.add(color1);
        colors.add(color2);
        colors.add(color3);
        colors.add(color4);

        color_icon.add(R.drawable.theme1_bg);
        color_icon.add(R.drawable.theme2_bg);
        color_icon.add(R.drawable.theme3_bg);
        color_icon.add(R.drawable.theme4_bg);

        color_selected_icon.add(R.drawable.theme1_selected_bg);
        color_selected_icon.add(R.drawable.theme2_selected_bg);
        color_selected_icon.add(R.drawable.theme3_selected_bg);
        color_selected_icon.add(R.drawable.theme4_selected_bg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001){
                //initial habit result
                String habit = data.getStringExtra("selected");
                initialHabitText.setText(habit);
                initialHabitText.setTextColor(getResources().getColor(R.color.h2Color, null));
                presenter.updateInitial(habit);
            } else if (requestCode == 1002) {
                //new habit result
                String habit = data.getStringExtra("selected");
                newHabitText.setText(habit);
                newHabitText.setTextColor(getResources().getColor(R.color.h2Color, null));
                presenter.updateNew(habit);
            } else if (requestCode == 1201) {
                presenter.addCollections(data.getIntExtra("id", 0));
                rewardtext.setText("Reward selected.");
                cardview.setOnClickListener(null);
            }
        }
    }

    @Override
    public void updateToCurrentSettings(int color) {
        if (presenter.isColorChanged(color)){
            colors.get(color).setBackgroundResource(color_selected_icon.get(color));
            colors.get(presenter.getCurrentColor()).setBackgroundResource(color_icon.get(presenter.getCurrentColor()));

            presenter.updateCurrentColor(color);
        }
    }

    @Override
    public void updateDaysBG(boolean tf, TextView textView) {
        if (tf){
            textView.setBackgroundColor(getResources().getColor(R.color.colorsubTheme4, null));
        } else {
            textView.setBackgroundColor(0xffffff);
        }
    }
}