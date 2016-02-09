package com.kimeeo.kandroid.sample.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.kimeeo.kandroid.R;
import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.model.IFragmentData;
import com.mikepenz.iconics.view.IconicsButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.titleButton)IconicsButton titleButton;
    NavigationDrawerFragment mNavigationDrawerFragment;
    @Bind(R.id.drawer_layout)DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout, toolbar);
        mNavigationDrawerFragment.gotoPageIndex(0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private BaseFragment activePage;
    public void onNavigationDrawerItemSelected(IFragmentData object)
    {
        BaseFragment view =BaseFragment.newInstance(object);
        if(view !=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, view).commit();
            activePage = view;
            titleButton.setEnabled(false);
            titleButton.setText(object.getName());
        }
    }
}