package ru.dinikos.mynotes.mvp.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.dinikos.mynotes.R

class AscNoteSaveDialog: DialogFragment() {

    companion object {
        val TAG = AscNoteSaveDialog::class.java.name + " TAG"

        fun createInstance(onSaveNote: (() -> Unit)): AscNoteSaveDialog {
            return AscNoteSaveDialog().apply {
                this.onSaveNote = onSaveNote
            }
        }
    }

    var onSaveNote: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_save_title)
            .setMessage(R.string.msg_dialog_save)
            .setNegativeButton(R.string.dialog_save_cancel) { _, _ -> }
            .setPositiveButton(R.string.dialog_save_ok) { _, _ -> onSaveNote?.invoke()}
            .create()
    }
}