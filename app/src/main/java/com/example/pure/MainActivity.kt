/*
 * MainActivity.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.pure.screen.ContentView
import com.example.pure.theme.PureTheme

class MainActivity : ComponentActivity() {
    lateinit var vessel: Vessel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            vessel = Vessel(LocalContext.current.applicationContext)
            PureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView(vessel)
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PureTheme {
//        ContentView(Raft)
//    }
//}