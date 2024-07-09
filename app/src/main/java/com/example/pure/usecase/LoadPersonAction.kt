/*
 * LoadPersonAction.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.usecase

import com.example.pure.Vessel
import com.example.pure.model.*
import kotlinx.coroutines.flow.update

suspend fun Vessel.loadPerson(idnt: PersonIdType): Person {
    update { it.copy(route = it.route.copy(uis = idnt)) }
    return personDBWork.readPerson(idnt)
}