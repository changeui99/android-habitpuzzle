package com.changeui.habbitpuzzle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.changeui.habbitpuzzle.adapter.CalendarAdapter;
import com.changeui.habbitpuzzle.adapter.RewardRecyclerViewAdapter;

import java.util.ArrayList;

public class HabitdetailActivityView extends AppCompatActivity implements HabitdetailActivityPresenter.View {

    private TextView initialHabitText, newHabitText, progressText;
    private ImageView cancelButton, calendar_back, calendar_forward;
    private Intent intent;
    private ArrayList<TextView> daysUI = new ArrayList<>();
    private ViewPager2 viewpager;
    private CalendarAdapter adapter;
    private ProgressBar progressBar;
    private HabitdetailActivityPresenter presenter;
    private ConstraintLayout calendarcont;
    private RecyclerView rewardrecycler;
    private RewardRecyclerViewAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitdetail);

        intent = getIntent();
        presenter = new HabitdetailActivityPresenter(this);

        initialHabitText = findViewById(R.id.initialHabitText);
        newHabitText = findViewById(R.id.newHabitText);
        progressText = findViewById(R.id.progressText);

        calendar_back = findViewById(R.id.calendar_back);
        calendar_forward = findViewById(R.id.calendar_forward);

        cancelButton = findViewById(R.id.cancelButton);

        viewpager = findViewById(R.id.viewpager);
        progressBar = findViewById(R.id.progressBar);

        calendarcont = findViewById(R.id.calendarcont);
        rewardrecycler = findViewById(R.id.rewardrecycler);

        setArray();

        calendar_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.getCurrentPage() < 239){
                    presenter.addCurrent();
                    viewpager.setCurrentItem(presenter.getCurrentPage());
                }
            }
        });

        calendar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.getCurrentPage() > 0){
                    presenter.subCurrent();
                    viewpager.setCurrentItem(presenter.getCurrentPage());
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setArray(){
        daysUI.add((TextView) findViewById(R.id.monday));
        daysUI.add((TextView) findViewById(R.id.tuesday));
        daysUI.add((TextView) findViewById(R.id.wednesday));
        daysUI.add((TextView) findViewById(R.id.thursday));
        daysUI.add((TextView) findViewById(R.id.friday));
        daysUI.add((TextView) findViewById(R.id.saturday));
        daysUI.add((TextView) findViewById(R.id.sunday));
    }

    private void setUI(){
        long habitID = intent.getLongExtra("id", 0);
        presenter.setHabitData(habitID);

        boolean temp[] = presenter.getHabitData().getDays();
        int color = presenter.getHabitData().getThemeColor();
        ArrayList<String> successdays = presenter.getHabitData().getSuccessdays();
        int max = presenter.getHabitData().getDaynumber();
        int progress = successdays.size();
        boolean needadd = presenter.getHabitData().needadd();
        ArrayList<Integer> collections = presenter.getHabitData().getCollections();

        adapter = new CalendarAdapter(getApplicationContext(), presenter.getWidth(), presenter.getDip(), successdays, color);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(1);
        presenter.setCurrentPage(presenter.getHabitData().getCurrentDay());
        viewpager.setCurrentItem(presenter.getCurrentPage());

        progressBar.setMax(max);
        progressBar.setProgress(progress);
        progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        setProgressColor(color);

        progressText.setText("You have succeeded " + progress + " out of " + max + " days.");

        initialHabitText.setText(presenter.getHabitData().getInitialHabit());
        newHabitText.setText(presenter.getHabitData().getNewHabit());

        calendarcont.getLayoutParams().height = (int) (presenter.getWidth() + presenter.getDip() * 40);

        adapter2 = new RewardRecyclerViewAdapter(this, this, presenter.getHabitData(), presenter);
        rewardrecycler.setAdapter(adapter2);
        rewardrecycler.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        for (int i = 0; i < temp.length; i++){
            if (temp[i]){
                if (color == 0){
                    daysUI.get(i).setBackgroundColor(getResources().getColor(R.color.colorsubTheme1, null));
                } else if (color == 1){
                    daysUI.get(i).setBackgroundColor(getResources().getColor(R.color.colorsubTheme2, null));
                } else if (color == 2){
                    daysUI.get(i).setBackgroundColor(getResources().getColor(R.color.colorsubTheme3, null));
                } else {
                    daysUI.get(i).setBackgroundColor(getResources().getColor(R.color.colorsubTheme4, null));
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1201){
                //initial habit result
                int id = data.getIntExtra("id", 0);
                presenter.addCollections(id);
                presenter.resetPuzzle();

                adapter2 = new RewardRecyclerViewAdapter(this, this, presenter.getHabitData(), presenter);
                rewardrecycler.setAdapter(adapter2);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUI();
    }

    private void setProgressColor(int color) {
        if (color == 0){
            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorTheme1, null), PorterDuff.Mode.SRC_IN);
        } else if (color == 1){
            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorTheme2, null), PorterDuff.Mode.SRC_IN);
        } else if (color == 2){
            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorTheme3, null), PorterDuff.Mode.SRC_IN);
        } else {
            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorTheme4, null), PorterDuff.Mode.SRC_IN);
        }
    }
}