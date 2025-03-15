package com.vn.whosthatdog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter

@Composable
fun ShowImage(painter: AsyncImagePainter) {
    Image(
        painter = painter,
        contentDescription = null,
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
    )
}
