package com.gvoscar.apps.postsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gvoscar.apps.postsapp.features.posts.ui.FavoritePostsFragment;
import com.gvoscar.apps.postsapp.features.posts.ui.PostsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.bottomFloating)
    FloatingActionButton bottomFloating;
    @BindView(R.id.main_coordinator_layout)
    CoordinatorLayout mainCoordinatorLayout;

    private PostsFragment postsFragment = new PostsFragment();
    private FavoritePostsFragment favoritePostsFragment = new FavoritePostsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        showFragment(new PostsFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public void setToolbar() {
        // toolbar.setTitle("CarShop");

        //toolbar.setNavigationIcon(R.drawable.ic_atras);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_menu_posts) {
            showFragment(postsFragment);
        }
        if (item.getItemId() == R.id.action_menu_favorites) {
            showFragment(favoritePostsFragment);
        }
        if (item.getItemId() == R.id.action_menu_none) {

        }
        return true;
    }

    @OnClick(R.id.bottomFloating)
    public void onClickBottomFloating() {
        postsFragment.romoveAll();
        // startActivity(new Intent(this, VehicleActivity.class));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}