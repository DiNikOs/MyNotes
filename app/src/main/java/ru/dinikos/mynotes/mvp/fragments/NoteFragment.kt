package ru.dinikos.mynotes.mvp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_note.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.presenters.BasePresenter
import ru.dinikos.mynotes.mvp.presenters.DefaultPresentImpl
import ru.dinikos.mynotes.mvp.presenters.DefaultPresenter
import ru.dinikos.mynotes.mvp.presenters.StartPresenter
import ru.dinikos.mynotes.mvp.view.BaseView
import ru.dinikos.mynotes.mvp.view.DefaultView

class NoteFragment : Fragment(), DefaultView, ShowFragmentSupport, BaseView {

    private var noteTitle: EditText? = null
    private var noteText: EditText? = null
    private var noteCreateDate: TextView? = null
    private var saveTextBtn: Button? = null
    private var shareDataBtn: Button? = null
    private var backToStartActivity: Button? = null


    private lateinit var managerFrag: FragmentManager
    private var startPresent: BasePresenter? = null
    private var defaultPresenter: DefaultPresenter? = null

    companion object {

        const val TAG_NOTE_FRAG = "NoteFragment TAG"
        const val ARG_TITLE = "titleNoteTxt"
        const val ARG_TEXT = "textNoteTxt"
        const val ARG_CREATE_DATE = "createDateNoteTxt"

        fun newInstance(note: Note): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = bundleOf(
                ARG_TITLE to note.title,
                ARG_TEXT to note.text,
                ARG_CREATE_DATE to note.createDate.toString())
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
        startPresent = StartPresenter(this)
        defaultPresenter = DefaultPresentImpl(this)

        noteTitle = itemView.noteTitle
        noteText = itemView.noteText
        noteCreateDate = itemView.noteCreateDate

        saveTextBtn = itemView.saveTextBtn
        shareDataBtn = itemView.shareDataBtn
        backToStartActivity = itemView.backToStartActivity

        noteTitle?.setText(arguments?.getString(ARG_TITLE).orEmpty())
        noteText?.setText(arguments?.getString(ARG_TEXT).orEmpty())
        noteCreateDate?.text = arguments?.getString(ARG_CREATE_DATE).orEmpty()

        saveTextBtn?.setOnClickListener {
            startPresent?.toSaveText(noteTitle?.text.toString(), noteText?.text.toString())
        }

        shareDataBtn?.setOnClickListener {
            startPresent?.shareDataBtn(noteTitle?.text.toString(), noteText?.text.toString())
        }
        backToStartActivity?.setOnClickListener {
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
        Log.d(BaseView.TAG_MAIN_VIEW, getString(R.string.msg_success) + " title:" + title)
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