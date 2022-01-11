package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note

interface BasePresenter {

    companion object {
        const val TAG_FIRST_PRESENT = "FirstPresent"
    }

    fun toSaveNote(note: Note)

    fun deleteNote(note: Note)

    fun saveNote(note: Note)

    fun shareDataBtn(noteTitle: String, editText: String)

    fun operateAboutBtn()

}