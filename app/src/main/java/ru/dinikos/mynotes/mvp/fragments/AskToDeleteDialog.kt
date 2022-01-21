package ru.dinikos.mynotes.mvp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.dinikos.mynotes.R

class AskToDeleteDialog : DialogFragment() {

    var onDelete: (() -> Unit)? = null

    companion object {
        val TAG = AskToDeleteDialog::class.java.name + " TAG"

        fun createInstance(onDelete: (() -> Unit)): AskToDeleteDialog {
            return AskToDeleteDialog().apply {
                this.onDelete = onDelete
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // закрытие диалога при смене темы
        if (savedInstanceState != null) {
            dismiss()
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.msg_dialog_delete)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_ok)
                { _, _ ->
                    onDelete?.invoke()
                }
                .setNegativeButton(R.string.dialog_cancel)
                { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}