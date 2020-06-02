package com.chirag.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.chirag.notekeeper.DataManager.Companion.instance
import com.chirag.notekeeper.databinding.ActivityNoteBinding
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_note.*

class NoteActivity : AppCompatActivity() {
    private var mNote: NoteInfo? = null
    private var mIsNewNote = false
    private var mNotePosition = 0
    private var mIsCancelling = false
    private var mViewModel: NoteActivityViewModel? = null
    private lateinit var mBinding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        setSupportActionBar(toolbar)
        val viewModelProvider = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
        mViewModel = viewModelProvider.get(NoteActivityViewModel::class.java)
        if (mViewModel!!.mIsNewlyCreated && savedInstanceState != null) mViewModel!!.restoreState(
            savedInstanceState
        )
        mViewModel!!.mIsNewlyCreated = false
        val courses = instance!!.courses
        val adapterCourses =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, courses)
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_courses.adapter = adapterCourses
        readDisplayStateValues()
        saveOriginalNoteValues()
        if (!mIsNewNote) displayNote(
            mBinding.contentNote.spinnerCourses,
            mBinding.contentNote.textNoteTitle,
            mBinding.contentNote.textNoteText
        )
    }

    private fun saveOriginalNoteValues() {
        if (mIsNewNote) return
        mViewModel!!.mOriginalNoteCourseId = mNote!!.course!!.courseId
        mViewModel!!.mOriginalNoteTitle = mNote!!.title
        mViewModel!!.mOriginalNoteText = mNote!!.text
    }

    private fun displayNote(
        spinnerCourses: Spinner?,
        textNoteTitle: EditText?,
        textNoteText: EditText?
    ) {
        val courses: List<CourseInfo?> = instance!!.courses
        val courseIndex = courses.indexOf(mNote!!.course)
        spinnerCourses!!.setSelection(courseIndex)
        textNoteTitle!!.setText(mNote!!.title)
        textNoteText!!.setText(mNote!!.text)
    }

    private fun readDisplayStateValues() {
        val intent = intent
        mNotePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
        mIsNewNote = mNotePosition == POSITION_NOT_SET
        if (mIsNewNote) createNewNote()
        mNote = instance!!.notes[mNotePosition]
    }

    private fun createNewNote() {
        val dm = instance
        mNotePosition = dm!!.createNewNote()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item: MenuItem = menu.findItem(R.id.action_next)
        val lastNoteIndex: Int? = (instance?.notes?.size)?.minus(1)
        item.setEnabled(mNotePosition < lastNoteIndex!!)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_send_email) {
            sendEmail()
            return true
        } else if (id == R.id.action_cancel) {
            mIsCancelling = true
            finish()
        } else if (id == R.id.action_next) {
            moveNext()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun moveNext() {

        saveNote()

        ++mNotePosition
        mNote = instance?.notes?.get(mNotePosition)

        saveOriginalNoteValues()

        displayNote(
            mBinding.contentNote.spinnerCourses,
            mBinding.contentNote.textNoteTitle,
            mBinding.contentNote.textNoteText
        )

        invalidateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        if (mIsCancelling) {
            if (mIsNewNote) instance!!.removeNote(mNotePosition) else {
                storePreviousNoteValues()
            }
        } else saveNote()
    }

    private fun storePreviousNoteValues() {
        val course = instance!!.getCourse(mViewModel!!.mOriginalNoteCourseId!!)
        mNote!!.course = course
        mNote!!.title = mViewModel!!.mOriginalNoteTitle
        mNote!!.text = mViewModel!!.mOriginalNoteText
    }

    private fun saveNote() {
        mNote!!.course = mBinding.contentNote.spinnerCourses.selectedItem as CourseInfo
        mNote!!.title = mBinding.contentNote.textNoteTitle.text.toString()
        mNote!!.text = mBinding.contentNote.textNoteText.text.toString()
    }

    private fun sendEmail() {
        val course = mBinding.contentNote.spinnerCourses.selectedItem as CourseInfo
        val subject = mBinding.contentNote.textNoteTitle.text.toString()
        val text = """
            Checkout what I learned in the Pluralsight course "${course.title}"
            ${mBinding.contentNote.textNoteText.text}
            """.trimIndent()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc2822"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mViewModel!!.saveState(outState)
    }

    companion object {
        const val NOTE_POSITION = "com.chirag.notekeeper.NOTE_POSITION"
        const val POSITION_NOT_SET = -1
    }
}