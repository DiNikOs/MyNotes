package ru.dinikos.mynotes.mvp.presenters

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note

interface DataPresenter  {

   fun onLoadAllNotes()

   fun onLoadTestDates()

   fun getAll(): Flow<List<Note>>?

   fun insertNote(note: Note): Long?

   fun insertNotes(listNote: List<Note>)

   fun updateNote(note: Note)

   fun deleteNote(note: Note)

   fun deleteAllNote(listNote: List<Note>)

}