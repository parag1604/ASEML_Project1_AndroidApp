package com.example.myapplicationbyparag;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AppEnd2EndTest {

    private String stringToBeTyped;
    private String emailId;
    private String phoneNo;
    private String invalidRecipient;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Rule
    public ActivityScenarioRule<SubActivity> subActivityScenarioRule =
            new ActivityScenarioRule<SubActivity>(SubActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry
                                .getInstrumentation()
                                .getTargetContext();
        assertEquals("com.example.myapplicationbyparag",
                        appContext.getPackageName());
    }

    @Before
    public void initValidStrings() {
        // Specify valid strings
        stringToBeTyped = "Hey, how you doing?";
        phoneNo = "8961089848";
        emailId = "paragdutta@iisc.ac.in";
        invalidRecipient = "abc123@xyz";
    }

    @Test
    public void checkText2Email() {
        // Type text
        onView(withId(R.id.input_text))
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());
        // Click send button
        onView(withId(R.id.send_button))
                .perform(click());
        // Type email
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(emailId), closeSoftKeyboard());
        // Click confirm button
        onView(withId(R.id.confirm_button))
                .perform(click());
    }

    @Test
    public void checkText2SMS() {
        // Type text
        onView(withId(R.id.input_text))
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());
        // Click send button
        onView(withId(R.id.send_button))
                .perform(click());
        // Type phone number
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(phoneNo), closeSoftKeyboard());
        // Click confirm button
        onView(withId(R.id.confirm_button))
                .perform(click());
    }

    @Test
    public void checkTextRetentionOnInvalidRecipentAndLoopback() {
        // Type text
        onView(withId(R.id.input_text))
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());
        // Click send button
        onView(withId(R.id.send_button))
                .perform(click());
        // Type email
        onView(withId(R.id.emailOrNumber))
                .perform(typeText(invalidRecipient), closeSoftKeyboard());
        // Click confirm button
        onView(withId(R.id.confirm_button))
                .perform(click());
        // Click back button
        onView(withId(R.id.emailOrNumber)).perform(pressBack());
        // Check text content is retained
        onView(withId(R.id.input_text))
                .check(matches(withText(stringToBeTyped)));
    }
}