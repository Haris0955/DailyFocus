package com.example.dailyfocus.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daily_focus_db"
                )
//                    .fallbackToDestructiveMigration() // Optional for simpler future migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

        // Migration logic to handle version upgrade

/*
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Rename the old table
                database.execSQL("ALTER TABLE tasks RENAME TO tasks_old")

                // Create the new table with the correct schema
                database.execSQL(
                    """
           CREATE TABLE tasks (
    id INTEGER PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    priority INTEGER NOT NULL,
    dueDate INTEGER,
    isCompleted INTEGER NOT NULL DEFAULT 0
)
            """.trimIndent()
                )

                // Copy the data from the old table to the new table
                database.execSQL(
                    """
            INSERT INTO tasks (id, title, description, priority, dueDate, isCompleted)
            SELECT id, title, description, priority, dueDate, 
                   CASE WHEN completed IS NOT NULL THEN completed ELSE 0 END AS isCompleted
            FROM tasks_old
            """.trimIndent()
                )

                // Drop the old table
                database.execSQL("DROP TABLE tasks_old")
            }
        }
*/


