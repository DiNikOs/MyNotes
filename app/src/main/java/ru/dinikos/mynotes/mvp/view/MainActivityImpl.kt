package ru.dinikos.mynotes.mvp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.databinding.ActivityMainBinding
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.data.repositories.RepositoryNotes
import ru.dinikos.mynotes.mvp.fragments.RecyclerFragment
import ru.dinikos.mynotes.mvp.presenters.*

import ru.dinikos.mynotes.mvp.view.MainView.Companion.TAG_MAIN_VIEW

class MainActivityImpl : AppCompatActivity(), MainView, DataView {

    private lateinit var binding: ActivityMainBinding

    private var mainPresent: MainPresenter? = null
    private var dataPresenter: DataPresenter? = null
    private var dataBase: AppDatabase? = null
    private var repository: RepositoryNotes = RepositoryNotes
    private var listNotes: MutableList<Note> = repository.getTestListNotes(10)

    /**
     * Вызов при первом создании Activity
     *
     * @param savedInstanceState  контекст для работы с Activity(ключ-значение)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            recreate()
        }
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
        dataBase = AppDatabase.getDataBase(this)
        dataBase?.let { dataPresenter = DataPresenterImpl(this, it) }
        dataPresenter?.onLoadAllNotes()
        mainPresent = MainMenuPresenter(this)
        showRecyclerFragment()
        binding.toolbarBtnAbout.setOnClickListener {
            mainPresent?.operateAboutBtn()
        }
        binding.toolbarBtnAddNote.setOnClickListener {
            mainPresent?.openPagerViewBtn()
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
        mainPresent = null
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
        NotesPagerActivity.startNotePagerActivity(this, position)
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

    override fun onLoadAllNotes() {
        dataPresenter?.insertNotes(listNotes)
    }

}

