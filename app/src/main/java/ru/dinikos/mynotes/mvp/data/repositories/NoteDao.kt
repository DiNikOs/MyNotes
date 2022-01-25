package ru.dinikos.mynotes.mvp.data.repositories
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.dinikos.mynotes.mvp.data.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun loadAll(): Flow<List<Note>>

    @Insert
    fun insert(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(listNote: List<Note>)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note:Note)

    @Delete
    fun deleteAll(listNote: List<Note>)

}