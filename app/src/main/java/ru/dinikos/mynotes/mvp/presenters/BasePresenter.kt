package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note

interface BasePresenter {

    companion object {
        var TAG_FIRST_PRESENT = "FirstPresent"
    }

    fun toSaveText(noteTitle: String, editText: String)

    fun saveText(noteTitle: String, editText: String)

    fun shareDataBtn(noteTitle: String, editText: String)

    fun operateAboutBtn()

    fun showFragment(note: Note, containerViewId:Int)

    fun openPagerViewBtn()

}