package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.view.NotesPagerView

class NotesPagerPresenterImpl(var view: NotesPagerView): NotesPagerPresenter {

    /**
     * Обработка перехода About Activity
     *
     */
    override fun openPagerViewBtn() {
        view?.openPagerViewActivity()
    }
}