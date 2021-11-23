package ru.dinikos.mynotes.mvp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.databinding.ActivityMainBinding
import ru.dinikos.mynotes.mvp.presenter.BasePresenter
import ru.dinikos.mynotes.mvp.presenter.StartPresenter

class MainActivityImpl : AppCompatActivity(), BaseView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var editText: EditText
    private lateinit var noteTitle: TextInputLayout
    private lateinit var saveBtn: Button

    private var startView: StartView? = StartView(null)

    private var startPresent: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init(this, binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        startPresent = null
        startView = null
    }

    override fun init(context: Context, view: View) {
        editText = view.noteText
        noteTitle = view.noteTitle
        startPresent = StartPresenter(this, resources)
        saveBtn = view.saveText.also{
            it.setOnClickListener {
                startPresent?.toSaveText(noteTitle, editText)
            }
        }

    }

    override fun destroy() {
        startView = null
        startPresent = null
    }

    override fun onSaveSuccess(title: TextInputLayout) {
        showToast(getString(R.string.msg_success), title)
    }

    override fun onSaveError(title: TextInputLayout) {
        showToast(getString(R.string.msg_error_save_text), title)
    }

    override fun onAttemptSaveBlankText(title: TextInputLayout) {
        showToast(getString(R.string.msg_data_blank), title)
    }

    override fun showToast(msg: String, title: TextInputLayout) {
        makeText(msg, title)
    }

    private fun makeText(msg: String, title: TextInputLayout) =
        Toast.makeText(this, "$msg:title:$title", Toast.LENGTH_LONG).show()

}