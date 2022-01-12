package ru.dinikos.mynotes.mvp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.*
import ru.dinikos.mynotes.mvp.view.BaseView
import ru.dinikos.mynotes.mvp.view.DefaultView
import ru.dinikos.mynotes.mvp.view.PagerView

class NoteFragment : Fragment(), DefaultView, ShowFragmentSupport, BaseView, PagerView {

    private var noteTitle: EditText? = null
    private var noteText: EditText? = null
    private var noteCreateDate: TextView? = null
    private var shareDataBtn: AppCompatImageView? = null
    private var toolbarBtnSaveNote: AppCompatImageView? = null
    private var toolbarBtnDelNote: AppCompatImageView? = null

    private lateinit var managerFrag: FragmentManager
    private var startPresent: BasePresenter? = null
    private var defaultPresenter: DefaultPresenter? = null
    private var dataPresenter: DataPresenter? = null

    companion object {

        const val TAG_NOTE_FRAG = "NoteFragment TAG"
        const val ARG_NOTE = "note"

        fun newInstance(note: Note): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = bundleOf(
            ARG_NOTE to note
            )
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
        dataPresenter = DataPresenterImpl(null, AppDatabase.getDataBase(this))

        noteTitle = itemView.findViewById(R.id.noteTitle)
        noteText = itemView.findViewById(R.id.noteText)
        noteCreateDate = itemView.findViewById(R.id.noteCreateDate)

        shareDataBtn = itemView.findViewById(R.id.share_data_btn)
        toolbarBtnSaveNote = itemView.findViewById(R.id.toolbar_btn_save_note)
        toolbarBtnDelNote = itemView.findViewById(R.id.toolbar_btn_delete_note)

        var note:Note = arguments?.get(ARG_NOTE) as Note

        if(note.noteId == null) {
            toolbarBtnDelNote?.visibility = View.INVISIBLE
            shareDataBtn?.visibility = View.INVISIBLE
            noteTitle?.setText("")
            noteText?.setText("")
        } else {
            noteTitle?.setText(note.title)
            noteText?.setText(note.text)
        }

        noteCreateDate?.text = note.createDate

        shareDataBtn?.setOnClickListener {
            var noteTitle = noteTitle?.text.toString()
            Log.d(TAG_NOTE_FRAG, "shareDataBtn noteTitle: $noteTitle")
            startPresent?.shareDataBtn(noteTitle, noteText?.text.toString())
        }

        toolbarBtnSaveNote?.setOnClickListener {
            note.title = noteTitle?.text.toString()
            note.text = noteText?.text.toString()
            Log.d(TAG_NOTE_FRAG, " toolbarBtnSaveNote: $note")
            startPresent?.toSaveNote(note)
        }

        toolbarBtnDelNote?.setOnClickListener {
            startPresent?.deleteNote(note)
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

    override fun onSaveSuccessNote(note: Note) {
        showSelectionDialog(note)
    }

    override fun onDeleteNote(note: Note) {
        showSelectionDialogDelete(note)
    }

    override fun onSaveError(text: String) {
        TODO("Not yet implemented")
    }

    override fun onAttemptSaveBlankText(text: String) {
        Log.d(BaseView.TAG_MAIN_VIEW, getString(R.string.msg_error_save_text))
        showWarningDialog()
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

    override fun getPagerData(): Note {
        return arguments?.get(ARG_NOTE) as Note
    }

    private fun showWarningDialog() {
        WarningDialog().show(this.parentFragmentManager, WarningDialog.TAG)
    }

    private fun showSelectionDialog(note: Note) {
        AskToSaveDialog.createInstance { okClickedSave(note) }.show(this.parentFragmentManager, AskToSaveDialog.TAG)
    }

    private fun showSelectionDialogDelete(note: Note) {
        AskToDeleteDialog.createInstance { okClickedDelete(note) }.show(this.parentFragmentManager, AskToDeleteDialog.TAG)
    }

    private fun okClickedSave(note: Note) {
        dataPresenter = DataPresenterImpl(null, AppDatabase.getDataBase(this))
        if(note.noteId == null) {
            lifecycleScope.launch {
                dataPresenter?.insertNote(note)
            }
        } else {
            lifecycleScope.launch {
                dataPresenter?.updateNote(note)
            }
        }
    }

    private fun okClickedDelete(note: Note) {
        dataPresenter = DataPresenterImpl(null, AppDatabase.getDataBase(this))
        if(note.noteId != null) {
            lifecycleScope.launch {
                dataPresenter?.deleteNote(note)
            }
        }
    }

}