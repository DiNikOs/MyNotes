package ru.dinikos.mynotes.mvp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.*
import ru.dinikos.mynotes.R

class InfoNoteFrag : Fragment(){

    private lateinit var titleNoteTxt: TextView
    private lateinit var textNoteTxt: TextView
    private lateinit var createDateNoteTxt: TextView

    companion object {
        val TAG_MAIN_FRAG = InfoNoteFrag:: class.java.name + " TAG"

        fun newInstance(onInfoNote: (() -> Unit)): InfoNoteFrag {
            return InfoNoteFrag().apply {
                this.onInfoNote = onInfoNote
            }
        }
    }

    var onInfoNote: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_main, container, false)
//        titleNoteTxt = view.txtTextNote
//        textNoteTxt = view.txtTextNote
//        createDateNoteTxt = view.txtCreateDateNote
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG_MAIN_FRAG, "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG_MAIN_FRAG, "onActivityCreated")

    }

    fun showDetails(manager: FragmentManager, titleNote:String, textNote:String, createDateNote:String) {
        titleNoteTxt.text = titleNote
        textNoteTxt.text = textNote
        createDateNoteTxt.text = createDateNote
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.add(this, tag)
        ft.commit()
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

