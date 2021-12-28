package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.view.NotesPagerView

class NotesPagerPresenterImpl(var view: NotesPagerView): NotesPagerPresenter {

    /**
     * Обработка перехода Note Activity
     *
     */
    override fun openPagerViewBtn() {
        view?.openPagerViewActivity(null, -1)
    }
}