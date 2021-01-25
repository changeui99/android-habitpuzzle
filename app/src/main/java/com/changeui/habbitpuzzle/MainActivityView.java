package com.changeui.habbitpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivityView extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter presenter;
    private ConstraintLayout navlayout;
    private CardView home_button_layout, collection_button_layout, profile_button_layout;
    private ImageView home_icon, collection_icon, profile_icon;
    private TextView home_text, collection_text, profile_text;

    private ArrayList<CardView> navLayouts = new ArrayList<CardView>();
    private ArrayList<ImageView> naviconLayouts = new ArrayList<ImageView>();
    private ArrayList<TextView> navtextLayouts = new ArrayList<TextView>();

    private ArrayList<Integer> onIcons = new ArrayList<Integer>();
    private ArrayList<Integer> offIcons = new ArrayList<Integer>();

    //fragment
    private HomeFragmentView homeFragment;
    private CollectionFragmentView collectionFragment;
    private ProfileFragmentView profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);

        //define views
        navlayout = (ConstraintLayout) findViewById(R.id.navLayout);

        home_button_layout = (CardView) findViewById(R.id.home_button_layout);
        collection_button_layout = (CardView) findViewById(R.id.collection_button_layout);
        profile_button_layout = (CardView) findViewById(R.id.profile_button_layout);

        home_icon = (ImageView) findViewById(R.id.home_icon);
        collection_icon = (ImageView) findViewById(R.id.collection_icon);
        profile_icon = (ImageView) findViewById(R.id.profile_icon);

        home_text = (TextView) findViewById(R.id.home_text);
        collection_text = (TextView) findViewById(R.id.collection_text);
        profile_text = (TextView) findViewById(R.id.profile_text);

        homeFragment = new HomeFragmentView(presenter.getDbhelper());
        collectionFragment = new CollectionFragmentView();
        profileFragment = new ProfileFragmentView();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, homeFragment).commit();

        makeViewLists();
        setInitialViews();

        home_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navClicked(0);
            }
        });

        collection_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navClicked(1);
            }
        });

        profile_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navClicked(2);
            }
        });
    }

    private void makeViewLists(){
        navLayouts.add(home_button_layout);
        navLayouts.add(collection_button_layout);
        navLayouts.add(profile_button_layout);

        naviconLayouts.add(home_icon);
        naviconLayouts.add(collection_icon);
        naviconLayouts.add(profile_icon);

        navtextLayouts.add(home_text);
        navtextLayouts.add(collection_text);
        navtextLayouts.add(profile_text);

        onIcons.add(R.drawable.home_icon2);
        onIcons.add(R.drawable.collection_icon2);
        onIcons.add(R.drawable.profile_icon2);

        offIcons.add(R.drawable.home_icon1);
        offIcons.add(R.drawable.collection_icon1);
        offIcons.add(R.drawable.profile_icon1);
    }

    @Override
    public void setInitialViews() {
        home_button_layout.getLayoutParams().height = (int) presenter.navFalseWH();
        collection_button_layout.getLayoutParams().height = (int) presenter.navFalseWH();
        profile_button_layout.getLayoutParams().height = (int) presenter.navFalseWH();

        navLayouts.get(0).setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nav_background));
        naviconLayouts.get(0).setImageResource(onIcons.get(0));
        navtextLayouts.get(0).setVisibility(View.VISIBLE);
    }

    @Override
    public void navClicked(int i) {
        if (!presenter.isCurrent(i)){
            navLayouts.get(i).setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.nav_background));
            navLayouts.get(presenter.getCurrent()).setCardBackgroundColor(0xffffff);

            naviconLayouts.get(i).setImageResource(onIcons.get(i));
            naviconLayouts.get(presenter.getCurrent()).setImageResource(offIcons.get(presenter.getCurrent()));

            navtextLayouts.get(i).setVisibility(View.VISIBLE);
            navtextLayouts.get(presenter.getCurrent()).setVisibility(View.GONE);

            presenter.setCurrent(i);

            if (i == 0){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, homeFragment).commit();
            } else if (i == 1){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, collectionFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, profileFragment).commit();
            }
        }
    }

    @Override
    protected void onDestroy() {
        presenter.end();
        super.onDestroy();
    }
}