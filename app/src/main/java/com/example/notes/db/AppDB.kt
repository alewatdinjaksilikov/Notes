package com.example.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDB:RoomDatabase() {
    abstract fun notesDao(): NoteDao

    companion object{

        @Volatile
        private var INSTANCE: AppDB? =null

        fun getInstance(context: Context): AppDB {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDB::class.java,
                        "notes_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}