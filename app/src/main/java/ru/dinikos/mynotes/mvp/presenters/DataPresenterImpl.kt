package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.view.DataView

class DataPresenterImpl(var dataView: DataView?): DataPresenter {

    override fun setDates(list: MutableList<Note>) {
        dataView?.setDate(list)
    }

    override fun getDates(): List<Note>? {
        return dataView?.getDates()
    }

}