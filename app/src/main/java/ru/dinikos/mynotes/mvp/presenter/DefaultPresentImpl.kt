package ru.dinikos.mynotes.mvp.presenter

import ru.dinikos.mynotes.mvp.view.DefaultView

class DefaultPresentImpl (var view: DefaultView?): DefaultPresenter {

    override fun backToMainActivity() {
        view?.backToMainActivity()
    }

}