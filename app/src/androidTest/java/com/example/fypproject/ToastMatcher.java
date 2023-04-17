package com.example.fypproject;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.Description;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    /**
     * Author: http://www.qaautomated.com/2016/01/how-to-test-toast-message-using-espresso.html
     */

    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            if (windowToken == appToken) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(org.hamcrest.Description description) {
        description.appendText("is toast");
    }
}