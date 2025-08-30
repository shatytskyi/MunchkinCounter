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
    val power: Int
) : Serializable {

    init {
        require(name.isNotBlank()) { "Character name cannot be blank" }
        require(lvl >= 1) { "Character level must be at least 1" }
        require(lvl <= 999) { "Character level cannot exceed 999" }
        require(power >= -999) { "Character power cannot be less than -999" }
        require(power <= 999) { "Character power cannot exceed 999" }
    }

    val score: Int
        get() = lvl + power

    companion object {
        const val MIN_LEVEL = 1
        const val MAX_LEVEL = 999
        const val MIN_POWER = -999
        const val MAX_POWER = 999
        const val MAX_SCORE = 999
        
        fun createDefault(name: String): Character {
            return Character(name = name.trim(), lvl = MIN_LEVEL, power = 0)
        }
        
        fun createMonster(power: Int = 0): Character {
            return Character(name = "Monster", lvl = MIN_LEVEL, power = power)
        }
    }

    fun withLevel(newLevel: Int): Character {
        val adjustedLevel = newLevel.coerceIn(MIN_LEVEL, MAX_LEVEL)
        val adjustedPower = if (adjustedLevel + power > MAX_SCORE) {
            MAX_SCORE - adjustedLevel
        } else {
            power
        }
        return copy(lvl = adjustedLevel, power = adjustedPower)
    }

    fun withPower(newPower: Int): Character {
        val totalScore = lvl + newPower
        val adjustedPower = if (totalScore > MAX_SCORE) {
            MAX_SCORE - lvl
        } else {
            newPower.coerceIn(MIN_POWER, MAX_POWER)
        }
        return copy(power = adjustedPower)
    }

    fun addLevel(delta: Int): Character {
        return withLevel(lvl + delta)
    }

    fun addPower(delta: Int): Character {
        return withPower(power + delta)
    }

    fun reset(): Character {
        return copy(lvl = MIN_LEVEL, power = 0)
    }

    fun isValidForCombat(): Boolean {
        return name.isNotBlank() && score > 0
    }
}