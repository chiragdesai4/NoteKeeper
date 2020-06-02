package com.chirag.notekeeper

import android.os.Parcel
import android.os.Parcelable

class NoteInfo : Parcelable {
    var course: CourseInfo?
    var title: String?
    var text: String?

    constructor(course: CourseInfo?, title: String?, text: String?) {
        this.course = course
        this.title = title
        this.text = text
    }

    private constructor(parcel: Parcel) {
        course = parcel.readParcelable(CourseInfo::class.java.classLoader)
        title = parcel.readString()
        text = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(course, 0)
        dest.writeString(title)
        dest.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    private val compareKey: String
        private get() = course!!.courseId + "|" + title + "|" + text

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as NoteInfo
        return compareKey == that.compareKey
    }

    override fun hashCode(): Int {
        return compareKey.hashCode()
    }

    override fun toString(): String {
        return compareKey
    }

    companion object {
        val CREATOR: Parcelable.Creator<NoteInfo?> = object : Parcelable.Creator<NoteInfo?> {
            override fun createFromParcel(parcel: Parcel): NoteInfo? {
                return NoteInfo(parcel)
            }

            override fun newArray(size: Int): Array<NoteInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}