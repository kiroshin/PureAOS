/*
 * Working.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import com.example.pure.model.Person
import com.example.pure.model.PersonIdType

interface PersonWebWork {
    suspend fun getAllPerson(): List<Person>
    suspend fun walk(isLeg: Boolean): String
}


interface PersonDBWork {
    suspend fun readAllPersonMeta(): List<Person.Meta>
    suspend fun readPerson(id: PersonIdType): Person
    suspend fun updateManyPerson(persons: List<Person>)
    suspend fun fly(isWing: Boolean): String
}
