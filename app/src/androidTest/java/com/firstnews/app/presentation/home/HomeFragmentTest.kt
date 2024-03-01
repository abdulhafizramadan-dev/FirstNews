package com.firstnews.app.presentation.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.firstnews.app.R
import com.firstnews.app.util.ApiConfigUtil
import com.firstnews.app.util.EspressoIdlingResource
import com.firstnews.app.util.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private val mockServer = MockWebServer()

    @Before
    fun setUp() {
        mockServer.start(8080)
        ApiConfigUtil.BASE_URL = "http://127.0.0.1:8080"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun aGetHomeFragmentSuccess() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("news_success_response.json"))
        mockServer.enqueue(mockResponse)

        launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_FirstNews)

        onView(withId(R.id.rv_latest)).check(matches(isDisplayed()))
        onView(withText("Prince Harry loses")).check(matches(isDisplayed()))
    }

    @Test
    fun bGetHomeFragmentError() {
        val mockResponse = MockResponse().setResponseCode(500)
        mockServer.enqueue(mockResponse)

        launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_FirstNews)

        onView(withId(R.id.cv_carousel)).check(matches(isDisplayed()))
        onView(withText("Prince Harry loses")).check(matches(isDisplayed()))
    }

}