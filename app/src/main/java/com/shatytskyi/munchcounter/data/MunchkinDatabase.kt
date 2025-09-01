package com.shatytskyi.munchcounter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Character::class],
    version = 2,
    exportSchema = false // Поскольку мы не поддерживаем миграции
)
@TypeConverters(GenderConverter::class)
abstract class MunchkinDatabase : RoomDatabase() {
    
    abstract fun characterDao(): CharacterDao
    
    companion object {
        @Volatile
        private var INSTANCE: MunchkinDatabase? = null
        
        fun getDatabase(context: Context): MunchkinDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MunchkinDatabase::class.java,
                    "munchkin_database"
                )
                .fallbackToDestructiveMigration() // ВАЖНО: НЕ поддерживаем миграции
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        // Для тестирования - создание in-memory database
        fun createInMemoryDatabase(context: Context): MunchkinDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                MunchkinDatabase::class.java
            ).build()
        }
    }
}