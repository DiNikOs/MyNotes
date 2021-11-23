package ru.dinikos.mynotes.mvp.presenter

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

interface BasePresenter {

    companion object {
        var TAG = "FirstPresent"
    }

    fun toSaveText(noteTitle: TextInputLayout, editText: EditText)

    fun saveText(noteTitle: TextInputLayout, editText: EditText)

}