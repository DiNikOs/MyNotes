package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.entities.Note

interface BaseView {

    companion object {
        var TAG_MAIN_VIEW = "MainView"
        var TAG_ABOUT = "AboutView"
        var TYPE_SHARE = "text/plain"
    }

    fun onSaveSuccess(title: String, text: String)

    fun onSaveError(text: String)

    fun onAttemptSaveBlankText(text: String)

    fun shareData(title: String, text: String)

    fun openAboutActivity()

    fun showNoteFragment(note: Note, containerViewId:Int)
}