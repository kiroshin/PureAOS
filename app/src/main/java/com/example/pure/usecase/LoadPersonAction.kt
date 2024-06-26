/*
 * LoadPersonAction.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.usecase

import com.example.pure.Vessel
import com.example.pure.model.*
import kotlinx.coroutines.delay

suspend fun Vessel.loadPerson(name: PersonIdType): Person {
    println("* _loadPersonAction : 대기")
    delay(2000)

    println("* _loadPersonAction : 발송")
    val person = Person("ABCD", "Jane", "jjnn", Gender.FEMALE, "jn@abc.com", 19, "KO", "010-1111-2222", "https://randomuser.me/api/portraits/women/5.jpg")
    return person
}