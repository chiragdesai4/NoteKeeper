package com.chirag.notekeeper

import com.chirag.notekeeper.DataManager.Companion.instance
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class DataManagerTest {
    @Before
    fun setUp() {
        val dm = instance
        dm!!.notes.clear()
        dm.initializeExampleNotes()
    }

    @Test
    fun createNewNote() {
        val course = sDataManager!!.getCourse("android_async")
        val noteTitle = "Test Note Title"
        val noteText = "This is the body text for my test note"
        val noteIndex = sDataManager!!.createNewNote()
        val noteInfo = sDataManager!!.notes[noteIndex]
        noteInfo.course = course
        noteInfo.title = noteTitle
        noteInfo.text = noteText
        val compareNote = sDataManager!!.notes[noteIndex]
        Assert.assertEquals(compareNote.course, course)
        Assert.assertEquals(compareNote.title, noteTitle)
        Assert.assertEquals(compareNote.text, noteText)
    }

    companion object {
        var sDataManager: DataManager? = null

        @BeforeClass
        fun classSetUp() {
            sDataManager = instance
        }
    }
}