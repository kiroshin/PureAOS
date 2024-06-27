/*
 * HomeView.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pure.AppState
import com.example.pure.Roger
import com.example.pure.model.Person
import com.example.pure.model.PersonIdType
import com.example.pure.show.InlineKeyValueCardCell
import com.example.pure.util.UiState
import com.example.pure.util.onFailure
import com.example.pure.util.onSuccess
import com.example.pure.util.stored
import kotlinx.coroutines.flow.map

@Composable
fun HomeView(appState: AppState, launcher: LaunchViewBlock) {
    val rogerThat = remember { appState.stored { HomeThat(it) } }
    val isRegion by rogerThat.map { it.isRegion }.collectAsStateWithLifecycle(initialValue = true)
    val itemState by rogerThat.map { when (it.last) {
        Roger.Signal.SUCCESS -> { UiState.Success(it.metas.map { pm -> HomeItem(pm) }) }
        Roger.Signal.FAILURE -> { UiState.Failure("데이터를 로드할 수 없습니다.") }
        else -> { UiState.Ready }
    } }.collectAsStateWithLifecycle(initialValue = UiState.Ready)

    itemState.onSuccess {
        if (isRegion) {
            GroupList(it.groupBy { l -> l.region }, launcher)
        } else {
            PlainList(it, launcher)
        }
    }
    itemState.onFailure {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "웁스... ${it}")
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GroupList(articles: Map<String, List<HomeItem>>, next: LaunchViewBlock) {
    LazyColumn { articles.forEach { (group, its) ->
        stickyHeader {
            Text(text = group,
                color = Color.White,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(5.dp)
                    .fillMaxWidth())
        }
        items(its) {
            InlineKeyValueCardCell(uid = it.id, key = it.generation, value = it.nick, onClick = next)
        }
    } }
}

@Composable
private fun PlainList(articles: List<HomeItem>, next: LaunchViewBlock) {
    LazyColumn {
        items(articles) {
            InlineKeyValueCardCell(uid = it.id, key = it.generation, value = it.nick, onClick = next)
        }
    }
}


data class HomeThat(
    val last: Roger.Signal,
    val isRegion: Boolean,
    val metas: List<Person.Meta>
) {
    constructor(roger: Roger): this(
        last = roger.sys.last,
        isRegion = roger.field.isRegion,
        metas = roger.query.metas
    )
}


data class HomeItem(
    val id: PersonIdType,
    val nick: String,
    val age: Int,
    val region: String,
) {
    val generation: String get() = when (age) {
        in 0..<13 -> "KID"
        in 13..19 -> "TEEN"
        in 20..<40 -> "YOUTH"
        in 40..<60 -> "MIDDLE"
        in 60..<70 -> "SENIOR"
        else -> "OLD"
    }
    constructor(meta: Person.Meta): this(
        id = meta.id,
        nick = meta.name,
        age = meta.age,
        region = meta.country
    )
}

