/*
 * Person.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.model

typealias PersonIdType = String

data class Person(
    val id: PersonIdType,
    var name: String,
    var username: String,
    var gender: Gender,
    var email: String,
    var age: Int,
    var country: String,
    var cellphone: String?,
    var photo: String?
) {
    fun toMeta() = Meta(id, name, country)

    data class Meta (
        val id: PersonIdType,
        val name: String,
        val country: String
    )
}

enum class Gender {
    UNKNOWN, MALE, FEMALE
}
