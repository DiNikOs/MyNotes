package ru.dinikos.mynotes.mvp.view

import ru.dinikos.mynotes.mvp.data.entities.Note

interface PagerView {

    fun getPagerData(): Note
}