package com.changeui.habbitpuzzle.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.CollectionSelectActivityView;
import com.changeui.habbitpuzzle.HabitData;
import com.changeui.habbitpuzzle.HabitdetailActivityPresenter;
import com.changeui.habbitpuzzle.PuzzleActivityView;
import com.changeui.habbitpuzzle.R;

import java.util.ArrayList;

public class RewardRecyclerViewAdapter extends RecyclerView.Adapter<RewardRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private boolean needadd;
    private ArrayList<Integer> collections = new ArrayList<>();
    private int progress, color;
    long id;
    private HabitdetailActivityPresenter presenter;

    public RewardRecyclerViewAdapter(Context context, Activity activity, HabitData habitData,
                                     HabitdetailActivityPresenter presenter) {
        this.context = context;
        this.activity = activity;
        needadd = habitData.needadd();
        collections = habitData.getCollections();
        progress = habitData.puzzleNumber();
        color = habitData.getThemeColor();
        id = habitData.getId();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reward_layout, parent, false);

        return new RewardRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            int i = collections.get(position);

            if (position == collections.size() - 1){
                holder.rewardtext.setText("Puzzle in progress. (" + progress + "/45)");
                holder.progressBar2.setProgress(progress);

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context.getApplicationContext(), PuzzleActivityView.class);
                        intent.putExtra("habitid", id);
                        intent.putExtra("id", collections.get(position));
                        activity.startActivity(intent);
                    }
                });
            } else {
                holder.rewardtext.setText(presenter.getTitle(i) + " (complete)");
                holder.progressBar2.setProgress(45);
            }

            setProgressColor(color, holder.progressBar2);
        } catch (IndexOutOfBoundsException e) {
            holder.rewardtext.setText("Select your reward.");
            holder.progressBar2.setVisibility(View.INVISIBLE);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), CollectionSelectActivityView.class);
                    activity.startActivityForResult(intent, 1201);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (needadd) {
            return collections.size() + 1;
        } else {
            return collections.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView rewardtext;
        ProgressBar progressBar2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            rewardtext = itemView.findViewById(R.id.rewardtext);
            progressBar2 = itemView.findViewById(R.id.progressBar2);
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
}
