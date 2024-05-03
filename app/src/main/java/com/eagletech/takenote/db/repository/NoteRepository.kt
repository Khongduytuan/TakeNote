package com.eagletech.takenote.db.repository

import android.app.Application
import com.eagletech.takenote.db.NoteDatabase
import com.eagletech.takenote.db.dao.NoteDao
import com.eagletech.takenote.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(app: Application) {
    private val noteDao: NoteDao

    init {
        val noteDatabase: NoteDatabase = NoteDatabase.getInstance(app)
        noteDao = noteDatabase.getNoteDao()
    }


    suspend fun addNote(note: Note) =  noteDao.addNote(note)
    suspend fun updateNote(note: Note) =  noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) =  noteDao.deleteNote(note)

    fun getAllNote(): Flow<List<Note>> = noteDao.getAllNotes()
}