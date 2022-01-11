package ru.dinikos.mynotes.mvp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.data.entities.Note

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
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialog_save_title)
                .setMessage(R.string.msg_dialog_save)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_ok)
                { _, _ ->
                    onSave?.invoke()
                    Toast.makeText(
                        this.activity, getString(R.string.msg_success_ok),
                        Toast.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton(R.string.dialog_cancel)
                     { dialog, _ ->
                        dialog.cancel()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

