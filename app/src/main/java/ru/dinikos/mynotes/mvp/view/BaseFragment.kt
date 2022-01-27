package ru.dinikos.mynotes.mvp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.db.AppDatabase
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.fragments.RecyclerFragment
import ru.dinikos.mynotes.mvp.presenters.DataPresenter
import ru.dinikos.mynotes.mvp.presenters.DataPresenterImpl

open class BaseFragment : Fragment(), DataView {

    var dataPresenter: DataPresenter? = null
    var dateBase: AppDatabase? = null
    var listNotes: MutableList<Note> = mutableListOf()

    companion object {

        const val TAG_FRAG = "BaseFragment TAG"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dateBase = AppDatabase.getDataBase(this)
        dataPresenter = DataPresenterImpl(this, dateBase)
        Log.d(RecyclerFragment.TAG_RECYCLER_FRAG, "onCreateView")
        return null
    }

    override suspend fun setDate(list: MutableList<Note>) {
        for (listNote in list) {
            listNotes.add(listNote)
        }
    }

    override fun onLoadAllNotes() {
        Log.d(TAG_FRAG, "getDates")
    }

    override suspend fun loadAllNotes(): Flow<List<Note>>? {
        return dateBase?.noteDao()?.loadAll()
    }

    override fun insertNote(note: Note): Long? {
        return dateBase?.noteDao()?.insert(note)
    }

    override fun insertNotes(listNote: List<Note>) {
        dateBase?.noteDao()?.insertNotes(listNote)
    }

    override fun updateNote(note: Note) {
        dateBase?.noteDao()?.update(note)
    }

    override fun deleteNote(note: Note) {
        dateBase?.noteDao()?.delete(note)
    }

    override fun deleteAllNote(listNote: List<Note>) {
        dateBase?.noteDao()?.deleteAll(listNote)
    }
}