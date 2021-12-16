package ru.dinikos.mynotes.mvp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.databinding.ActivityMainBinding
import ru.dinikos.mynotes.mvp.data.repositories.RepositoryNotes
import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.fragments.NoteFragmentSupport
import ru.dinikos.mynotes.mvp.fragments.RecyclerFragmentSupport
import ru.dinikos.mynotes.mvp.presenters.BasePresenter
import ru.dinikos.mynotes.mvp.presenters.StartPresenter
import ru.dinikos.mynotes.mvp.view.BaseView.Companion.TAG_MAIN_VIEW
import ru.dinikos.mynotes.mvp.view.BaseView.Companion.TYPE_SHARE
import java.util.*

class MainActivityImpl : AppCompatActivity(), BaseView {

    private lateinit var binding: ActivityMainBinding

    private var startPresent: BasePresenter? = null

    private var repository: RepositoryNotes = RepositoryNotes()

    private var listNotes: MutableList<Note> = repository!!.getTestListNotes(10)

    /**
     * Вызов при первом создании Activity
     *
     * @param savedInstanceState  контекст для работы с Activity(ключ-значение)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    /**
     * Инициализация view и описания взаимодействия с Presenter
     *
     */
    private fun init() {
        startPresent = StartPresenter(this)
        showRecyclerFragment(listNotes)
        toolbar_btn_about.setOnClickListener {
            startPresent?.operateAboutBtn()
        }
    }

    /**
     * Вызов перед открытием Activity
     *
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_on_start))
    }

    /**
     * Вызов перед предоставлением доступа к Activity
     *
     */
    override fun onResume() {
        super.onResume()
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_on_resume))
    }

    /**
     * Вызов когда Activity теряет фокус
     *
     */
    override fun onPause() {
        super.onPause()
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_on_pause))
    }

    /**
     * Вызов после удаления Activity с экрана
     *
     */
    override fun onStop() {
        super.onStop()
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_on_stop))
    }

    /**
     * Вызов перед уничтожением Activity
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_on_destroy))
        startPresent = null
    }

    /**
     * Действие при успешном сохранении
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun onSaveSuccess(title: String, text: String) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_success) + " title:" + title)
        listNotes.add(Note(listNotes.size.toLong(), title, text, Date(), Date()))
        showToast(getString(R.string.msg_success), title)
    }

    /**
     * Действие при не успешном сохранении
     *
     * @param text  текст с описанием где ошибка
     */
    override fun onSaveError(text: String) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_not_success) + " title:" + text)
        showToast(getString(R.string.msg_error_save_text), text)
    }

    /**
     * Действие при попытке сохранения пустой заметки
     *
     * @param text  текст с описанием где ошибка
     */
    override fun onAttemptSaveBlankText(text: String) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_error_save_text))
        showToast(getString(R.string.msg_data_blank), text)
    }

    /**
     * Отправка данных в другие сервисы
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun shareData(title: String, text: String) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_shareData) + " title:" + title)
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = TYPE_SHARE
            putExtra(Intent.EXTRA_TEXT, "$title:\n$text")
        })
    }

    override fun openAboutActivity() {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_openAbout))
        startActivity(Intent(this, AboutActivity::class.java))
    }

    /**
     * Показ тоаста
     *
     * @param msg  сообщение формируемое по событию
     * @param text  текст с результатом события
     */
    private fun showToast(msg: String, text: String) =
        Toast.makeText(this, "$msg:$text", Toast.LENGTH_LONG).show()


    private fun showNoteFragment(note: Note) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_intent_frag) + " - note: $note")
        NoteFragmentSupport.newInstance(note)
            .showFragment(supportFragmentManager)
    }

    private fun showRecyclerFragment(list: MutableList<Note>) {
        RecyclerFragmentSupport.newInstance(list, onItemClick = {
            showNoteFragment(it)
        }).showFragment(supportFragmentManager)
    }
}

