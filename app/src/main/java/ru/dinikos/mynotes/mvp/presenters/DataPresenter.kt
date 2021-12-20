package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note

interface DataPresenter  {

   fun setDates(list: MutableList<Note>)

   fun getDates(): List<Note>?
}