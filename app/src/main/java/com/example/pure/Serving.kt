/*
 * Serving.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import com.example.pure.model.Person
import com.example.pure.model.PersonIdType

interface Serving {
    val appState: AppState
    val loadPersonAction: LoadPersonUsecase
    val applyRegionAction: ApplyRegionUsecase
    val moveHereAction: MoveHereUsecase
}

typealias LoadPersonUsecase = suspend (PersonIdType) -> Person
typealias ApplyRegionUsecase = suspend (Boolean) -> Unit
typealias MoveHereUsecase = suspend (Boolean, Boolean) -> String

