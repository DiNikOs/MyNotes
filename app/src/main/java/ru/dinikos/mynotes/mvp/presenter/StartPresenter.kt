package ru.dinikos.mynotes.mvp.presenter

import ru.dinikos.mynotes.mvp.view.BaseView

class StartPresenter(var view: BaseView?): BasePresenter  {

    override fun toSaveText(title: String, text: String) {
        var textError: String = dataIsEmpty(title, text)
        if (textError.isNotBlank()) {
            view?.onAttemptSaveBlankText(textError)
        } else
        saveText(title, text)
    }

    override fun saveText(title: String, text: String) {
        view?.onSaveSuccess(title, text)
    }

    private fun dataIsEmpty(title: String, text: String): String {
        var errorText = ""
        if (title.isNullOrBlank()) errorText = "title"
        if (text.isNullOrBlank()) errorText += " editText"
        return errorText
    }
}
