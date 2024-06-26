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
    val setFieldVisibleAction: SetFieldVisibleUsecase
}


typealias LoadPersonUsecase = suspend (PersonIdType) -> Person
typealias SetFieldVisibleUsecase = suspend (Boolean) -> Unit


