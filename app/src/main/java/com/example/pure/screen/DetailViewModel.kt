/*
 * DetailViewModel.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pure.AppState
import com.example.pure.LoadPersonUsecase
import com.example.pure.model.Fizzle
import com.example.pure.model.Person
import com.example.pure.model.PersonIdType
import com.example.pure.util.UiState
import com.example.pure.util.stored
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DetailViewModel(appState: AppState,
                      loadPersonAction: LoadPersonUsecase,
                      destID: PersonIdType): ViewModel() {
    companion object {
        fun by(s: AppState, l: LoadPersonUsecase, t: PersonIdType) = viewModelFactory { initializer {
            DetailViewModel(s, l, t)
        } }
    }

    private val dataFlow = flowOf(destID)
        .map(loadPersonAction)
        .map { it.toItem() }
        .flowOn(Dispatchers.IO)

    val itemState = dataFlow
        .map { UiState.Success(it) as UiState<Item> }
        .catch { if (it is Fizzle) { emit(UiState.Failure(it.localizedMessage)) } }
    val isRegionState = appState.stored { it.field.isRegion }

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
