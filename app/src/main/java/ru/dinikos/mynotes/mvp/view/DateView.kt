package ru.dinikos.mynotes.mvp.view

import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note

interface DataView {

    fun onLoadAllNotes(list: Flow<List<Note>>)

    fun onLoadTestDates(list: List<Note>)

}