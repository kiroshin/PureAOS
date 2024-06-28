/*
 * DetailViewModel.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pure.AppState
import com.example.pure.LoadPersonUsecase
import com.example.pure.Serving
import com.example.pure.model.Fizzle
import com.example.pure.model.Person
import com.example.pure.model.PersonIdType
import com.example.pure.util.UiState
import com.example.pure.util.stored
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    val isRegionState = service.appState.stored { it.field.isRegion }
    val itemState = flowOf(destID)
        .map(loadPersonAction)
        .map { it.toItem() }
        .flowOn(Dispatchers.IO)
        .map { UiState.Success(it) as UiState<Item> }
        .catch { if (it is Fizzle) { emit(UiState.Failure(it.localizedMessage)) } }

//    init { viewModelScope.launch {
//    } }

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
