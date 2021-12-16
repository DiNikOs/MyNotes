package ru.dinikos.mynotes.mvp.presenters

interface BasePresenter {

    companion object {
        const val TAG_FIRST_PRESENT = "FirstPresent"
    }

    fun toSaveText(noteTitle: String, editText: String)

    fun saveText(noteTitle: String, editText: String)

    fun shareDataBtn(noteTitle: String, editText: String)

    fun operateAboutBtn()

}