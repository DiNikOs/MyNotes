package ru.dinikos.mynotes.mvp.presenter

import ru.dinikos.mynotes.mvp.view.BaseView

class StartPresenter(var view: BaseView?): BasePresenter  {

    /**
     * Попытка сохраниния заметки
     *
     * @param title
     * @param text
     */
    override fun toSaveText(title: String, text: String) {
        var textError: String = dataIsEmpty(title, text)
        if (textError.isNotBlank()) view?.onAttemptSaveBlankText(textError)
        else saveText(title, text)
    }

    /**
     * Успешное действие с заметкой
     *
     * @param title
     * @param text
     */
    override fun saveText(title: String, text: String) {
        view?.onSaveSuccess(title, text)
    }

    /**
     * Обработка действий с заметкой по отправке в сторонние ресурсы
     *
     * @param noteTitle
     * @param editText
     */
    override fun shareDataBtn(noteTitle: String, editText: String) {
        var textError: String = dataIsEmpty(noteTitle, editText)
        if (textError.isNotBlank()) view?.onAttemptSaveBlankText(textError)
        else view?.shareData(noteTitle, editText)
    }

    /**
     * Обработка перехода About Activity
     *
     */
    override fun operateAboutBtn() {
        view?.openAboutActivity()
    }

    private fun dataIsEmpty(title: String, text: String): String {
        var errorText = ""
        if (title.isNullOrBlank()) errorText = "title"
        if (text.isNullOrBlank()) errorText += " editText"
        return errorText
    }
}
