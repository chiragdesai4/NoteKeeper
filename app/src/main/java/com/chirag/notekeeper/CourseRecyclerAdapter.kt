package com.chirag.notekeeper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.notekeeper.NoteActivity.Companion.NOTE_POSITION
import com.chirag.notekeeper.databinding.ItemCourseListBinding

class CourseRecyclerAdapter(
    private val mContext: Context,
    private val courses: ArrayList<CourseInfo>
) : RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val mBinding: ItemCourseListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.item_course_list,
            parent,
            false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val courseData = courses[position]

        //Note Title
        holder.mBinder.textCourse.text = courseData.title

        holder.mBinder.cardView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, NoteActivity::class.java).putExtra(NOTE_POSITION, position)
            )
        }

    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class ViewHolder(var mBinder: ItemCourseListBinding) : RecyclerView.ViewHolder(mBinder.root)
}