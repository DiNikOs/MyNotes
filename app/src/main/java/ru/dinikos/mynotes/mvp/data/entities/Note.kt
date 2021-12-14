package ru.dinikos.mynotes.mvp.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_title") val title: String = "",
    @ColumnInfo(name = "note_text") val text: String = "",
    @ColumnInfo(name = "note_createDate") val createDate: Date = Date(),
    @ColumnInfo(name = "note_changeDate") var changeDate: Date = Date()
) : Parcelable
