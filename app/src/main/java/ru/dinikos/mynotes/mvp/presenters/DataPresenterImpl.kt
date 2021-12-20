package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.data.repositories.RepositoryNotes

class DataPresenterImpl: DataPresenter {

    private var repository: RepositoryNotes = RepositoryNotes
    private var listNotes: MutableList<Note> = repository.getTestListNotes(10)

    override fun setDates(list: MutableList<Note>) {
        for (listNote in list) {
            listNotes.add(listNote)
        }
    }

    override fun getDates(): MutableList<Note> {
        return listNotes
    }

}