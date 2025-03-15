package com.example.whosthatdog.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginAndSignButton(modifier: Modifier, text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), enabled = enabled) {
            Text(
                text = text
            )
    }
}