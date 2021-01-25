package com.changeui.habbitpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.changeui.habbitpuzzle.adapter.CollectionRecyclerViewAdapter;
import com.changeui.habbitpuzzle.adapter.CollectionRecyclerViewAdapter2;
import com.changeui.habbitpuzzle.adapter.RewardRecyclerViewAdapter;

public class CollectionSelectActivityView extends AppCompatActivity implements CollectionFragmentPresenter.View {

    RecyclerView recyclerView;
    ImageView cancelButton;
    CollectionRecyclerViewAdapter2 adapter;
    CollectionSelectActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_select);

        recyclerView = findViewById(R.id.recyclerView);
        cancelButton = findViewById(R.id.cancelButton);

        presenter = new CollectionSelectActivityPresenter(this);

        setUI();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUI(){
        adapter = new CollectionRecyclerViewAdapter2(presenter.getCollections(), this, this, presenter.getWidth(), presenter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}