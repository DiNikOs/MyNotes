package ru.dinikos.mynotes.mvp.view

interface BaseView {

    companion object {
        const val TAG_MAIN_VIEW = "MainView"
        const val TAG_ABOUT = "AboutView"
        const val TYPE_SHARE = "text/plain"
    }

    fun onSaveSuccess(title: String, text: String)

    fun onSaveError(text: String)

    fun onAttemptSaveBlankText(text: String)

    fun shareData(title: String, text: String)

    fun openAboutActivity()

}