package ru.dinikos.mynotes.mvp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_note.backToStartActivity
import kotlinx.android.synthetic.main.fragment_note.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.presenters.BasePresenter
import ru.dinikos.mynotes.mvp.presenters.DefaultPresenter

class NoteFragment: Fragment(), DefaultView {

    private var startPresent: BasePresenter? = null
    private var defaultPresenter: DefaultPresenter? = null

    companion object {
        val TAG_NOTE_FRAG = NoteFragment:: class.java.name + " TAG"

        val ARG_TITLE = "titleNoteTxt"
        val ARG_TEXT = "textNoteTxt"
        val ARG_CREATE_DATE = "createDateNoteTxt"

        fun newInstance(note: Note): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_TITLE, note.title)
                putString(ARG_TEXT, note.text)
                putString(ARG_CREATE_DATE, note.createDate.toString())
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG_NOTE_FRAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    /**
     * Заполение формы фрагмента
     *
     * @param itemView  представление
     * @param savedInstanceState  бандлы
     */
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        itemView.noteTitle.setText(arguments?.getString(ARG_TITLE))
        itemView.noteText.setText(arguments?.getString(ARG_TEXT))
        itemView.noteCreateDate.text = arguments?.getString(ARG_CREATE_DATE)
        super.onViewCreated(itemView, savedInstanceState)
        saveTextBtn.also{
            it.setOnClickListener {
                startPresent?.toSaveText(noteTitle.text.toString(), noteText.text.toString())
            }
        }
        shareDataBtn.also {
            it.setOnClickListener {
                startPresent?.shareDataBtn(noteTitle.text.toString(), noteText.text.toString())
            }
        }
        backToStartActivity.also {
            it.setOnClickListener {
                defaultPresenter?.backToMainActivity()
            }
        }
        Log.d(TAG_NOTE_FRAG, "onViewCreated")
    }

    /**
     * Замещает новыми значениями фрагмент
     *
     * @param manager
     * @param containerViewId
     */
    fun showDetails(manager: FragmentManager, containerViewId: Int) {
        val fragment: Fragment? = this
        if (fragment != null) {
            manager
                .beginTransaction()
                .replace(containerViewId, fragment)
                .commit()
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun backToMainActivity() {
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_backToMain))
        val activity = activity as MainActivityImpl
        activity.onBackPressed()
    }
}