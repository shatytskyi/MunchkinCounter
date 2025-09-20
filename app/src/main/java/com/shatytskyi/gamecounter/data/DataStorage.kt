package com.shatytskyi.gamecounter.data

interface DataStorage {
    suspend fun saveCharacters(characters: List<Character>)
    suspend fun loadCharacters(): List<Character>
}
