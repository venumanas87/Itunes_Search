package xyz.v.itunessearch.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ResultEntity::class),version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun searchDao():SearchDao
}