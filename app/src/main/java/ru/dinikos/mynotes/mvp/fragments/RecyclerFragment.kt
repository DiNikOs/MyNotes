package ru.dinikos.mynotes.mvp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.adapters.NotesRecyclerAdapter
import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.view.BaseView

/**
 * A fragment representing a list of Items.
 */
class RecyclerFragment : Fragment(), ShowFragment {

    private var columnCount = 1

    private var listNotes: MutableList<Note>? = null

    companion object {

        val TAG_RECYCLER_FRAG = RecyclerFragment:: class.java.name + " TAG"
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(list: MutableList<Note>) =
            RecyclerFragment().apply {
                listNotes = list
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, list.size)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d(TAG_RECYCLER_FRAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  LinearLayoutManager(context)
                adapter = NotesRecyclerAdapter(listNotes!!, context as BaseView)
            }
        }
        return view
    }

    /**
     * Замещает новыми значениями фрагмент
     *
     * @param manager
     * @param containerViewId
     */
    override fun showFragment(manager: FragmentManager, containerViewId: Int) {
        val fragment: Fragment? = this
        if (fragment != null) {
            manager
                .beginTransaction()
                .replace(containerViewId, fragment)
                .commit()
        }
    }
}