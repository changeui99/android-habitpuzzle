package com.changeui.habbitpuzzle.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.CollectionData;
import com.changeui.habbitpuzzle.CollectionFragmentPresenter;
import com.changeui.habbitpuzzle.CollectionSelectActivityPresenter;
import com.changeui.habbitpuzzle.PuzzleActivityView;
import com.changeui.habbitpuzzle.R;

import java.util.ArrayList;

public class CollectionRecyclerViewAdapter extends RecyclerView.Adapter<CollectionRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<CollectionData> collections = new ArrayList<>();
    private Context context;
    private Activity activity;
    private int width;
    private int length;
    private CollectionFragmentPresenter presenter;
    private boolean isProgress;

    public CollectionRecyclerViewAdapter(ArrayList<CollectionData> collections, Context context, Activity activity, int width,
                                         CollectionFragmentPresenter presenter, boolean isProgress) {
        this.collections = collections;
        this.context = context;
        this.activity = activity;
        this.width = width;
        this.presenter = presenter;
        this.isProgress = isProgress;

        length = collections.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.collection_recyclerview_layout, parent, false);

        return new CollectionRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.collection1001.getLayoutParams().width = width;
        holder.collection1001.getLayoutParams().height = (int) (width * 1.3);

        holder.collection2001.getLayoutParams().width = width;
        holder.collection2001.getLayoutParams().height = (int) (width * 1.3);

        holder.text1001.getLayoutParams().width = (int) (width * 0.7);
        holder.text2001.getLayoutParams().width = (int) (width * 0.7);

        if (position * 2 < length) {
            final CollectionData data = collections.get(position * 2);
            if (presenter.isFinished(data.getId())){
                holder.text1001.setText(data.getTitle());
                holder.image1001.setImageResource(data.getSub_photo());

                holder.collection1001.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog2(data.getMain_photo(), data.getTitle(), data.getSub_title());
                    }
                });
            } else {
                holder.text1001.setText("???");
                holder.image1001.setImageResource(R.drawable.habitpuzzle_unknown);

                holder.collection1001.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isProgress){
                            Intent intent = new Intent(context.getApplicationContext(), PuzzleActivityView.class);
                            intent.putExtra("habitid", presenter.getHabitId(data.getId()));
                            intent.putExtra("id", data.getId());
                            activity.startActivity(intent);
                        } else {
                            showDialog(R.drawable.habitpuzzle_unknown, "???", data.getSub_title());
                        }
                    }
                });
            }
        }

        if (position * 2 + 1 < length) {
            final CollectionData data = collections.get(position * 2 + 1);
            if (presenter.isFinished(data.getId())){
                holder.text2001.setText(data.getTitle());
                holder.image2001.setImageResource(data.getSub_photo());

                holder.collection2001.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog2(data.getMain_photo(), data.getTitle(), data.getSub_title());
                    }
                });
            } else {
                holder.text2001.setText("???");
                holder.image2001.setImageResource(R.drawable.habitpuzzle_unknown);

                holder.collection2001.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isProgress){
                            Intent intent = new Intent(context.getApplicationContext(), PuzzleActivityView.class);
                            intent.putExtra("habitid", presenter.getHabitId(data.getId()));
                            intent.putExtra("id", data.getId());
                            activity.startActivity(intent);
                        } else {
                            showDialog(R.drawable.habitpuzzle_unknown, "???", data.getSub_title());
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (collections.size() % 2 == 0){
            return collections.size() / 2;
        } else {
            return (collections.size() / 2) + 1;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView collection1001, collection2001;
        public ImageView image1001, image2001;
        public TextView text1001, text2001;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            collection1001 = itemView.findViewById(R.id.collection1001);
            collection2001 = itemView.findViewById(R.id.collection2001);

            image1001 = itemView.findViewById(R.id.image1001);
            image2001 = itemView.findViewById(R.id.image2001);

            text1001 = itemView.findViewById(R.id.text1001);
            text2001 = itemView.findViewById(R.id.text2001);
        }
    }

    public void showDialog(int image, String title, String subtitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final View customLayout = activity.getLayoutInflater().inflate(R.layout.collection_notcomplete_detail, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();

        ImageView collectionImage = customLayout.findViewById(R.id.collectionImage);
        TextView titletext = customLayout.findViewById(R.id.title);
        TextView subtitletext = customLayout.findViewById(R.id.subtitle);

        collectionImage.getLayoutParams().width = width;
        collectionImage.getLayoutParams().height = (int) (width * 1.3);

        collectionImage.setImageResource(image);

        titletext.setText(title);
        subtitletext.setText(subtitle);

        dialog.show();
    }

    public void showDialog2(int image, String title, String subtitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final View customLayout = activity.getLayoutInflater().inflate(R.layout.collection_complete_detail, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();

        ImageView collectionImage = customLayout.findViewById(R.id.collectionImage);
        TextView collectionText = customLayout.findViewById(R.id.collectionText);

        collectionImage.getLayoutParams().width = width * 2;
        collectionImage.getLayoutParams().height = (int) (width * 1.3) * 2;

        collectionText.setText(title);

        collectionImage.setImageResource(image);

        dialog.show();
    }
}
