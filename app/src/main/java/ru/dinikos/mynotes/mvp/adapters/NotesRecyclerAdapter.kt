package ru.dinikos.mynotes.mvp.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_main.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note

class NotesRecyclerAdapter(
    val listNotes: List<Note>,
    var onItemClick: ((Note) -> Unit)? = null
) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {

    override fun getItemCount(): Int = listNotes.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesRecyclerAdapter.NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recycler, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    inner class NotesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(note: Note) = with(containerView) {
            txtTitleNote.text = note.title
            txtTextNote.text = note.text
            txtCreateDateNote.text = note.createDate.toString()

            itemView.setOnClickListener {
                var position = adapterPosition
                Log.d("RecyclerAdapter",  " [adapterPosition: $position ]")
                onItemClick?.invoke(note)
            }
        }
    }
}