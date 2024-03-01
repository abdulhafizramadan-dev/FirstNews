package com.firstnews.app.presentation.favorite

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firstnews.app.R
import com.firstnews.app.presentation.home.HomeFragment
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class FavoriteFragmentTest {

    @Test
    fun aGetFavoriteFragmentSuccess() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        launchFragmentInContainer<FavoriteFragment>(themeResId = R.style.Theme_FirstNews)
        onView(withText(context.getString(R.string.label_favorite_fragment))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}