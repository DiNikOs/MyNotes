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
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.presenters.DataPresenter
import ru.dinikos.mynotes.mvp.presenters.DataPresenterImpl

/**
 * A fragment representing a list of Items.
 */
class RecyclerFragment : Fragment(), ShowFragmentSupport {

    private lateinit var onClick: ((Note) -> Unit)
    private var dataPresenter: DataPresenter? = null

    companion object {

        const val TAG_RECYCLER_FRAG = "RecyclerFragment TAG"

        @JvmStatic
        fun newInstance(onItemClick: ((Note) -> Unit)) =
            RecyclerFragment().apply {
                onClick = onItemClick
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d(TAG_RECYCLER_FRAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        dataPresenter = DataPresenterImpl()
        val list: List<Note>? = dataPresenter?.getDates()
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  LinearLayoutManager(context)
                view.adapter = list?.let { NotesRecyclerAdapter(it, onClick) }
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

}