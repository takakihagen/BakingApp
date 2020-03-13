package android.example.mybakingapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import com.example.mybakingapp.Model.Step;
import com.example.mybakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.ExtendedSSLSession;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityBasicTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }


    @Test
    public void click_card(){
        Espresso.onView(withId(R.id.tv_recipe_card)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        Espresso.onView(withId(R.id.list_of_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(withId(R.id.next_button)).perform(ViewActions.click());

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
