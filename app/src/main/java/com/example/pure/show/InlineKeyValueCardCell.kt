/*
 * InlineKeyValueCardCell.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.show

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InlineKeyValueCardCell(uid: String, key: String, value: String, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick(uid) },
        shape = RoundedCornerShape(4.dp)
    ) {
        InlineKeyValueText(key, value, (4 * 20), (4 * 12), MaterialTheme.typography.titleMedium)
    }
}


@Preview(showBackground = true)
@Composable
fun InlineKeyValueCardCell_Preview() {
    InlineKeyValueCardCell("UNIQU", "MAN", "Leonardo Dicaprio", { })
}
