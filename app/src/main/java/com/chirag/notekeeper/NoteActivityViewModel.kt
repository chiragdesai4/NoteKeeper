package com.chirag.notekeeper

import android.os.Bundle
import androidx.lifecycle.ViewModel

class NoteActivityViewModel : ViewModel() {
    @JvmField
    var mOriginalNoteCourseId: String? = null

    @JvmField
    var mOriginalNoteTitle: String? = null

    @JvmField
    var mOriginalNoteText: String? = null

    @JvmField
    var mIsNewlyCreated = true
    fun saveState(outState: Bundle) {
        outState.putString(
            ORIGINAL_NOTE_COURSE_ID,
            mOriginalNoteCourseId
        )
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle)
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText)
    }

    fun restoreState(inState: Bundle) {
        mOriginalNoteCourseId = inState.getString(ORIGINAL_NOTE_COURSE_ID)
        mOriginalNoteTitle = inState.getString(ORIGINAL_NOTE_TITLE)
        mOriginalNoteText = inState.getString(ORIGINAL_NOTE_TEXT)
    }

    companion object {
        const val ORIGINAL_NOTE_COURSE_ID = "com.chirag.notekeeper.ORIGINAL_NOTE_COURSE_ID"
        const val ORIGINAL_NOTE_TITLE = "com.chirag.notekeeper.ORIGINAL_NOTE_TITLE"
        const val ORIGINAL_NOTE_TEXT = "com.chirag.notekeeper.ORIGINAL_NOTE_TEXT"
    }
}