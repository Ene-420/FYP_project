package com.example.fypproject;

import android.app.Activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

public class HomeActivityTest {

    private Activity activity;

    @Before
    public void setUp(){
        activity = Robolectric.buildActivity(HomeActivity.class).create().get();
    }
    @Test
    public void navigateToChatsFragment() {

        BottomNavigationView navigationView = activity.findViewById(R.id.nav_bar);

        TestNavHo

    }
}