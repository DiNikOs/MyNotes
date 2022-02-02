package ru.dinikos.mynotes.mvp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.fragments.NoteFragment

class NotesPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {

    var items: MutableList<Note> = mutableListOf()
        set(value) {
            field = value
            itemIds = mutableListOf()
            for (item in items) {
                var id: Long? = -1
                if (item.noteId != null) {
                    id = item.noteId
                }
                id?.let { itemIds.add(it) }
            }
            notifyDataSetChanged()
        }

    private lateinit var note: Note

    private lateinit var itemIds: MutableList<Long>

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
    override fun getItemId(position: Int) = itemIds[position]

    /**
     * Получение соответствия Item для перехода после удаления записи.
     *
     * @param itemId
     * @return
     */
    override fun containsItem(itemId: Long) = itemIds.contains(itemId)
}