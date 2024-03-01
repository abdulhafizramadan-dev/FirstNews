package com.firstnews.app

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firstnews.app.presentation.detail.DetailActivity
import com.firstnews.app.presentation.list.ListActivity
import com.firstnews.app.util.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun aLoadHomeScreen_Success() {
        Intents.init()
        onView(withId(R.id.rv_latest)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_latest)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(R.id.rv_latest)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(DetailActivity::class.java.name))
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_save)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_save)).perform(click())
        pressBack()
        Intents.release()
    }

    @Test
    fun bLoadRecommendationScreen_Success() {
        Intents.init()
        onView(withId(R.id.recommendationFragment)).perform(click())
        onView(withId(R.id.rv_recommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_news_recommendation_vertical)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(R.id.rv_news_recommendation_vertical)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        intended(hasComponent(DetailActivity::class.java.name))
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_save)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_save)).perform(click())
        pressBack()
        Intents.release()
    }

    @Test
    fun cLoadFavoriteScreen_Success() {
        Intents.init()
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(DetailActivity::class.java.name))
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_save)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_save)).perform(click())
        pressBack()
        Intents.release()
    }

    @Test
    fun cLoadSeeAllScreen_Success() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Intents.init()
        onView(withId(R.id.rv_latest)).check(matches(isDisplayed()))
        onView(withContentDescription(context.getString(R.string.desc_btn_see_all_news_horizontal))).check(matches(isDisplayed()))
        onView(withContentDescription(context.getString(R.string.desc_btn_see_all_news_horizontal))).perform(click())
        intended(hasComponent(ListActivity::class.java.name))
        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        intended(hasComponent(ListActivity::class.java.name))
        onView(withId(R.id.fab_save)).perform(click())
        pressBack()
        pressBack()
        Intents.release()
    }


}