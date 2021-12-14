package ru.dinikos.mynotes.mvp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.coroutines.CoroutineScope
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.databinding.ActivityMainBinding
import ru.dinikos.mynotes.mvp.adapters.NotesPagerAdapter
import ru.dinikos.mynotes.mvp.adapters.NotesRecyclerAdapter
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.BasePresenter
import ru.dinikos.mynotes.mvp.presenters.DefaultPresentImpl
import ru.dinikos.mynotes.mvp.presenters.DefaultPresenter
import ru.dinikos.mynotes.mvp.presenters.StartPresenter
import java.util.*
import kotlin.coroutines.CoroutineContext

abstract class NotesPagerActivity :
    AppCompatActivity(), CoroutineScope, BaseView, DefaultView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesPagerAdapter
    private lateinit var viewPager: ViewPager2

    private var startPresent: BasePresenter? = null
    private var defaultPresenter: DefaultPresenter? = null

    private var note: Note? = null

    private var listNotes: List<Note>? = null

    abstract val layoutRes: Int?

//    private var adapter: NotesPagerAdapter? =  NotesPagerAdapter(this)

    companion object {
        private const val TAG_NOTE_ACTIVITY = "NotesPagerActivity"
        private const val SELECTED_POSITION = "selectedPosition"
        private const val NOTES_LIST = "notesList"

        fun startActivity(
            context: Context,
            notesList: List<Note>? = null,
            selectPosition: Int = 0
        ) {
            val intent = Intent(context, NotesPagerActivity::class.java)

            notesList?.let {
                intent.putParcelableArrayListExtra(NOTES_LIST, ArrayList(notesList))
            }
            context.startActivity(intent)
        }
    }

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
        defaultPresenter = DefaultPresentImpl(this)

        adapter?.items = intent.getParcelableArrayListExtra(NOTES_LIST)?: listOf(Note())
        viewPager = view_pager
        viewPager.adapter = adapter


//        saveTextBtn.also{
//            it.setOnClickListener {
//                startPresent?.toSaveText(noteTitle.text.toString(), noteText.text.toString())
//            }
//        }
//        shareDataBtn.also {
//            it.setOnClickListener {
//                startPresent?.shareDataBtn(noteTitle.text.toString(), noteText.text.toString())
//            }
//        }
//        back_to_start_activity.also {
//            it.setOnClickListener {
//                defaultPresenter?.backToMainActivity()
//            }
//        }
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
    }

    /**
     * Действие при успешном сохранении
     *
     * @param title  заголовок заметки
     * @param text  тест заметки
     */
    override fun onSaveSuccess(title: String, text: String) {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_success) + " title:" + title)
//        listNotes.add(Note(notesList.size.toLong(), title, text, Date(), Date()))
        showToast(getString(R.string.msg_success), title)
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


    override fun showNoteFragment(note: Note, containerViewId:Int) {
        Log.d(TAG_NOTE_ACTIVITY, getString(R.string.msg_intent_frag) + " - note: $note")
        NoteFragment.newInstance(note).showDetails(supportFragmentManager, R.id.activity_main)
    }

}