/*
 * ContentView.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.pure.Serving
import com.example.pure.util.stored
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView(service: Serving) {
    val sysIsRegionState = remember { service.appState.stored { it.field.isRegion } }
    val setFieldVisibleAction = remember { service.setFieldVisibleAction }

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val isRegion by sysIsRegionState.collectAsStateWithLifecycle(initialValue = true)
    var navTitle: String by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = navTitle, style = MaterialTheme.typography.headlineLarge) },
                actions = {
                    Text(text = "${isRegion}", modifier = Modifier.padding(horizontal = 8.dp))
                    Switch(checked = isRegion, onCheckedChange = {
                        coroutineScope.launch {
                            setFieldVisibleAction(it)
                        }
                    } )
                }
            ) },
        content = {
            ContentNavHost(
                service = service,
                navController = navController,
                modifier = Modifier.padding(it),
                onTitleChange = { title ->
                    navTitle = title
                }
            )
        }
    )
}

