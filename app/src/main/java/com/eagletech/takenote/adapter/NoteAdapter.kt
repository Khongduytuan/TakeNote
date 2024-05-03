package com.eagletech.takenote.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eagletech.takenote.R
import com.eagletech.takenote.model.Note

//class NoteAdapter {
//}

class NoteAdapter(
    private val context: Context,
    private val onClick: (Note)-> Unit

): RecyclerView.Adapter<NoteAdapter.NoteViewModel>() {
    private var notes: List<Note> = listOf()

    inner class NoteViewModel(itemView: View): RecyclerView.ViewHolder(itemView){
        private val noteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        private val noteBody: TextView = itemView.findViewById(R.id.tvNoteBody)
        //        private val circle_view: TextView = itemView.findViewById(R.id.circle_view)
        fun onBind(note: Note){
            noteTitle.text = note.noteTitle
            noteBody.text = note.noteBody
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewModel {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return NoteViewModel(itemView)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewModel, position: Int) {
        val note = notes[position]
        holder.itemView.setOnClickListener { onClick.invoke(note) }
        holder.onBind(notes[position])

    }

    fun setNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}