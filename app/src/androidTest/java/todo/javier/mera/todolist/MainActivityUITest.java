package todo.javier.mera.todolist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import todo.javier.mera.todolist.model.TodoList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

//    @Rule
//    public ActivityTestRule<MainActivity> activityRule =
//            new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void init() throws Exception {
//
//        // Arrange
//        String expectedTitle = activityRule.getActivity().getString(R.string.home_text);
//        String expectedEmptyText = activityRule.getActivity().getString(R.string.recycler_empty_text);
//
//        // Assert
//        onView(withId(R.id.toolbar)).check(matches(new TitleMatcher(expectedTitle)));
//        onView(withId(R.id.todoListsRecyclerView)).check(matches(isDisplayed()));
//        onView(withId(R.id.recyclerViewEmptyText)).check(matches(withText(expectedEmptyText)));
//    }
//
//    @Test
//    public void fabButtonClickDisplaysDialogAddTodoList() throws Exception {
//
//        // Arrange
//        int expectedView = R.id.todoListNameEditText;
//
//        // Act
//        onView(withId(R.id.fab)).perform(click());
//
//        // Assert
//        onView(withId(expectedView)).check(matches(isDisplayed()));
//    }
//
//
//    @Test
//    public void addInvalidTodolistDisplaysError() throws Exception {
//
//        // Arrange
//        String expectedErrorMessage = activityRule.getActivity().getString(R.string.dialog_todo_list_hint_error);
//        // Act
//        onView(withId(R.id.fab)).perform(click());
//        onView(withId(R.id.todoListAddButton)).perform(click());
//
//        // Assert
//        onView(withId(R.id.todoListNameEditText)).check(matches(withHint(expectedErrorMessage)));
//    }
//
//    @Test
//    public void addValidTodolistIsDisplayedInHomeScreen() throws Exception {
//
//        // Arrange
//        String todoListName = "Awesome list";
//
//        // Act
//        onView(withId(R.id.fab)).perform(click());
//        onView(withId(R.id.todoListNameEditText)).perform(typeText(todoListName));
//        onView(withId(R.id.todoListAddButton)).perform(click());
//
//        // Assert
//        onView(withId(R.id.todoListsRecyclerView)).check(matches(new RecyclerViewMatcher(todoListName)));
//        onView(withId(R.id.recyclerViewEmptyText)).check(matches(not(isDisplayed())));
//    }
//
//    private class TitleMatcher extends BaseMatcher {
//
//        private final String mExpectedTitle;
//
//        public TitleMatcher(String title) {
//
//            mExpectedTitle = title;
//        }
//
//        @Override
//        public boolean matches(Object item) {
//
//            Toolbar bar = (Toolbar)item;
//            return bar.getTitle().equals(mExpectedTitle);
//        }
//
//        @Override
//        public void describeTo(Description description) {
//
//            description.appendText("Toolbar's title does not equal " + mExpectedTitle);
//        }
//    }
//
//    private class RecyclerViewMatcher extends BaseMatcher {
//
//        private String mItemName;
//
//        public RecyclerViewMatcher(String name) {
//
//            mItemName = name;
//        }
//
//        @Override
//        public boolean matches(Object item) {
//
//            RecyclerView view = (RecyclerView)item;
//            TodoListAdapter adapter = (TodoListAdapter) view.getAdapter();
//            TodoList todoList = adapter.getItem(0);
//
//            return todoList.getTitle().equals(mItemName);
//        }
//
//        @Override
//        public void describeTo(Description description) {
//
//            description.appendText("There are no items matching the title " + mItemName);
//        }
//    }
}