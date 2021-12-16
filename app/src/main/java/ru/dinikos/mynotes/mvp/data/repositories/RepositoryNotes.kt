package ru.dinikos.mynotes.mvp.data.repositories

import ru.dinikos.mynotes.mvp.entities.Note
import java.util.*

object RepositoryNotes {

    fun addNotesList(notes: List<Note>): List<Note> {
        val result = mutableListOf<Note>()
        for (note in notes) {
           result.add(note)
        }
        return result
    }

    /**
     * Предоставление тестовых данных заметки
     *
     * @param size размер массива тестовых данных
     * @return
     */
    fun getTestListNotes(size: Int) : MutableList<Note> {
        val result = mutableListOf<Note>()
        for (inx in 1..size) {
            result.add(Note(inx.toLong(), "title$inx", "text$inx", Date(), Date() ))
        }
        return result
    }
}