package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.entities.Note

interface DataView {

    fun setDate(list: MutableList<Note>)

    fun getDates(): MutableList<Note>
}