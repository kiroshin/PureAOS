/*
 * DetailViewModel.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pure.Serving
import com.example.pure.model.Fizzle
import com.example.pure.model.Person
import com.example.pure.model.PersonIdType
import com.example.pure.util.UiState
import com.example.pure.util.stored
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(service: Serving, destID: PersonIdType): ViewModel() {
    companion object {
        fun by(s: Serving, t: PersonIdType) = viewModelFactory { initializer {
            DetailViewModel(s, t)
        } }
    }

    private val loadPersonAction = service.loadPersonAction
    private val moveHereAction = service.moveHereAction

    val isRegionStored = service.appState.stored { it.field.isRegion }
    val itemStateFlow = flowOf(destID)
        .map(loadPersonAction)
        .map { it.toItem() }
        .flowOn(Dispatchers.IO)
        .map { UiState.Success(it) as UiState<Item> }
        .catch { if (it is Fizzle) { emit(UiState.Failure(it.localizedMessage)) } }
    val moveTextFlow = MutableStateFlow("MOVE")

    fun moveHere(isLeg: Boolean) { viewModelScope.launch {
        try {
            val isWing = moveTextFlow.value.length < 4
            moveTextFlow.emit(moveHereAction(isLeg, isWing))
        } catch (e: Fizzle) {
            moveTextFlow.emit(e.localizedMessage)
        }
    } }

    data class Item(
        var name: String,
        var username: String,
        var gender: String,
        var email: String,
        var age: String,
        var region: String,
        var phone: String,
        var photo: String
    )
}


private fun Person.toItem(): DetailViewModel.Item = DetailViewModel.Item(
    name = name,
    username = username,
    gender = gender.name,
    email = email,
    age = "${age}",
    region = country,
    phone = cellphone ?: "",
    photo = photo ?: ""
)
