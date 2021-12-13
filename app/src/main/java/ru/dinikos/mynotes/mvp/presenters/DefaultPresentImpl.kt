package ru.dinikos.mynotes.mvp.presenters

import ru.dinikos.mynotes.mvp.view.DefaultView

class DefaultPresentImpl (var view: DefaultView?): DefaultPresenter {

    override fun backToMainActivity() {
        view?.backToMainActivity()
    }

}