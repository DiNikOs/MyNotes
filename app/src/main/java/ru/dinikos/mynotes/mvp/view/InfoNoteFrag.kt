package ru.dinikos.mynotes.mvp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note

class InfoNoteFrag : Fragment() {

    companion object {
        val TAG_MAIN_FRAG = InfoNoteFrag:: class.java.name + " TAG"

        val ARG_TITLE = "titleNoteTxt"
        val ARG_TEXT = "textNoteTxt"
        val ARG_CREATE_DATE = "createDateNoteTxt"

        fun newInstance(note: Note): InfoNoteFrag {
            val fragment = InfoNoteFrag()
            fragment.arguments = Bundle().apply {
                putString(ARG_TITLE, note.title)
                putString(ARG_TEXT, note.text)
                putString(ARG_CREATE_DATE, note.createDate.toString())
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    /**
     * Заполение формы фрагмента
     *
     * @param itemView  представление
     * @param savedInstanceState  бандлы
     */
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        itemView.txtTitleNote.text = arguments?.getString(ARG_TITLE)
        itemView.txtTextNote.text = arguments?.getString(ARG_TEXT)
        itemView.txtCreateDateNote.text = arguments?.getString(ARG_CREATE_DATE)
        super.onViewCreated(itemView, savedInstanceState)
        Log.d(TAG_MAIN_FRAG, "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG_MAIN_FRAG, "onActivityCreated")
    }

    /**
     * Замещает новыми значениями фрагмент
     *
     * @param manager
     * @param containerViewId
     */
    fun showDetails(manager: FragmentManager, containerViewId: Int) {
        val fragment: Fragment? = this
        if (fragment != null) {
            manager
                .beginTransaction()
                .replace(containerViewId, fragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_MAIN_FRAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_MAIN_FRAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG_MAIN_FRAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_MAIN_FRAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG_MAIN_FRAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_MAIN_FRAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG_MAIN_FRAG, "onDetach")
    }

}

