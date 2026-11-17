package ru.dinikos.mynotes.mvp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.dinikos.mynotes.R

class WarningDialog: DialogFragment() {

    companion object {
        val TAG = WarningDialog::class.java.name + " TAG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.msg_data_blank)
                .setPositiveButton(R.string.warning_ok) {
                        dialog, _ ->  dialog.cancel()                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}