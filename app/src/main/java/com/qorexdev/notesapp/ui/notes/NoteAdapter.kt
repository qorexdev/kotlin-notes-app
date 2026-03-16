package com.qorexdev.notesapp.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qorexdev.notesapp.R
import com.qorexdev.notesapp.data.model.Note
import com.qorexdev.notesapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter(
    private val onNoteClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                textTitle.text = note.title.ifBlank { "Untitled" }
                textContent.text = note.content
                textDate.text = formatDate(note.updatedAt)

                iconPin.visibility = if (note.isPinned) View.VISIBLE else View.GONE

                val colorRes = getColorResource(note.color)
                colorStrip.setBackgroundColor(
                    ContextCompat.getColor(root.context, colorRes)
                )

                root.setOnClickListener { onNoteClick(note) }
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }

        private fun getColorResource(color: Int): Int {
            return when (color) {
                Note.COLOR_RED -> R.color.note_red
                Note.COLOR_ORANGE -> R.color.note_orange
                Note.COLOR_YELLOW -> R.color.note_yellow
                Note.COLOR_GREEN -> R.color.note_green
                Note.COLOR_BLUE -> R.color.note_blue
                Note.COLOR_PURPLE -> R.color.note_purple
                else -> R.color.note_default
            }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
