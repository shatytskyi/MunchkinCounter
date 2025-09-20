package com.shatytskyi.gamecounter.data

import androidx.room.TypeConverter

class GenderConverter {
    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name
    }

    @TypeConverter
    fun toGender(genderString: String): Gender {
        return Gender.fromString(genderString)
    }
}
