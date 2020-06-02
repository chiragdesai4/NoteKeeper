package com.chirag.notekeeper

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class NoteListActivityTest {
    @Rule
    var mActivityTestRule = ActivityTestRule(
        NoteListActivity::class.java
    )

    @Test
    fun noteListActivityTest() {
        val floatingActionButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        floatingActionButton.perform(ViewActions.click())
        val appCompatEditText = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.text_note_title),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        1
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatEditText.perform(ViewActions.click())
        val appCompatEditText2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.text_note_title),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        1
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatEditText2.perform(
            ViewActions.replaceText("Title of the day"),
            ViewActions.closeSoftKeyboard()
        )
        val appCompatEditText3 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.text_note_text),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        1
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatEditText3.perform(
            ViewActions.replaceText("Text note for the testing purpose "),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.pressBack()
    }

    companion object {
        private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return (parent is ViewGroup && parentMatcher.matches(parent)
                            && view == parent.getChildAt(position))
                }
            }
        }
    }
}