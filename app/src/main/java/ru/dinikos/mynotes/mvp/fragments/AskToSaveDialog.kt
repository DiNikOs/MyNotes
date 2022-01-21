package ru.dinikos.mynotes.mvp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.dinikos.mynotes.R

class AskToSaveDialog: DialogFragment() {

    var onSave: (() -> Unit)? = null

    companion object {
        val TAG = AskToSaveDialog::class.java.name + " TAG"

        fun createInstance(onSave: (() -> Unit)): AskToSaveDialog {
            return AskToSaveDialog().apply {
                this.onSave = onSave
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
            builder.setTitle(R.string.dialog_save_title)
                .setMessage(R.string.msg_dialog_save)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_ok)
                { _, _ ->
                    onSave?.invoke()
                }
                .setNegativeButton(R.string.dialog_cancel)
                     { dialog, _ ->
                        dialog.cancel()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

