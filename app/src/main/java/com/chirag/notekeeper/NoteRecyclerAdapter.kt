package com.chirag.notekeeper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.notekeeper.NoteActivity.Companion.NOTE_POSITION
import com.chirag.notekeeper.databinding.ItemNoteListBinding
import java.util.*

class NoteRecyclerAdapter(
    private val mContext: Context,
    private val notes: ArrayList<NoteInfo>
) : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val mBinding: ItemNoteListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.item_note_list,
            parent,
            false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val notesData = notes[position]

        //Note Title
        holder.mBinder.textCourse.text = notesData.course?.title

        //Note Description
        holder.mBinder.textTitle.text = notesData.title

        holder.mBinder.cardView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, NoteActivity::class.java).putExtra(NOTE_POSITION, position)
            )
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(var mBinder: ItemNoteListBinding) : RecyclerView.ViewHolder(mBinder.root)
}