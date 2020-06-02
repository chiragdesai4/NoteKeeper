package com.chirag.notekeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chirag.notekeeper.databinding.ActivityNoteListBinding
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNoteListBinding
    private var mNotesAdapter: NoteRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_list)
        setSupportActionBar(toolbar)
        mBinding.fab.setOnClickListener { startActivity(Intent(this, NoteActivity::class.java)) }
        initializeDisplayContent()
    }

    private fun initializeDisplayContent() {
        val notesLayoutManager = LinearLayoutManager(this)
        listNotes.layoutManager = notesLayoutManager

        val notes: ArrayList<NoteInfo>? = DataManager.instance?.notes;

        mNotesAdapter = this.let { notes?.let { it1 -> NoteRecyclerAdapter(it, it1) } }
        listNotes.adapter = mNotesAdapter
    }

    override fun onResume() {
        super.onResume()
        mNotesAdapter?.notifyDataSetChanged()
    }
}