package ru.dinikos.mynotes.mvp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.databinding.ActivityMainBinding
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.fragments.RecyclerFragment
import ru.dinikos.mynotes.mvp.presenters.*
import ru.dinikos.mynotes.mvp.view.BaseView.Companion.TAG_MAIN_VIEW
import ru.dinikos.mynotes.mvp.view.BaseView.Companion.TYPE_SHARE


class MainActivityImpl : AppCompatActivity(), BaseView, NotesPagerView, DataView {

    private lateinit var binding: ActivityMainBinding

    private var startPresent: BasePresenter? = null
    private var dataPresenter: DataPresenter? = null
    private var pagerPresenter: NotesPagerPresenter? = null
    private var database:AppDatabase? = null

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
        database =  AppDatabase.getDataBase(context = this)
        dataPresenter = DataPresenterImpl(this, AppDatabase.getDataBase(this))

        pagerPresenter = NotesPagerPresenterImpl(this)
        showRecyclerFragment()
        binding.toolbarBtnAbout.setOnClickListener {
            startPresent?.operateAboutBtn()
        }
        binding.toolbarBtnAddNote.setOnClickListener {
            pagerPresenter?.openPagerViewBtn()
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
        dataPresenter = null
        pagerPresenter = null
    }

    override fun onSaveSuccessNote(note: Note) {
        TODO("Not yet implemented")
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
     * Open PagerView Activity
     *
     * @param note - open item in PagerView
     */
    override fun openPagerViewActivity(note: Note?, position: Int?) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_openPagerView))
        NotesPagerActivity.startActivity(this, position)
    }

    /**
     * Показ тоаста
     *
     * @param msg  сообщение формируемое по событию
     * @param text  текст с результатом события
     */
    private fun showToast(msg: String, text: String) =
        Toast.makeText(this, "$msg:$text", Toast.LENGTH_LONG).show()


    private fun showNoteFragment(note: Note, position:Int) {
        Log.d(TAG_MAIN_VIEW, getString(R.string.msg_intent_frag) + " - note: $note")
        openPagerViewActivity(note, position)
    }

    private fun showRecyclerFragment() {
        var note:Note? = null
        RecyclerFragment.newInstance(onItemClick = {
            note = it
        }, position = {
            note?.let { it2 -> showNoteFragment(it2, it) }
        }).showFragment(supportFragmentManager)
    }

    override fun setDate(list: MutableList<Note>) {
        TODO("Not yet implemented")
    }

    override fun getDates(): MutableList<Note> {
        TODO("Not yet implemented")
    }

    override fun loadAllNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override fun insertNote(note: Note): Long {
        TODO("Not yet implemented")
    }

    override fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

}

