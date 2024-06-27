/*
 * LoadPersonAction.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.usecase

import com.example.pure.Vessel
import com.example.pure.model.*

suspend fun Vessel.loadPerson(idnt: PersonIdType): Person {
    return personDBWork.readPerson(idnt)
}