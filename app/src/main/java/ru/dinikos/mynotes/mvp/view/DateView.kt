package ru.dinikos.mynotes.mvp.view

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note

interface DataView {

    suspend fun setDate(list: MutableList<Note>)

    fun onLoadAllNotes()

    suspend fun loadAllNotes(): Flow<List<Note>>?

    fun insertNote(note: Note): Long?

    fun insertNotes(listNote: List<Note>)

    fun updateNote(note: Note)

    fun deleteNote(note: Note)

    fun deleteAllNote(listNote: List<Note>)

}