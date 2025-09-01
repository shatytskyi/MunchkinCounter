package com.shatytskyi.munchcounter.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val lvl: Int,
    val items: Int,
    val gender: Gender = Gender.MALE
) : Serializable {

    init {
        require(name.isNotBlank()) { "Character name cannot be blank" }
        require(lvl >= 1) { "Character level must be at least 1" }
        require(lvl <= 999) { "Character level cannot exceed 999" }
        require(items >= -999) { "Character power cannot be less than -999" }
        require(items <= 999) { "Character power cannot exceed 999" }
    }

    val power: Int
        get() = lvl + items

    companion object {
        const val MIN_LEVEL = 1
        const val MAX_LEVEL = 999
        const val MIN_POWER = -999
        const val MAX_POWER = 999
        const val MAX_SCORE = 999
        
        fun createDefault(name: String, gender: Gender = Gender.MALE): Character {
            return Character(name = name.trim(), lvl = MIN_LEVEL, items = 0, gender = gender)
        }
        
        fun createMonster(power: Int = 0): Character {
            return Character(name = "Monster", lvl = MIN_LEVEL, items = power)
        }
    }

    fun withLevel(newLevel: Int): Character {
        val adjustedLevel = newLevel.coerceIn(MIN_LEVEL, MAX_LEVEL)
        val adjustedPower = if (adjustedLevel + items > MAX_SCORE) {
            MAX_SCORE - adjustedLevel
        } else {
            items
        }
        return copy(lvl = adjustedLevel, items = adjustedPower)
    }

    fun withPower(newPower: Int): Character {
        val totalScore = lvl + newPower
        val adjustedPower = if (totalScore > MAX_SCORE) {
            MAX_SCORE - lvl
        } else {
            newPower.coerceIn(MIN_POWER, MAX_POWER)
        }
        return copy(items = adjustedPower)
    }

    fun addLevel(delta: Int): Character {
        return withLevel(lvl + delta)
    }

    fun addPower(delta: Int): Character {
        return withPower(items + delta)
    }

    fun reset(): Character {
        return copy(lvl = MIN_LEVEL, items = 0)
    }

    fun isValidForCombat(): Boolean {
        return name.isNotBlank() && power > 0
    }
}
