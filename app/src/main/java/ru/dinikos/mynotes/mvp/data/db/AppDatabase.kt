package ru.dinikos.mynotes.mvp.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import ru.dinikos.mynotes.mvp.data.entities.Note
import ru.dinikos.mynotes.mvp.data.repositories.NoteDao
import ru.dinikos.mynotes.mvp.fragments.NoteFragment
import ru.dinikos.mynotes.mvp.fragments.RecyclerFragment
import ru.dinikos.mynotes.mvp.view.BaseFragment

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var instanceDatabase: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            return instanceDatabase?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                ).allowMainThreadQueries().build()
                instanceDatabase = instance
                instance
            }
        }

        fun getDataBase(baseFragment: BaseFragment): AppDatabase? {
            return instanceDatabase?.let { it }
        }
    }
}