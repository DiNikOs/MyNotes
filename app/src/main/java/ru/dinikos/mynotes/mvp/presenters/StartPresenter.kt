package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.view.BaseView

class StartPresenter(var view: BaseView?): BasePresenter  {

    /**
     * Попытка сохраниния заметки
     *
     * @param note  заметка на сохранение
     */
    override fun toSaveNote(note: Note) {
        var textError: String = dataIsEmpty(note.title, note.text)
        if (textError.isNotBlank()) view?.onAttemptSaveBlankText(textError)
        else saveNote(note)
    }

    /**
     * Успешное сохранение заметки
     *
     * @param note заметка для сохранения
     */
    override fun saveNote(note:Note) {
        view?.onSaveSuccessNote(note)
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
