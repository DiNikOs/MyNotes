package ru.dinikos.mynotes.mvp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.adapters.NotesPagerAdapter
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.*
import java.util.*

class NotesPagerActivity : AppCompatActivity(), BaseView, DefaultView {

    private lateinit var adapter: NotesPagerAdapter
    private lateinit var viewPager: ViewPager2

    private var startPresent: BasePresenter? = null
    private var defaultPresenter: DefaultPresenter? = null
    private var dataPresenter: DataPresenter? = null

    companion object {
        private const val TAG_NOTE_ACTIVITY = "NotesPagerActivity"
        private const val SELECTED_POSITION = "selectedPosition"

        fun startNotePagerActivity(
            context: Context,
            selectPosition: Int? = null
        ) {
            val intent = Intent(context, NotesPagerActivity::class.java)
            intent.putExtra(SELECTED_POSITION, selectPosition)
            context.startActivity(intent)
        }
    }

    /**
     * Вызов при первом создании Activity
     *
     * @param savedInstanceState  контекст для работы с Activity(ключ-значение)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        init()
    }

    /**
     * Инициализация view и описания взаимодействия с Presenter
     *
     */
    private fun init() {
        startPresent = StartPresenter(this)
        defaultPresenter = DefaultPresentImpl(this)
        dataPresenter = DataPresenterImpl(null, AppDatabase.getDataBase(this))
        viewPager = findViewById(R.id.view_pager)
        adapter = NotesPagerAdapter(this)
        var position: Int = intent.getIntExtra(SELECTED_POSITION, -1)

        var positionStart: Int = position

        lifecycleScope.launch {
            dataPresenter?.getAll()?.collect {
                var list: MutableList<Note> = it.toMutableList()
                if(position == -1) {
                    positionStart = 0
                    list.add(Note(
                        null,
                        "",
                        "",
                        Date().toString(),
                        Date().toString()
                    ))
                }
                list.reverse()
                adapter.items = list
                viewPager.adapter = adapter
                viewPager.currentItem = positionStart
            }
        }
    }

    /**
     * Вызов перед открытием Activity
     *
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_on_start))
    }

    /**
     * Вызов перед предоставлением доступа к Activity
     *
     */
    override fun onResume() {
        super.onResume()
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_on_resume))
    }

    /**
     * Вызов когда Activity теряет фокус
     *
     */
    override fun onPause() {
        super.onPause()
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_on_pause))
    }

    /**
     * Вызов после удаления Activity с экрана
     *
     */
    override fun onStop() {
        super.onStop()
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_on_stop))
    }

    /**
     * Вызов перед уничтожением Activity
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_on_destroy))
        startPresent = null
        defaultPresenter = null
    }

    override fun onSaveSuccessNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun onDeleteNote(note: Note) {
        TODO("Not yet implemented")
    }

    /**
     * Действие при не успешном сохранении
     *
     * @param text  текст с описанием где ошибка
     */
    override fun onSaveError(text: String) {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_not_success) + " title:" + text)
        showToast(getString(R.string.msg_error_save_text), text)
    }

    /**
     * Действие при попытке сохранения пустой заметки
     *
     * @param text  текст с описанием где ошибка
     */
    override fun onAttemptSaveBlankText(text: String) {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_error_save_text))
        showToast(getString(R.string.msg_data_blank), text)
    }

    /**
     * Отправка данных в другие сервисы
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun shareData(title: String, text: String) {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_shareData) + " title:" + title)
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = BaseView.TYPE_SHARE
            putExtra(Intent.EXTRA_TEXT, "$title:\n$text")
        })
    }

    override fun openAboutActivity() {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_openAbout))
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

    override fun backToMainActivity() {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_backToMain))
        onBackPressed()
    }

}