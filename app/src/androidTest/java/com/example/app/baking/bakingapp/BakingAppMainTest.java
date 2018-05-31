package com.example.app.baking.bakingapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
public class BakingAppMainTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void RecipeRecyclerView (){
        onView(ViewMatchers.withId(R.id.recyclerview_recipe)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }

    @Test
    public void checkRecipieIngredients() {
        onView(ViewMatchers.withId(R.id.recyclerview_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withText("Ingredients list :")).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview_steps)).check(matches(isDisplayed()));
    }

    @Test
    public void checkStep() {
        onView(ViewMatchers.withId(R.id.recyclerview_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.recyclerview_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.cardView_ingredients)).check(matches(isDisplayed()));

    }

}
