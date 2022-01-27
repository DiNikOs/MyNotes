package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.view.MainView

class MainMenuPresenter(var view: MainView?): MainPresenter {

    /**
     * Обработка перехода About Activity
     *
     */
    override fun operateAboutBtn() {
        view?.openAboutActivity()
    }

    override fun openPagerViewBtn() {
        view?.openPagerViewActivity(null, -1)
    }

}