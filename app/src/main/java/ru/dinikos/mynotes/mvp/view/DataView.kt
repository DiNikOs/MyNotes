package ru.dinikos.mynotes.mvp.view

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note


interface DataView {

    fun setDate(list: MutableList<Note>)

    fun getDates(): MutableList<Note>

    fun loadAllNotes(): Flow<List<Note>>

    fun insertNote(note: Note): Long

    fun deleteNote(note: Note)

}