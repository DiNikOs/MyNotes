package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.data.entities.Note


interface MainView {

    companion object {
        const val TAG_MAIN_VIEW = "MainView"
    }

    fun openAboutActivity()

    fun openPagerViewActivity(note: Note?, position: Int?)

}