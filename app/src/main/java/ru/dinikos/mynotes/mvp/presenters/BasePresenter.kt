package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.entities.Note

interface BasePresenter {

    companion object {
        const val TAG_FIRST_PRESENT = "FirstPresent"
    }

    fun toSaveText(noteTitle: String, editText: String)

    fun saveText(noteTitle: String, editText: String)

    fun shareDataBtn(noteTitle: String, editText: String)

    fun operateAboutBtn()

    fun showFragment(note: Note)

}