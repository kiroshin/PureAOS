/*
 * HomeView.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pure.AppState
import com.example.pure.util.stored

@Composable
fun HomeView(appState: AppState, launchDetail: LaunchViewBlock) {
    val queryMetasState = remember { appState.stored { it.query.metas } }
    val queryMetas by queryMetasState.collectAsStateWithLifecycle(initialValue = listOf())
//    val coroutineScope = rememberCoroutineScope()

    Column {
        Button(onClick = { launchDetail("H") }) {
            Text(text = "버튼")
        }
    }
}
