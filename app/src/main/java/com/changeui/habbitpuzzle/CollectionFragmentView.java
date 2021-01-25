package com.changeui.habbitpuzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.changeui.habbitpuzzle.adapter.CollectionRecyclerViewAdapter;

public class CollectionFragmentView extends Fragment implements CollectionFragmentPresenter.View {

    private CollectionFragmentPresenter presenter;

    private RecyclerView recyclerView, recyclerView2;
    private CollectionRecyclerViewAdapter adapter, adapter2;

    public CollectionFragmentView() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_collection, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView2 = v.findViewById(R.id.recyclerView2);

        return v;
    }

    private void setUI(){
        presenter = new CollectionFragmentPresenter(this);
        adapter = new CollectionRecyclerViewAdapter(presenter.getCollections(), getActivity().getApplicationContext(), getActivity(),
                presenter.uiWidth(), presenter, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        adapter2 = new CollectionRecyclerViewAdapter(presenter.getProgress(), getActivity().getApplicationContext(), getActivity(),
                presenter.uiWidth(), presenter, true);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    @Override
    public void onResume() {
        super.onResume();

        setUI();
    }
}
