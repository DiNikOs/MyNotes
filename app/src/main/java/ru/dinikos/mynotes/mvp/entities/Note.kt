package ru.dinikos.mynotes.mvp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: Long = +1,
    val title: String = "",
    val text: String = "",
    val createDate: Date = Date(),
    val changeDate: Date = Date()
) : Parcelable
