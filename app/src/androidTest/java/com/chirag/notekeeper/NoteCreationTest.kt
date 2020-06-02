package com.chirag.notekeeper

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.chirag.notekeeper.CourseInfo
import com.chirag.notekeeper.DataManager.Companion.instance
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NoteCreationTest {
    @Rule
    var mNoteListActivityRule =
        ActivityTestRule(
            NoteListActivity::class.java
        )

    @Test
    fun createNewNote() {
        val course = sDataManager!!.getCourse("java_lang")
        val noteTitle = "Test Note Title"
        val noteText = "Test Note Text"

        /*ViewInteraction fabNewNote = onView(withId(R.id.fab));
        fabNewNote.perform(click());*/Espresso.onView(ViewMatchers.withId(R.id.fab))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_courses)).perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.instanceOf<Any?>(CourseInfo::class.java),
                Matchers.equalTo<CourseInfo?>(course)
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_courses)).check(
            ViewAssertions.matches(
                ViewMatchers.withSpinnerText(
                    Matchers.containsString(course!!.title)
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.text_note_title))
            .perform(ViewActions.typeText(noteTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString(noteTitle))))
        Espresso.onView(ViewMatchers.withId(R.id.text_note_text)).perform(
            ViewActions.typeText(noteText),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(ViewMatchers.withId(R.id.text_note_text))
            .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString(noteText))))
        Espresso.pressBack()
        val noteIndex = sDataManager!!.notes.size - 1
        val note = sDataManager!!.notes[noteIndex]
        Assert.assertEquals(course, note.course)
        Assert.assertEquals(noteTitle, note.title)
        Assert.assertEquals(noteText, note.text)
    }

    companion object {
        var sDataManager: DataManager? = null

        @BeforeClass
        fun classSetUp() {
            sDataManager = instance
        }
    }
}