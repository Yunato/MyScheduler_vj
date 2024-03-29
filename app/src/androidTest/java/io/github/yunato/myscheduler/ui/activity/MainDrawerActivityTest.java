package io.github.yunato.myscheduler.ui.activity;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.github.yunato.myscheduler.R;
import io.github.yunato.myscheduler.model.dao.DaoFactory;
import io.github.yunato.myscheduler.model.dao.LocalDao;
import io.github.yunato.myscheduler.model.entity.EventItem;
import io.github.yunato.myscheduler.ui.fragment.CalendarFragment;
import io.github.yunato.myscheduler.ui.fragment.DayFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainDrawerActivityTest {
    @Rule
    public ActivityTestRule<MainDrawerActivity> activityRule
                                = new ActivityTestRule<>(MainDrawerActivity.class, false ,false);

    private static final Intent MY_ACTIVITY_INTENT
            = new Intent(InstrumentationRegistry.getTargetContext(), MainDrawerActivity.class);

    @Before
    public void setUp() throws Exception {
        activityRule.launchActivity(MY_ACTIVITY_INTENT);
    }

    @Test
    public void checkViewItemOnCalendar(){
        CalendarFragment fragment = CalendarFragment.newInstance(new CalendarFragment.OnSelectedDateListener() {
            @Override
            public void onSelectedDate(int year, int month, int dayOfMonth) {
            }
        });
        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, fragment).commit();

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.button_floating_action)).check(matches(isDisplayed()));
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_view)).check(matches(not(isDisplayed())));
        onView(getAt(withId(R.id.calendarView), 0)).check(matches(isDisplayed()));

        List<EventItem> items = new ArrayList<>();
        LocalDao dao = DaoFactory.getLocalDao();
        //CalendarLocalDao dao = Mockito.mock(CalendarLocalDao.class);
        //when(dao.getEventItems(anyInt(), anyInt(), anyInt())).thenReturn(items);

        //activityRule.getActivity().localDao = dao;
        onView(getAt(withId(R.id.calendarView), 1)).perform(click());
        //verify(dao).getEventItems(2019, 0, 9);
    }

    @Test
    public void checkViewItemOnDayList(){
        DayFragment fragment = DayFragment.newInstance(new DayFragment.OnSelectedEventListener() {
            @Override
            public void onSelectedEvent(EventItem item, View view) {
            }
        });
        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, fragment).commit();

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.button_floating_action)).check(matches(isDisplayed()));
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    public static Matcher<View> getAt(final Matcher<View> parentMatcher, final int index){
        return new NthMatcher<>(parentMatcher, index);
    }

    private static class NthMatcher<T> extends TypeSafeMatcher<T> {
        private Matcher<T> mParentMatcher;
        private int mNum;
        private int mCount = 0;

        NthMatcher(final Matcher<T> parentMatcher, int num){
            mParentMatcher = parentMatcher;
            mNum = num;
        }

        @Override
        protected boolean matchesSafely(T item){
            return mParentMatcher.matches(item) && (mCount++ == mNum);
        }

        @Override
        public void describeTo(Description description){
            description.appendText("with" + mNum + "-th: " + mParentMatcher);
        }
    }
}
