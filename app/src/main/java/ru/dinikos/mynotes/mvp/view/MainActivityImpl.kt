package ru.dinikos.mynotes.mvp.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.databinding.ActivityMainBinding
import ru.dinikos.mynotes.mvp.presenter.BasePresenter
import ru.dinikos.mynotes.mvp.presenter.StartPresenter

class MainActivityImpl : AppCompatActivity(), BaseView {

    private lateinit var binding: ActivityMainBinding

    private var startPresent: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        startPresent = StartPresenter(this)
        saveText.also{
            it.setOnClickListener {
                startPresent?.toSaveText(noteTitle.text.toString(), noteText.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startPresent = null
    }

    override fun onSaveSuccess(title: String, text: String) {
        Log.d(BasePresenter.TAG, getString(R.string.msg_success) + " title:" + title)
        showToast(getString(R.string.msg_success), title)
    }

    override fun onSaveError(text: String) {
        Log.d(BasePresenter.TAG, getString(R.string.msg_not_success) + " title:" + text)
        showToast(getString(R.string.msg_error_save_text), text)
    }

    override fun onAttemptSaveBlankText(text: String) {
        Log.d(BasePresenter.TAG, getString(R.string.msg_error_save_text))
        showToast(getString(R.string.msg_data_blank), text)
    }

    private fun showToast(msg: String, text: String) =
        Toast.makeText(this, "$msg:$text", Toast.LENGTH_LONG).show()

}