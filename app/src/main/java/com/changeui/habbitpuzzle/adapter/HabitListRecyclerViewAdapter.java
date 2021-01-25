package com.changeui.habbitpuzzle.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.HabitlistActivityPresenter;
import com.changeui.habbitpuzzle.R;

import java.util.ArrayList;

public class HabitListRecyclerViewAdapter extends RecyclerView.Adapter<HabitListRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<Integer> colors = new ArrayList<>();
    private int color;
    private HabitlistActivityPresenter presenter;

    public HabitListRecyclerViewAdapter(Context context, Activity activity, ArrayList<String> titles, int color, HabitlistActivityPresenter presenter) {
        this.context = context;
        this.activity = activity;
        this.titles = titles;
        this.color = color;
        this.presenter = presenter;

        colors.add(context.getResources().getColor(R.color.colorTheme1, null));
        colors.add(context.getResources().getColor(R.color.colorTheme2, null));
        colors.add(context.getResources().getColor(R.color.colorTheme3, null));
        colors.add(context.getResources().getColor(R.color.colorTheme4, null));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.habitlist_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            holder.textView.setText(titles.get(position));
            holder.titleColor.setCardBackgroundColor(colors.get(color));
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("selected", titles.get(position));
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
            });
        } catch (IndexOutOfBoundsException e){
            holder.textView.setText("add custom habit.");
            holder.addImage.setVisibility(View.VISIBLE);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (color == 3){
            return titles.size() + 1;
        } else {
            return titles.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        CardView titleColor;
        TextView textView;
        ImageView addImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            titleColor = itemView.findViewById(R.id.titleColor);
            textView = itemView.findViewById(R.id.habit_text);
            addImage = itemView.findViewById(R.id.addImage);
        }
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View customLayout = activity.getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();

        final TextView cancel = customLayout.findViewById(R.id.cancel);
        final TextView done = customLayout.findViewById(R.id.done);
        final EditText habitText = customLayout.findViewById(R.id.habitText);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = habitText.getText().toString();

                if (text.replace(" ", "").equals("")){
                    Toast.makeText(context, "please enter your habit.", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.addHabit(text);
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }
}
