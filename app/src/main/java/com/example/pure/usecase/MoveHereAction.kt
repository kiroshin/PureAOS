/*
 * MoveHereAction.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.usecase

import com.example.pure.Vessel

suspend fun Vessel.moveHere(isLeg: Boolean, isWing: Boolean): String {
    val txtweb = personWebData.walk(isLeg)
    val txtdb = personDBWork.fly(isWing)
    return txtweb + txtdb
}