package todo.javier.mera.todolist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void init() throws Exception {

        // Arrange
        String expectedTitle = activityRule.getActivity().getString(R.string.home_text);
        String expectedEmptyText = activityRule.getActivity().getString(R.string.recycler_empty_text);

        // Assert
        onView(withId(R.id.toolbar)).check(matches(new TitleMatcher(expectedTitle)));

        onView(withId(R.id.recyclerViewTodoLists)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewEmptyText)).check(matches(withText(expectedEmptyText)));
    }

    private class TitleMatcher extends BaseMatcher {

        private final String mExpectedTitle;

        public TitleMatcher(String title) {

            mExpectedTitle = title;
        }

        @Override
        public boolean matches(Object item) {

            Toolbar bar = (Toolbar)item;
            return bar.getTitle().equals(mExpectedTitle);
        }

        @Override
        public void describeTo(Description description) {

            description.appendText("Toolbar's title does not equal " + mExpectedTitle);
        }
    }
}