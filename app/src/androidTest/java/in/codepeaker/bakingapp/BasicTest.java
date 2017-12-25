package in.codepeaker.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import in.codepeaker.bakingapp.activities.VideoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by github.com/codepeaker on 25/12/17.
 */
@RunWith(AndroidJUnit4.class)
public class BasicTest {
    @Rule public ActivityTestRule<VideoActivity> detailActivityActivityTestRule
            = new ActivityTestRule<VideoActivity>(VideoActivity.class);

    @Test
    public void clickNextButtonToCheckStepsDescription(){
        //find the view
        //Perform action on the view
        onView((withId(R.id.fab_next)));

        //Check if the view does what you expected
    }
}
