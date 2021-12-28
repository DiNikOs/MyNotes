package ru.dinikos.mynotes.mvp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.fragments.NoteFragment

class NotesPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {

    var items: List<Note> = emptyList()

    lateinit var note:Note

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        note = items[position]
        return NoteFragment.newInstance(note)
    }
}