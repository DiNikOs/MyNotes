package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.view.BaseView

class StartPresenter(var view: BaseView?): BasePresenter  {

    /**
     * Попытка сохраниния заметки
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun toSaveText(title: String, text: String) {
        var textError: String = dataIsEmpty(title, text)
        if (textError.isNotBlank()) view?.onAttemptSaveBlankText(textError)
        else saveText(title, text)
    }

    /**
     * Успешное действие с заметкой
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun saveText(title: String, text: String) {
        view?.onSaveSuccess(title, text)
    }

    /**
     * Обработка действий с заметкой по отправке в сторонние ресурсы
     *
     * @param noteTitle  заголовок заметки
     * @param editText  тест заметки
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

    /**
     * Обработка перехода About Activity
     *
     */
    override fun openPagerViewBtn() {
        view?.openPagerViewActivity()
    }

    /**
     * Обработка отображения фрагмента
     *
     * @param note  модель данных
     * @param containerViewId  id контейнера который будет получать фрагмент
     */
    override fun showFragment(note: Note, containerViewId:Int) {
        view?.showNoteFragment(note, containerViewId)
    }

    /**
     * Проверка ввода данных
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     * @return
     */
    private fun dataIsEmpty(title: String, text: String): String {
        var errorText = ""
        if (title.isNullOrBlank()) errorText = "title"
        if (text.isNullOrBlank()) errorText += " editText"
        return errorText
    }
}
