package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.entities.Note

interface DataPresenter  {

   fun setDates(list: MutableList<Note>)

   fun getDates(): List<Note>?
}