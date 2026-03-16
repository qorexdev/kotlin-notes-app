package com.qorexdev.notesapp.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.qorexdev.notesapp.data.local.NoteDatabase
import com.qorexdev.notesapp.data.model.Note
import com.qorexdev.notesapp.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    private val _searchQuery = MutableLiveData("")
    private val _sortOrder = MutableLiveData(SortOrder.UPDATED_DESC)

    val notes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)

        notes = MediatorLiveData<List<Note>>().apply {
            var querySource: LiveData<List<Note>>? = null

            fun update() {
                val query = _searchQuery.value ?: ""
                val sort = _sortOrder.value ?: SortOrder.UPDATED_DESC

                querySource?.let { removeSource(it) }

                val newSource = if (query.isNotBlank()) {
                    repository.searchNotes(query)
                } else {
                    when (sort) {
                        SortOrder.UPDATED_DESC -> repository.getAllNotes()
                        SortOrder.CREATED_DESC -> repository.getAllNotesByCreatedDesc()
                        SortOrder.CREATED_ASC -> repository.getAllNotesByCreatedAsc()
                    }
                }

                querySource = newSource
                addSource(newSource) { value = it }
            }

            addSource(_searchQuery) { update() }
            addSource(_sortOrder) { update() }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    enum class SortOrder {
        UPDATED_DESC,
        CREATED_DESC,
        CREATED_ASC
    }
}
