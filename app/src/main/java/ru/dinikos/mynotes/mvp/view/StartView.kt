package ru.dinikos.mynotes.mvp.view

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.getSystemService
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.presenter.BasePresenter
import ru.dinikos.mynotes.mvp.presenter.StartPresenter
import android.widget.Toast.makeText as makeText

class StartView(var view: BaseView?): BaseView {

    private lateinit var editText: EditText
    private lateinit var noteTitle: TextInputLayout
    private lateinit var saveBtn: Button
    private var res: Resources = Resources.getSystem()
    private var context: Context? = null
    private var viewOut: View? = null
    private var presenter: StartPresenter? = null


    override fun init(contextIn: Context, viewIn: View) {
        context = contextIn
        viewOut = viewIn
        editText = viewOut!!.noteText
        noteTitle = viewOut!!.noteTitle
        saveBtn = viewOut!!.saveText.also {
            it.setOnClickListener{
                presenter?.toSaveText(noteTitle, editText)
            }
        }
    }

    override fun destroy() {
        view = null
        viewOut = null
    }

    override fun onSaveSuccess(title: TextInputLayout) {
        showToast(res.getString(R.string.msg_success), title)
    }

    override fun onSaveError(title: TextInputLayout) {
        showToast(res.getString(R.string.msg_error_save_text), title)
    }

    override fun onAttemptSaveBlankText(title: TextInputLayout) {
        showToast(res.getString(R.string.msg_data_blank), title)
    }

    override fun showToast(msg: String, title: TextInputLayout) {
       makeText(msg, title)
    }

    private fun makeText(msg: String, title: TextInputLayout) =
        makeText(context, "$msg:title:$title", Toast.LENGTH_LONG).show()

}