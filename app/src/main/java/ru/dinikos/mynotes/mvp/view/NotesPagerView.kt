package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.data.entities.Note

interface NotesPagerView {

    fun onSaveSuccess(title: String, text: String)

    fun onSaveError(text: String)

    fun onAttemptSaveBlankText(text: String)

    fun shareData(title: String, text: String)

    fun openAboutActivity()

    fun showNoteFragment(note: Note, containerViewId:Int)
}