package com.qorexdev.notesapp.data.repository

import androidx.lifecycle.LiveData
import com.qorexdev.notesapp.data.local.NoteDao
import com.qorexdev.notesapp.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun getAllNotesByCreatedDesc(): LiveData<List<Note>> = noteDao.getAllNotesByCreatedDesc()

    fun getAllNotesByCreatedAsc(): LiveData<List<Note>> = noteDao.getAllNotesByCreatedAsc()

    fun searchNotes(query: String): LiveData<List<Note>> = noteDao.searchNotes(query)

    suspend fun getNoteById(id: Long): Note? = noteDao.getNoteById(id)

    suspend fun insert(note: Note): Long = noteDao.insert(note)

    suspend fun update(note: Note) = noteDao.update(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun deleteById(id: Long) = noteDao.deleteById(id)
}
