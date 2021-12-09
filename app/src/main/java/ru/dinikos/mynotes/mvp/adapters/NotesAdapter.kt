package ru.dinikos.mynotes.mvp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_about.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import ru.dinikos.mynotes.R
import ru.dinikos.mynotes.mvp.entities.Note
import ru.dinikos.mynotes.mvp.presenter.BasePresenter
import ru.dinikos.mynotes.mvp.presenter.StartPresenter
import ru.dinikos.mynotes.mvp.view.BaseView
import ru.dinikos.mynotes.mvp.view.InfoNoteFrag


class NotesAdapter(
    val listNotes: List<Note>,
    val onItemClick: ((Note) -> Unit)? = null,
    val context: BaseView
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var startPresent: BasePresenter = StartPresenter(context)

    override fun getItemCount(): Int {
        return listNotes.count()
    }

    inner class NotesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(note: Note) = with(containerView) {
            txtTitleNote.text = note.title

            itemView.setOnClickListener {
                var position = adapterPosition
                Log.d("Adapter",  " [adapterPosition: $position ]")
                startPresent?.showFragment(note, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
}