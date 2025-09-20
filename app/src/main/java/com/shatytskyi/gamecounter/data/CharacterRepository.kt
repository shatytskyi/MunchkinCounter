package com.shatytskyi.gamecounter.data

import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val characterDao: CharacterDao
) {
    val characters: Flow<List<Character>> = characterDao.getAllCharacters()

    private var tempCharacters: List<Character>? = null

    suspend fun addCharacter(name: String, gender: Gender = Gender.MALE) {
        val newCharacter = Character.createDefault(name, gender)
        characterDao.insertCharacter(newCharacter)
    }

    suspend fun removeCharacter(id: Long) {
        characterDao.deleteCharacterById(id)
    }

    suspend fun updateCharacter(id: Long, name: String, level: Int, power: Int, gender: Gender) {
        val character = characterDao.getCharacterById(id)
        if (character != null) {
            val updatedCharacter =
                character.copy(name = name, level = level, items = power, gender = gender)
            characterDao.updateCharacter(updatedCharacter)
        }
    }

    suspend fun resetCharacter(id: Long) {
        characterDao.resetCharacter(id)
    }

    suspend fun resetAllCharacters() {
        characterDao.resetAllCharacters()
    }

    suspend fun removeAllCharacters() {
        // Создаем backup перед удалением
        tempCharacters = characterDao.getAllCharactersSync()
        characterDao.deleteAllCharacters()
    }

    suspend fun changePower(id: Long, value: Int) {
        val character = characterDao.getCharacterById(id)
        if (character != null) {
            val newPower =
                (character.items + value).coerceIn(Character.MIN_POWER, Character.MAX_POWER)
            val adjustedValue = newPower - character.items
            if (adjustedValue != 0) {
                characterDao.updatePower(id, adjustedValue)
            }
        }
    }

    suspend fun changeLevel(id: Long, value: Int) {
        // Проверяем ограничения
        val character = characterDao.getCharacterById(id)
        if (character != null) {
            val newLevel =
                (character.level + value).coerceIn(Character.MIN_LEVEL, Character.MAX_LEVEL)
            val adjustedValue = newLevel - character.level
            if (adjustedValue != 0) {
                characterDao.updateLevel(id, adjustedValue)
            }
        }
    }
}
