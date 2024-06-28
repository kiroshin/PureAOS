/*
 * ProfileImage.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.show

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pure.R

@Composable
fun ProfileImage(url: String, text: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .border(1.dp, Color.White, CircleShape)
            .then(modifier)
        ,
        model = url,
        contentDescription = text,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentScale = ContentScale.FillBounds
    )
}


@Preview(showBackground = true)
@Composable
fun ProfileImage_Preview() {
    ProfileImage("https://picsum.photos/500", "Sample")
}