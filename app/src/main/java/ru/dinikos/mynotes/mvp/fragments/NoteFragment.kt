package ru.dinikos.mynotes.mvp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.*
import ru.dinikos.mynotes.mvp.view.*

class NoteFragment : Fragment(), ShowFragmentSupport, NoteView, DataView {

    private var noteTitle: EditText? = null
    private var noteText: EditText? = null
    private var noteCreateDate: TextView? = null
    private var shareDataBtn: AppCompatImageView? = null
    private var toolbarBtnSaveNote: AppCompatImageView? = null
    private var toolbarBtnDelNote: AppCompatImageView? = null
    var dataPresenter: DataPresenter? = null
    var dataBase: AppDatabase? = null

    private lateinit var managerFrag: FragmentManager
    private var notePresent: NotePresenter? = null

    companion object {

        const val TAG_NOTE_FRAG = "NoteFragment TAG"
        const val ARG_NOTE = "note"
        const val TYPE_SHARE = "text/plain"

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
        dataBase = AppDatabase.getDataBase(inflater.context)
        dataBase?.let { dataPresenter = DataPresenterImpl(this, it) }
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notePresent = null
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
        notePresent = NotesMenuPresenter(this)

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
            notePresent?.shareDataBtn(noteTitle, noteText?.text.toString())
        }

        toolbarBtnSaveNote?.setOnClickListener {
            note.title = noteTitle?.text.toString()
            note.text = noteText?.text.toString()
            Log.d(TAG_NOTE_FRAG, " toolbarBtnSaveNote: $note")
            notePresent?.toSaveNote(note)
            activity?.onContentChanged()
        }

        toolbarBtnDelNote?.setOnClickListener {
            notePresent?.deleteNote(note)
            activity?.onContentChanged()
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

    override fun onSaveSuccessNote(note: Note) {
        showSelectionDialog(note)
    }

    override fun onDeleteNote(note: Note) {
        showSelectionDialogDelete(note)
    }

    override fun onSaveError(text: String) {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_error_save_text))
        showWarningDialog()
    }

    override fun onAttemptSaveBlankText(text: String) {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_error_save_text))
        showWarningDialog()
    }

    /**
     * Отправка данных в другие сервисы
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun shareData(title: String, text: String) {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_shareData) + " title:" + title)
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = TYPE_SHARE
            putExtra(Intent.EXTRA_TEXT, "$title:\n$text")
        })
    }

    private fun showWarningDialog() {
        WarningDialog().show(this.parentFragmentManager, WarningDialog.TAG)
    }

    private fun showSelectionDialog(note: Note) {
        AskToSaveDialog.createInstance { okClickedSave(note) }
            .show(requireActivity().supportFragmentManager, AskToSaveDialog.TAG)
    }

    private fun showSelectionDialogDelete(note: Note) {
        AskToDeleteDialog.createInstance { okClickedDelete(note) }
            .show(requireActivity().supportFragmentManager, AskToDeleteDialog.TAG)
    }

    private fun okClickedSave(note: Note) {
        if (note.noteId == null) {
            dataPresenter?.insertNote(note)
            // возврат на главный экран со списком при сохранении новой записи.
            activity?.onBackPressed()
        } else {
            dataPresenter?.updateNote(note)
        }
        Toast.makeText(
            this.activity, getString(R.string.msg_success_ok),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun okClickedDelete(note: Note) {
        if(note.noteId != null) {
            dataPresenter?.deleteNote(note)
            Toast.makeText(
                this.activity, getString(R.string.msg_delete_success_ok),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onLoadAllNotes(notes: Flow<List<Note>>) {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_load_notes))
    }

    override fun onLoadTestDates(list: List<Note>) {
        Log.d(TAG_NOTE_FRAG, getString(R.string.msg_load_test_notes))
    }

}