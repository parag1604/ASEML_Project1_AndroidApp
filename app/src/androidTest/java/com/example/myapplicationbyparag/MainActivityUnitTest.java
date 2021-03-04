package com.example.myapplicationbyparag;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUnitTest {
    private String stringToBeTyped;
    private String stringToBeSpoken;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBeTyped = "testing";
        stringToBeSpoken = "hello";
    }

    @Test
    public void changeTextUsingKeyboard() {
        // Type text.
        onView(withId(R.id.input_text))
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.input_text))
                .check(matches(withText(stringToBeTyped)));
    }

    @Test
    public void changeTextUsingVoice() {
        // Speak text.
        onView(withId(R.id.voice_button)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.input_text))
                .check(matches(withText(stringToBeSpoken)));
    }

    @Test
    public void rewriteTextUsingVoice() {
        // Type text.
        onView(withId(R.id.input_text))
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());

        // Speak text.
        onView(withId(R.id.voice_button)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.input_text))
                .check(matches(withText(stringToBeSpoken)));
    }

    @Test
    public void rewriteTextUsingKeyboard() {
        // Speak text.
        onView(withId(R.id.voice_button)).perform(click());

        // Rewrite text.
        onView(withId(R.id.input_text))
                .perform(clearText(), typeText(stringToBeTyped), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.input_text))
                .check(matches(withText(stringToBeTyped)));
    }
}