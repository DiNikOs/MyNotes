package ru.dinikos.mynotes.mvp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_main.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note


class NotesAdapter(
    val listNotes: List<Note>,
    val onItemClick: ((Note) -> Unit)? = null
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    override fun getItemCount(): Int {
        return listNotes.count()
    }

    inner class NotesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(note: Note) = with(containerView) {
            txtTitleNote.text = note.title
            txtTextNote.text = note.text
            txtCreateDateNote.text = note.createDate.toString()

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
}