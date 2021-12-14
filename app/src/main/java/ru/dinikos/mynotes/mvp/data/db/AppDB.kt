package ru.dinikos.mynotes.mvp.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import ru.dinikos.mynotes.mvp.data.entities.Note

@Database(entities = [Note::class], version = 1)
class AppDB: RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE_DB: AppDB? = null

        fun getDataBase(context: Context): AppDB {
            return INSTANCE_DB?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "notes"
                ).build()
                INSTANCE_DB = instance
                instance
            }
        }
    }


    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}