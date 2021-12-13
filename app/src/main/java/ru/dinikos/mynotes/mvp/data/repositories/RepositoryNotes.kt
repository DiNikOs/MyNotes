package ru.dinikos.mynotes.mvp.data.repositories

import ru.dinikos.mynotes.mvp.entities.Note
import java.util.*

class RepositoryNotes {

    fun addNotesList(notes: List<Note>): List<Note> {
        val result = mutableListOf<Note>()
        for (note in notes) {
           result.add(note)
        }
        return result
    }

    fun notesTestMap(notes: List<Note>) = notes.map {
        Note(1, "title1", "text1", Date(), Date())
        Note(2, "title2", "text2", Date(), Date())
        Note(3, "title3", "text3", Date(), Date())
        Note(4, "title4", "text4", Date(), Date())
        Note(5, "title5", "text5", Date(), Date())
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