package ru.dinikos.mynotes.mvp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.adapters.NotesRecyclerAdapter
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.DataPresenter
import ru.dinikos.mynotes.mvp.presenters.DataPresenterImpl
import ru.dinikos.mynotes.mvp.view.DataView

/**
 * A fragment representing a list of Items.
 */
class RecyclerFragment : Fragment(), ShowFragmentSupport, DataView {

    private lateinit var onClick: ((Note) -> Unit)
    private lateinit var onPosition: ((Int) -> Unit)
    private var dataPresenter: DataPresenter? = null
    private var database: AppDatabase? = null

    companion object {

        const val TAG_RECYCLER_FRAG = "RecyclerFragment TAG"

        @JvmStatic
        fun newInstance(onItemClick: ((Note) -> Unit), position: ((Int) -> Unit)) =
            RecyclerFragment().apply {
                onClick = onItemClick
                onPosition = position
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG_RECYCLER_FRAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        database = AppDatabase.getDataBase(this)
        dataPresenter = DataPresenterImpl(this, AppDatabase.getDataBase(this))
        var listNotes = dataPresenter?.getDates()
        database?.noteDao()?.insertNotes(listNotes!!)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)//
                lifecycleScope.launch {
                    dataPresenter?.getAll()?.collect {
                        view.adapter = NotesRecyclerAdapter(it, onClick, onPosition)
                    }
                }
            }
        }
        return view
    }

    /**
     * Замещает новыми значениями фрагмент
     *
     * @param manager
     */
    override fun showFragment(manager: FragmentManager) {
        val fragment: Fragment? = this
        if (fragment != null) {
            manager
                .beginTransaction()
                .replace(R.id.container_recycler, fragment)
                .commit()
        }
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