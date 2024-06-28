/*
 * DetailView.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pure.Serving
import com.example.pure.model.PersonIdType
import com.example.pure.show.InlineKeyValueText
import com.example.pure.show.ProfileImage
import com.example.pure.util.UiState
import com.example.pure.util.onFailure
import com.example.pure.util.onReadyOrLoading
import com.example.pure.util.onSuccess

@Composable
fun DetailView(service: Serving, target: PersonIdType) {
    val viewmodel: DetailViewModel = viewModel(factory = DetailViewModel.by(service, target))
    val scrollState = rememberScrollState()
    val isRegion by viewmodel.isRegionStored.collectAsStateWithLifecycle(initialValue = true)
    val itemState by viewmodel.itemStateFlow.collectAsStateWithLifecycle(initialValue = UiState.Ready)
    val moveText by viewmodel.moveTextFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
    ) {
        itemState.onSuccess {
            Content(isRegion = isRegion, item = it, moveText, viewmodel::moveHere)
        }
        itemState.onFailure {
            Text(text = "== 실패 : ${it} ==")
        }
        itemState.onReadyOrLoading {
            Text(text = "아직...")
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
    //
}


@Composable
private fun Content(isRegion: Boolean, item: DetailViewModel.Item, btnText:String, btnAction: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ProfileImage(url = item.photo, text = item.username)
    }
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
    SectionRow(item, isRegion)
    Button(
        onClick = { btnAction(isRegion) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = btnText)
    }
    //
}

@Composable
private fun SectionRow(item: DetailViewModel.Item, isRegion: Boolean) {
    val height = 4 * 12
    val keyWidth = 4 * 20
    val textStyle = MaterialTheme.typography.titleMedium
    val dividerModifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .padding(horizontal = 8.dp)

    Text(text = "Information", modifier = Modifier.padding(start = 16.dp))
    Column(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clip(shape = RoundedCornerShape(16.dp))
        .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        InlineKeyValueText("Name", item.name, keyWidth, height, textStyle)
        Divider(color = Color.LightGray, modifier = dividerModifier)
        InlineKeyValueText("Nick", item.username, keyWidth, height, textStyle)
        Divider(color = Color.LightGray, modifier = dividerModifier)
        InlineKeyValueText("Email", item.email, keyWidth, height, textStyle)
        Divider(color = Color.LightGray, modifier = dividerModifier)
        InlineKeyValueText("Age", item.age, keyWidth, height, textStyle)
        Divider(color = Color.LightGray, modifier = dividerModifier)
        if (isRegion) {
            InlineKeyValueText("Region", item.region, keyWidth, height, textStyle)
            Divider(color = Color.LightGray, modifier = dividerModifier)
        }
        InlineKeyValueText("Phone", item.phone, keyWidth, height, textStyle)
    }
}

