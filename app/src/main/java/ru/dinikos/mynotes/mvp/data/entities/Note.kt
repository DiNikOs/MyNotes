package ru.dinikos.mynotes.mvp.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="note_id") var noteId: Long? = 0,
    @ColumnInfo(name = "note_title") var title: String = "",
    @ColumnInfo(name = "note_text") var text: String = "",
    @ColumnInfo(name = "note_createDate") var createDate: String = "",
    @ColumnInfo(name = "note_changeDate") var changeDate: String = ""
) : Parcelable
