package ru.dinikos.mynotes.mvp.view

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import ru.dinikos.mynotes.mvp.presenter.BasePresenter

interface BaseView {

    fun init(context: Context, view: View)

    fun destroy()

    fun onSaveSuccess(title: TextInputLayout)

    fun onSaveError(title: TextInputLayout)

    fun onAttemptSaveBlankText(title: TextInputLayout)

    fun showToast(msg: String, title: TextInputLayout)
}