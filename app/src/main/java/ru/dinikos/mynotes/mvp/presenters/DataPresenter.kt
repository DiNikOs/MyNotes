package ru.dinikos.mynotes.mvp.presenters

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note

interface DataPresenter  {

   fun setDates(list: MutableList<Note>)

   fun getDates(): MutableList<Note>

   suspend fun getAll(): Flow<List<Note>>?

   suspend fun insertNote(note: Note): Long

   suspend fun insertNotes(listNote: List<Note>)

   suspend fun updateNote(note: Note)

   fun deleteNote(note: Note)

   fun deleteAllNote(listNote: List<Note>)

}