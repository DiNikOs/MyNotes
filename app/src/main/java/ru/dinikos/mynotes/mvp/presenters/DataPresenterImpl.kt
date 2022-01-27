package ru.dinikos.mynotes.mvp.presenters

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.view.DataView
import java.util.*

class DataPresenterImpl(private val view: DataView?, private  val database: AppDatabase?): DataPresenter {

    override suspend fun setDates(list: MutableList<Note>) {
        view?.setDate(list)
    }

    override fun getDates() {
        view?.onLoadAllNotes()
    }

    override suspend fun getAll(): Flow<List<Note>>? {
        return view?.loadAllNotes()
    }

    override fun insertNote(note: Note): Long? {
        return view?.insertNote(note)
    }

    override suspend fun insertNotes(listNote: List<Note>) {
        view?.insertNotes(listNote)
    }

    override suspend fun updateNote(note: Note) {
        note.changeDate = Date().toString()
        view?.updateNote(note)
    }

    override fun deleteNote(note: Note) {
       view?.deleteNote(note)
    }

    override fun deleteAllNote(listNote: List<Note>) {
        view?.deleteAllNote(listNote)
    }

}