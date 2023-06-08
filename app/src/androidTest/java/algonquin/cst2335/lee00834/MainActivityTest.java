package algonquin.cst2335.lee00834;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {

        //tells the testing library to type "12345" into the editText
        //The function closeSoftKeyboard() makes the keyboard go away from the screen.
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        //find the button with id R.id.button.
        ViewInteraction materialButton = onView(withId(R.id.theButton));
        //perform clicking that button
        materialButton.perform(click());

        //finds the TextView and makes sure that it shows "You shall not pass!"
        ViewInteraction textView = onView(withId(R.id.theTextView));
       // checks that the text matches "You shall not pass!". If the text matches, then the test case passes.
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingUpperCase() {

        //finds the EditText
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"));

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.theButton));
        //click the button
        materialButton.perform(click());

        //find the TextView
        ViewInteraction textView = onView(withId(R.id.theTextView));
        //check that the text
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingLowerCase() {

        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));

        ViewInteraction materialButton = onView(withId(R.id.theButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.theTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingNumber() {

        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("PASSWORD#$*"));

        ViewInteraction materialButton = onView(withId(R.id.theButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.theTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingSpecialSymbol() {

        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("PassWord123"));

        ViewInteraction materialButton = onView(withId(R.id.theButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.theTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testPassAll() {

        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("PassWord123#$*"));

        ViewInteraction materialButton = onView(withId(R.id.theButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.theTextView));
        textView.check(matches(withText("Your password meets the requirements.")));
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
