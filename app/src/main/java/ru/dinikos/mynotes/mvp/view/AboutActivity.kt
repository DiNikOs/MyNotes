package ru.dinikos.mynotes.mvp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_about.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.presenters.DefaultPresentImpl
import ru.dinikos.mynotes.mvp.presenters.DefaultPresenter

class AboutActivity : AppCompatActivity(), DefaultView  {

    private var defaultPresenter: DefaultPresenter? = null

    /**
     * Вызов при первом создании AboutActivity
     *
     * @param savedInstanceState  контекст для работы с Activity(ключ-значение)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        init()
    }

    /**
     * Инициализация view и описания взаимодействия с Presenter
     *
     */
    private fun init() {
        defaultPresenter = DefaultPresentImpl(this)
        backToStartActivity.also {
            it.setOnClickListener {
                defaultPresenter?.backToMainActivity()
            }
        }
    }

    /**
     * Вызов перед открытием Activity
     *
     */
    override fun onStart() {
        super.onStart()
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_on_start))
    }

    /**
     * Вызов перед предоставлением доступа к Activity
     *
     */
    override fun onResume() {
        super.onResume()
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_on_resume))
    }

    /**
     * Вызов когда Activity теряет фокус
     *
     */
    override fun onPause() {
        super.onPause()
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_on_pause))
    }

    /**
     * Вызов после удаления Activity с экрана
     *
     */
    override fun onStop() {
        super.onStop()
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_on_stop))
    }

    /**
     * Вызов перед уничтожением Activity
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_on_destroy))
        defaultPresenter = null
    }

    /**
     * Возврат к главному Activity
     *
     */
    override fun backToMainActivity() {
        Log.d(BaseView.TAG_ABOUT, getString(R.string.msg_backToMain))
        startActivity(Intent(this, MainActivityImpl::class.java))
    }
}