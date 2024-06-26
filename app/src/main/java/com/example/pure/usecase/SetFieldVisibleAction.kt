/*
 * SetFieldVisibleAction.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.usecase

import com.example.pure.Vessel
import kotlinx.coroutines.flow.update

suspend fun Vessel.setFieldVisible(isRegion: Boolean): Unit {
    println("* _setFieldVisibleAction : 대기")
//    delay(1000)
    println("* _setFieldVisibleAction : 발송")
    update { it.copy(field = it.field.copy(isRegion = isRegion)) }

}