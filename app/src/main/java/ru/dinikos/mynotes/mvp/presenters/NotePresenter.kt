package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note

interface NotePresenter {

    fun toSaveNote(note: Note)

    fun deleteNote(note: Note)

    fun saveNote(note: Note)

    fun shareDataBtn(noteTitle: String, editText: String)
}