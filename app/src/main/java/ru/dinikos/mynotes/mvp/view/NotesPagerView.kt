package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.data.entities.Note

interface NotesPagerView {

    fun openPagerViewActivity(note: Note?, position: Int?)

}