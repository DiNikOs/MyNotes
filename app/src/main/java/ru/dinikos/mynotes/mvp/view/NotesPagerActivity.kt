package ru.dinikos.mynotes.mvp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.adapters.NotesPagerAdapter
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.*
import java.util.*

class NotesPagerActivity : AppCompatActivity(), DataView {

    private lateinit var adapter: NotesPagerAdapter
    private lateinit var viewPager: ViewPager2

    private var dataPresenter: DataPresenter? = null
    private var dataBase: AppDatabase? = null

    private var position: Int? = -1

    companion object {
        const val TAG_NOTE_ACTIVITY = "NotesPagerActivity"
        const val SELECTED_POSITION = "selectedPosition"

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
        dataBase = AppDatabase.getDataBase(this)
        dataBase?.let { dataPresenter = DataPresenterImpl(this, it) }
        viewPager = findViewById(R.id.view_pager)
        adapter = NotesPagerAdapter(this)
        position = intent.getIntExtra(SELECTED_POSITION, -1)
        dataPresenter?.onLoadAllNotes()
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
    }

    override fun onLoadAllNotes() {
        lifecycleScope.launch {
            dataPresenter?.getAll()?.collect {
                var list: MutableList<Note> = it.toMutableList()
                if(position == -1) {
                    position = 0
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
                position?.let { it -> viewPager.currentItem = it }
            }
        }
    }

}