/*
 * ApplyRegionAction.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.usecase

import com.example.pure.Vessel
import kotlinx.coroutines.flow.update

suspend fun Vessel.applyRegion(isRegion: Boolean): Unit {
    update { it.copy(field = it.field.copy(isRegion = isRegion)) }
}