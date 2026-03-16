package com.qorexdev.notesapp.ui.editor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qorexdev.notesapp.data.local.NoteDatabase
import com.qorexdev.notesapp.data.model.Note
import com.qorexdev.notesapp.data.repository.NoteRepository
import kotlinx.coroutines.launch

class EditorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    var selectedColor: Int = Note.COLOR_DEFAULT
    var isPinned: Boolean = false

    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)
    }

    fun loadNote(id: Long) {
        if (id == -1L) {
            _note.value = null
            return
        }
        viewModelScope.launch {
            val loaded = repository.getNoteById(id)
            loaded?.let {
                selectedColor = it.color
                isPinned = it.isPinned
            }
            _note.postValue(loaded)
        }
    }

    fun saveNote(noteId: Long, title: String, content: String) {
        if (title.isBlank() && content.isBlank()) {
            _saved.value = true
            return
        }

        viewModelScope.launch {
            val now = System.currentTimeMillis()
            if (noteId == -1L) {
                repository.insert(
                    Note(
                        title = title,
                        content = content,
                        color = selectedColor,
                        isPinned = isPinned,
                        createdAt = now,
                        updatedAt = now
                    )
                )
            } else {
                val existing = repository.getNoteById(noteId)
                if (existing != null) {
                    repository.update(
                        existing.copy(
                            title = title,
                            content = content,
                            color = selectedColor,
                            isPinned = isPinned,
                            updatedAt = now
                        )
                    )
                }
            }
            _saved.postValue(true)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteById(noteId)
            _saved.postValue(true)
        }
    }
}
