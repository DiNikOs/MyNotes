package ru.dinikos.mynotes.mvp.adapters

import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.fragments.NoteFragment

class NotesPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {

    var items: MutableList<Note> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var note:Note

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        note = items[position]
        return NoteFragment.newInstance(note)
    }

    /**
     * Получение itemId
     * (костыль для корректного отображения элементов после удаления из бд записи)
     *
     * @param position
     * @return если элемент удалён то вернётся -1
     */
    override fun getItemId(position: Int): Long {
        var itemId = items[position].noteId
        if (itemId == null) return RecyclerView.NO_ID
        return itemId
    }

    /**
     * Получение соответствия Item для перехода после удаления записи.
     *
     * @param itemId
     * @return
     */
    override fun containsItem(itemId: Long): Boolean {
        Log.d(TAG,"containsItem: itemId:$itemId, note: $note")
        return items.contains(note)
    }
}