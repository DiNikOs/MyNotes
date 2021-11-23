package ru.dinikos.mynotes.mvp.presenter

import android.content.res.Resources
import android.util.Log
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.presenter.BasePresenter.Companion.TAG
import ru.dinikos.mynotes.mvp.view.BaseView
import ru.dinikos.mynotes.mvp.view.MainActivityImpl

class StartPresenter(var view: BaseView, var res: Resources): BasePresenter  {

    override fun toSaveText(title: TextInputLayout, text: EditText) {
        if (dataIsEmpty(title, text)) {
            Log.d(TAG, res.getString(R.string.msg_error_save_text))
            view?.onAttemptSaveBlankText(title)
        } else
        saveText(title, text)
    }

    override fun saveText(title: TextInputLayout, text: EditText) {
        Log.d(TAG, res.getString(R.string.msg_success)+ " title:" + title.toString())
        view?.onSaveSuccess(title)
    }

    fun dataIsEmpty(title: TextInputLayout, text: EditText): Boolean {
        return title.toString().isNullOrEmpty()||text.toString().isNullOrEmpty()
    }


}

