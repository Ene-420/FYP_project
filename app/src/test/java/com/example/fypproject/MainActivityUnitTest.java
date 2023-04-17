package com.example.fypproject;


import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityUnitTest {

    //private ActivityController<SignInActivity> activity;
    private Activity activity;

    @Before
    public void setUp(){

        activity = Robolectric.buildActivity(MainActivity.class).create().get();
//        activity = Robolectric.buildActivity(SignInActivity.class);
//        activity.setup();
//        SignInActivity signInAct = activity.get();
//        FirebaseApp.initializeApp(signInAct.getApplicationContext());

    }

    @Test
    public void navigationToCreateAccount(){
        Button createAct_btn = activity.findViewById(R.id.createAcctBtn);

        createAct_btn.performClick();

        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startIntent);

        assertEquals(shadowIntent.getIntentClass().getName(), CreateNewAccountActivity.class.getName());
    }

    public void navigateToSignIn(){
        Button sign_in = activity.findViewById(R.id.signInBtn);

        sign_in.performClick();

        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startIntent);

        assertEquals(shadowIntent.getIntentClass().getName(), SignInActivity.class.getName());
    }

}