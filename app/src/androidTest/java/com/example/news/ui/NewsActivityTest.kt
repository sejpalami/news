package com.example.news.ui

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.news.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NewsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(NewsActivity::class.java)

    @Test
    fun newsFragmentLoadsOnNewsClick() {
        onView(withId(R.id.breakingNewsFragment)).perform(click())
    }

    @Test
    fun savedFragmentLoadsOnSavedNewsClick() {
        onView(withId(R.id.savedNewsFragment)).perform(click())
    }

    @Test
    fun filterNewsFragmentLoadsOnFilterNewsClick() {
        onView(withId(R.id.filterNewsFragment)).perform(click())
    }


}
