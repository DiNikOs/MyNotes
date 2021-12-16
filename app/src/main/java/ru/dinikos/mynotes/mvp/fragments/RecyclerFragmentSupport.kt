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

/**
 * A fragment representing a list of Items.
 */
class RecyclerFragmentSupport : Fragment(), ShowFragmentSupport {

    private lateinit var listNotes: MutableList<Note>
    private lateinit var onClick: ((Note) -> Unit)

    companion object {

        const val TAG_RECYCLER_FRAG = "RecyclerFragment TAG"

        @JvmStatic
        fun newInstance(list: MutableList<Note>, onItemClick: ((Note) -> Unit)) =
            RecyclerFragmentSupport().apply {
                listNotes = list
                onClick = onItemClick
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                view.adapter = NotesRecyclerAdapter(listNotes, onClick)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
}