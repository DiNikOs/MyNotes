package ru.dinikos.mynotes.mvp.data.repositories

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note_title LIKE :title LIMIT 1")
    suspend fun findByTitleName(title: String): Note

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note:Note)


}