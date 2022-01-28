package ru.dinikos.mynotes.mvp.presenters

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.view.DataView
import java.util.*

class DataPresenterImpl(private val view: DataView?, private  val database: AppDatabase): DataPresenter {

    override fun onLoadAllNotes() {
        view?.onLoadAllNotes()
    }

    override suspend fun getAll(): Flow<List<Note>> {
        return database.noteDao().loadAll()
    }

    override fun insertNote(note: Note): Long {
        return database.noteDao().insert(note)
    }

    override fun insertNotes(listNote: List<Note>) {
        return database.noteDao().insertNotes(listNote)
    }

    override fun updateNote(note: Note) {
        note.changeDate = Date().toString()
        return database.noteDao().update(note)
    }

    override fun deleteNote(note: Note) {
        database.noteDao().delete(note)
    }

    override fun deleteAllNote(listNote: List<Note>) {
        database.noteDao().deleteAll(listNote)
    }

}