package ru.dinikos.mynotes.mvp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_note.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.presenters.BasePresenter
import ru.dinikos.mynotes.mvp.presenters.DefaultPresentImpl
import ru.dinikos.mynotes.mvp.presenters.DefaultPresenter
import ru.dinikos.mynotes.mvp.presenters.StartPresenter
import ru.dinikos.mynotes.mvp.view.BaseView
import ru.dinikos.mynotes.mvp.view.DefaultView

class NoteFragmentSupport : Fragment(), DefaultView, ShowFragmentSupport, BaseView {

    private var startPresent: BasePresenter? = null
    private var defaultPresenter: DefaultPresenter? = null
    private lateinit var managerFrag: FragmentManager

    companion object {

        const val TAG_NOTE_FRAG = "NoteFragment TAG"
        const val ARG_TITLE = "titleNoteTxt"
        const val ARG_TEXT = "textNoteTxt"
        const val ARG_CREATE_DATE = "createDateNoteTxt"

        fun newInstance(note: Note): NoteFragmentSupport {
            val fragment = NoteFragmentSupport()
            fragment.arguments = bundleOf(
                ARG_TITLE to note.title,
                ARG_TEXT to note.text,
                ARG_CREATE_DATE to note.createDate.toString())//
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG_NOTE_FRAG, "onCreateView")
        defaultPresenter = DefaultPresentImpl(this)
        startPresent = StartPresenter(this)
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        startPresent = null
        defaultPresenter = null
    }

    /**
     * Заполение формы фрагмента
     *
     * @param itemView  представление
     * @param savedInstanceState  бандлы
     */
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        Log.d(TAG_NOTE_FRAG, "onViewCreated")
        noteTitle.setText(arguments?.getString(ARG_TITLE).orEmpty())
        noteText.setText(arguments?.getString(ARG_TEXT).orEmpty())
        noteCreateDate.text = arguments?.getString(ARG_CREATE_DATE).orEmpty()

        saveTextBtn.setOnClickListener {
            startPresent?.toSaveText(noteTitle.text.toString(), noteText.text.toString())
        }

        shareDataBtn.setOnClickListener {
            startPresent?.shareDataBtn(noteTitle.text.toString(), noteText.text.toString())
        }
        back_to_start_activity.setOnClickListener {
            defaultPresenter?.backToMainActivity()
        }
    }

    /**
     * Замещает новыми значениями фрагмент
     *
     * @param manager
     */
    override fun showFragment(manager: FragmentManager) {
        val fragment: Fragment? = this
        managerFrag = manager
        if (fragment != null) {
            managerFrag
                .beginTransaction()
                .replace(R.id.container_recycler, fragment)
                .addToBackStack(fragment.tag)
                .commit()
        }
    }

    override fun backToMainActivity() {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_backToMain))
        managerFrag.popBackStack()
    }

    override fun onSaveSuccess(title: String, text: String) {
        TODO("Not yet implemented")
    }

    override fun onSaveError(text: String) {
        TODO("Not yet implemented")
    }

    override fun onAttemptSaveBlankText(text: String) {
        TODO("Not yet implemented")
    }

    override fun shareData(title: String, text: String) {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_shareData) + " title:" + title)
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = BaseView.TYPE_SHARE
            putExtra(Intent.EXTRA_TEXT, "$title:\n$text")
        })
    }

    override fun openAboutActivity() {
        TODO("Not yet implemented")
    }

}