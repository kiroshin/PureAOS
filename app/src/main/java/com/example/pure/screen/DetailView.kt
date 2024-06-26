/*
 * DetailView.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pure.AppState
import com.example.pure.LoadPersonUsecase
import com.example.pure.model.PersonIdType

@Composable
fun DetailView(appState: AppState, loadPersonAction: LoadPersonUsecase, target: PersonIdType) {
    val viewmodel: DetailViewModel = viewModel(factory = DetailViewModel.by(appState, loadPersonAction, target))

    Text(text = "Hello")
}