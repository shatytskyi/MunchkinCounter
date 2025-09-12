package com.shatytskyi.munchcounter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenderConverter::class)
abstract class MunchkinDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
