package ru.dinikos.mynotes.mvp.model

import android.graphics.ColorSpace
import android.widget.TextView
import ru.dinikos.mynotes.utils.formatDate
import java.util.*

class Note {

    var id: Long = 0

    var title: String? = null

    var text: String? = null

    var createDate: Date? = null

    var changeDate: Date? = null

    constructor(title: String, createDate: Date) {
        this.title = title
        this.createDate = createDate
        this.changeDate = createDate
    }

    constructor()

    fun getInfo(): String = "Title:\n$title\n" +
            "Created at:\n${formatDate(createDate)}\n" +
            "Changed at:\n${formatDate(changeDate)}"
}

