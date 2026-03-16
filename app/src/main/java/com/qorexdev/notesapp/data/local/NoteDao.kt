package com.qorexdev.notesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.qorexdev.notesapp.data.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY isPinned DESC, updatedAt DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY isPinned DESC, createdAt DESC")
    fun getAllNotesByCreatedDesc(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY isPinned DESC, createdAt ASC")
    fun getAllNotesByCreatedAsc(): LiveData<List<Note>>

    @Query(
        "SELECT * FROM notes WHERE title LIKE '%' || :query || '%' " +
        "OR content LIKE '%' || :query || '%' " +
        "ORDER BY isPinned DESC, updatedAt DESC"
    )
    fun searchNotes(query: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Long)
}
