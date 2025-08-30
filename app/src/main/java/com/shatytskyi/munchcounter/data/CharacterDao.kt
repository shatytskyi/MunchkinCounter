package com.shatytskyi.munchcounter.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    
    @Query("SELECT * FROM characters ORDER BY id")
    fun getAllCharacters(): Flow<List<Character>>
    
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Long): Character?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
    
    @Update
    suspend fun updateCharacter(character: Character)
    
    @Delete
    suspend fun deleteCharacter(character: Character)
    
    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteCharacterById(id: Long)
    
    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
    
    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharacterCount(): Int
    
    // Batch operations for better performance
    @Query("UPDATE characters SET lvl = lvl + :delta WHERE id = :id")
    suspend fun updateLevel(id: Long, delta: Int)
    
    @Query("UPDATE characters SET power = power + :delta WHERE id = :id")
    suspend fun updatePower(id: Long, delta: Int)
    
    @Query("UPDATE characters SET lvl = 1, power = 0 WHERE id = :id")
    suspend fun resetCharacter(id: Long)
    
    @Query("UPDATE characters SET lvl = 1, power = 0")
    suspend fun resetAllCharacters()
    
    @Transaction
    suspend fun shuffleCharacters() {
        val characters = getAllCharactersSync()
        deleteAllCharacters()
        insertCharacters(characters.shuffled())
    }
    
    @Query("SELECT * FROM characters ORDER BY id")
    suspend fun getAllCharactersSync(): List<Character>
}