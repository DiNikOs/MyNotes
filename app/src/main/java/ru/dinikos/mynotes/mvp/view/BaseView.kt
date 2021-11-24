package ru.dinikos.mynotes.mvp.view

interface BaseView {

    fun init()

    fun destroy()

    fun onSaveSuccess(title: String, text: String)

    fun onSaveError(text: String)

    fun onAttemptSaveBlankText(text: String)
}