package com.example.fypproject;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void signInActivityTest() {


        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.signInEmailText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("dimowo@my.ntu.ac.uk"), closeSoftKeyboard());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.signInPasswordText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Poptropica20"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.signIn_Btn), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());
    }

    @Test
    public void invalidSignInDetails(){
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.signInEmailText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("dimowoene@my.ntu.ac.uk"), closeSoftKeyboard());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.signInPasswordText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Poptropica20"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.signIn_Btn), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(withText("Please Enter Valid Details \nTry Again"))
                .inRoot(new ToastMatcher()).check(matches(isDisplayed()));

    }

    @Test
    public void emptyFields(){
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.signInEmailText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.signInPasswordText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.signIn_Btn), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(withText("Please Enter Details"))
                .inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
