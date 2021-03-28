package com.example.myapplicationbyparag;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SubActivityUnitTest {
    private String validPhNo;
    private String validEmail;
    private String invalidPhNo;
    private String invalidEmail;
    private String invalidString;
    private String invalidString2;

    @Rule
    public ActivityScenarioRule<SubActivity> activityRule
            = new ActivityScenarioRule<>(SubActivity.class);

    @Before
    public void initValidAndInvalidStrings() {
        validPhNo = "1234567890";
        validEmail = "abcd@12.34";
        invalidPhNo = "123456789";
        invalidEmail = "abcd@1234";
        invalidString = "abcd1234";
        invalidString2 = "hello, world!";
    }

    public static Activity getCurrentActivity() {
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check((view, noViewFoundException) -> {
            View checkedView = view;
            while (checkedView instanceof ViewGroup && ((ViewGroup) checkedView).getChildCount() > 0) {
                checkedView = ((ViewGroup) checkedView).getChildAt(0);
                if (checkedView.getContext() instanceof Activity) {
                    activity[0] = (Activity) checkedView.getContext();
                    return;
                }
            }
        });
        return activity[0];
    }

    @Test
    public void checkToastMessage_givenValidBlankInput() {
        // Type nothing and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct error is displayed
        onView(withId(R.id.emailOrNumber))
                .check(matches(hasErrorText("Cannot be blank!")));
    }

    @Test
    public void checkToastMessage_givenValidPhoneNumber() {
        // Type a string and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(validPhNo), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct toast is displayed
        onView(withText("Sending message ..."))
                .inRoot(withDecorView(not(getCurrentActivity()
                        .getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkToastMessage_givenInvalidPhoneNumber() {
        // Type a string and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(invalidPhNo), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct toast is displayed
        onView(withId(R.id.emailOrNumber))
                .check(matches(hasErrorText("Invalid input!!!")));
    }

    @Test
    public void checkToastMessage_givenValidEmailId() {
        // Type a string and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(validEmail), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct toast is displayed
        onView(withText("Sending email ..."))
                .inRoot(withDecorView(not(getCurrentActivity()
                        .getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkToastMessage_givenInvalidEmailId() {
        // Type a string and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(invalidEmail), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct toast is displayed
        onView(withId(R.id.emailOrNumber))
                .check(matches(hasErrorText("Invalid input!!!")));
    }

    @Test
    public void checkToastMessage_givenSimpleAlphanumericInvalidString() {
        // Type a string and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(invalidString), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct toast is displayed
        onView(withId(R.id.emailOrNumber))
                .check(matches(hasErrorText("Invalid input!!!")));
    }

    @Test
    public void checkToastMessage_givenInvalidStringWithSpecialCharacters() {
        // Type a string and click the confirm button.
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(invalidString2), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());

        // check if correct toast is displayed
        onView(withId(R.id.emailOrNumber))
                .check(matches(hasErrorText("Invalid input!!!")));
    }
}