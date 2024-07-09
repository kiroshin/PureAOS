/*
 * InlineKeyValueText.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.show

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InlineKeyValueText(key: String, value: String, keyWidth: Int, height: Int, style: TextStyle) {
    Row(
        modifier = Modifier.height(height.dp).padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = key,
            style = style,
            modifier = Modifier
                .padding(8.dp)
                .width(keyWidth.dp)
        )
        Text(text = value,
            style = style,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun InlineKeyValueText_Preview() {
    InlineKeyValueText("KEY", "VALUE", (4 * 20), (4 * 14), MaterialTheme.typography.titleMedium)
}