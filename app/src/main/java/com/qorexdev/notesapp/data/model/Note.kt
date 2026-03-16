package com.qorexdev.notesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val color: Int = COLOR_DEFAULT,
    val isPinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val COLOR_DEFAULT = 0
        const val COLOR_RED = 1
        const val COLOR_ORANGE = 2
        const val COLOR_YELLOW = 3
        const val COLOR_GREEN = 4
        const val COLOR_BLUE = 5
        const val COLOR_PURPLE = 6
    }
}
