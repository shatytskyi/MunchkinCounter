package com.shatytskyi.munchcounter.data

enum class Gender(val displayName: String) {
    MALE("Male"),
    FEMALE("Female");

    companion object {
        fun fromString(value: String?): Gender {
            return when (value?.uppercase()) {
                "MALE" -> MALE
                "FEMALE" -> FEMALE
                else -> MALE // Default to MALE if not specified
            }
        }
    }
}