package ru.dinikos.mynotes.mvp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.fragments.RecyclerFragment
import ru.dinikos.mynotes.mvp.presenters.DataPresenter
import ru.dinikos.mynotes.mvp.presenters.DataPresenterImpl

open class BaseFragment : Fragment(), DataView {

    var dataPresenter: DataPresenter? = null
    var dataBase: AppDatabase? = null

    companion object {
        const val TAG_FRAG = "BaseFragment TAG"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBase = AppDatabase.getDataBase(this)
        dataBase?.let { dataPresenter = DataPresenterImpl(this, it) }
        Log.d(RecyclerFragment.TAG_RECYCLER_FRAG, "onCreateView")
        return null
    }

    override fun onLoadAllNotes() {
        Log.d(TAG_FRAG, "getDates BaseClass.")
    }
}