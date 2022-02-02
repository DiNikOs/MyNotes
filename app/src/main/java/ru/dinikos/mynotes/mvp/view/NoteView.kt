package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.data.entities.Note

interface NoteView {

    companion object {
        const val TAG_NOTE_VIEW = "NoteView"
    }

    fun onSaveSuccessNote(note: Note)

    fun onDeleteNote(note: Note)

    fun onSaveError(text: String)

    fun onAttemptSaveBlankText(text: String)

    fun shareData(title: String, text: String)
}