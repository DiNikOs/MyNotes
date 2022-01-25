package ru.dinikos.mynotes.mvp.presenters

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.data.repositories.RepositoryNotes
import java.util.*

class DataPresenterImpl(private  val database: AppDatabase): DataPresenter {

    private var repository: RepositoryNotes = RepositoryNotes
    private var listNotes: MutableList<Note> = repository.getTestListNotes(10)

    override fun setDates(list: MutableList<Note>) {
        for (listNote in list) {
            listNotes.add(listNote)
        }
    }

    override fun getDates(): MutableList<Note> {
        return listNotes
    }

    override suspend fun getAll(): Flow<List<Note>> {
        return database.noteDao().loadAll()
    }

    override suspend fun insertNote(note: Note): Long {
        return database.noteDao().insert(note)
    }

    override suspend fun insertNotes(listNote: List<Note>) {
        return database.noteDao().insertNotes(listNote)
    }

    override suspend fun updateNote(note: Note) {
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