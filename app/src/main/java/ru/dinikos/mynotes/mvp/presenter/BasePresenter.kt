package ru.dinikos.mynotes.mvp.presenter

interface BasePresenter {

    companion object {
        var TAG = "FirstPresent"
    }

    fun toSaveText(noteTitle: String, editText: String)

    fun saveText(noteTitle: String, editText: String)

}