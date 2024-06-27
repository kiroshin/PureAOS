/*
 * DetailView.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pure.AppState
import com.example.pure.LoadPersonUsecase
import com.example.pure.model.PersonIdType
import com.example.pure.show.ProfileImage
import com.example.pure.util.UiState
import com.example.pure.util.onFailure
import com.example.pure.util.onSuccess

@Composable
fun DetailView(appState: AppState, loadPersonAction: LoadPersonUsecase, target: PersonIdType) {
    val viewmodel: DetailViewModel = viewModel(factory = DetailViewModel.by(appState, loadPersonAction, target))
    val scrollState = rememberScrollState()
    val itemState by viewmodel.itemState.collectAsStateWithLifecycle(initialValue = UiState.Ready)
    val isRegion by viewmodel.isRegionState.collectAsStateWithLifecycle(initialValue = true)

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
    ) {
        itemState.onSuccess {
            Content(isRegion = isRegion, item = it)
        }
        itemState.onFailure {
            Text(text = "== 실패 : ${it} ==")
        }
        Spacer(modifier = Modifier.padding(8.dp))

    }
    //
}

@Composable
private fun Content(isRegion: Boolean, item: DetailViewModel.Item) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ProfileImage(url = item.photo, text = item.username)
    }

}




