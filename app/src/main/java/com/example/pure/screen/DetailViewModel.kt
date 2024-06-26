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
import com.example.pure.model.PersonIdType
import kotlinx.coroutines.launch

class DetailViewModel(private val appState: AppState,
                      private val loadPersonAction: LoadPersonUsecase,
                      destID: PersonIdType): ViewModel() {
    companion object {
        fun by(s: AppState, l: LoadPersonUsecase, t: PersonIdType) = viewModelFactory { initializer {
            DetailViewModel(s, l, t)
        } }
    }

    val target = destID

    init {
        println("* INIT DetailViewModel : ${appState}")
        viewModelScope.launch {
            val value = loadPersonAction("코코코")
            println("* 받은값: ${value}")
        }
    }
}
