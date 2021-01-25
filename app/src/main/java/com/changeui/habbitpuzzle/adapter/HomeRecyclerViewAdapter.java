package com.changeui.habbitpuzzle.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.HabitData;
import com.changeui.habbitpuzzle.HabitdetailActivityView;
import com.changeui.habbitpuzzle.HomeFragmentPresenter;
import com.changeui.habbitpuzzle.R;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Integer> themes = new ArrayList<Integer>();
    private ArrayList<HabitData> datas = new ArrayList<HabitData>();

    private Context context;
    private Activity activity;
    private boolean istoday;
    private String df;
    private HomeFragmentPresenter presenter;

    public HomeRecyclerViewAdapter(ArrayList<HabitData> datas, Context context, Activity activity, boolean istoday, String df,
                                   HomeFragmentPresenter presenter) {
        this.datas = datas;
        this.context = context;
        this.activity = activity;
        this.istoday = istoday;
        this.df = df;
        this.presenter = presenter;

        themes.add(context.getResources().getColor(R.color.colorTheme1, null));
        themes.add(context.getResources().getColor(R.color.colorTheme2, null));
        themes.add(context.getResources().getColor(R.color.colorTheme3, null));
        themes.add(context.getResources().getColor(R.color.colorTheme4, null));
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_recyclerview_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final HabitData tempdata = datas.get(position);
        holder.text1.setText("After I, " + tempdata.getInitialHabit());
        holder.text2.setText("I will " + tempdata.getNewHabit());
        holder.cardColor.setCardBackgroundColor(themes.get(tempdata.getThemeColor()));
        setPuzzleUI(tempdata, holder.rewardText, holder.progressBar3);

        if (istoday){
            if (!tempdata.getSuccessdays().contains(df)){
                holder.checkImage.setImageResource(R.drawable.unchecked);
            } else {
                holder.checkImage.setImageResource(R.drawable.checked);
            }

            holder.checkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!tempdata.getSuccessdays().contains(df)){
                        showDialog(tempdata, holder);
                    }
                }
            });
        } else {
            holder.checkImage.setVisibility(View.INVISIBLE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity.getApplicationContext(), HabitdetailActivityView.class);
                intent.putExtra("id", datas.get(position).getId());
                activity.startActivity(intent);
            }
        });
    }

    private void setPuzzleUI(HabitData habitData, TextView rewardText, ProgressBar progressBar){
        if (habitData.needadd()){
            rewardText.setText("Please select your reword.");
            progressBar.setVisibility(View.GONE);
        } else {
            rewardText.setText("Puzzle in progress. (" + habitData.puzzleNumber() + "/45)");
            progressBar.setProgress(habitData.puzzleNumber());
            setProgressColor(habitData.getThemeColor(), progressBar);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView, cardColor;
        TextView text1, text2, rewardText;
        ImageView checkImage;
        ProgressBar progressBar3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.initialHabit);
            cardColor = itemView.findViewById(R.id.cardColor);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            rewardText = itemView.findViewById(R.id.rewardText);
            checkImage = itemView.findViewById(R.id.checkImage);
            progressBar3 = itemView.findViewById(R.id.progressBar3);
        }
    }

    private void setProgressColor(int color, ProgressBar progressBar) {
        if (color == 0){
            progressBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorTheme1, null), PorterDuff.Mode.SRC_IN);
        } else if (color == 1){
            progressBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorTheme2, null), PorterDuff.Mode.SRC_IN);
        } else if (color == 2){
            progressBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorTheme3, null), PorterDuff.Mode.SRC_IN);
        } else {
            progressBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorTheme4, null), PorterDuff.Mode.SRC_IN);
        }
    }

    public void showDialog(final HabitData data, final MyViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final View customLayout = activity.getLayoutInflater().inflate(R.layout.conform_layout, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();

        TextView okaybutton = customLayout.findViewById(R.id.okaybutton);
        TextView cancelbutton = customLayout.findViewById(R.id.cancelbutton);

        okaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                data.addSuccessDays(df);
                presenter.update(data.getId(), data.getSuccessdaysAsString());
                holder.checkImage.setImageResource(R.drawable.checked);

                if (!data.needadd()) {
                    data.addPuzzle(5);
                    presenter.updateHabitPuzzleState(data.getId(), data.puzzleToString());
                    setPuzzleUI(data, holder.rewardText, holder.progressBar3);
                }
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
