package com.example.app_management.presentation.detailApp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun PermissionCheck(label: String, isChecked: Boolean, modifier: Modifier) {
    val icon = if (isChecked) Icons.Filled.Lock else Icons.Filled.ArrowForward
    val color = if (isChecked) Color.Red else Color.White
    Row(modifier) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color
        )
        Spacer(Modifier.width(10.dp))
        Text(
            label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = color,
        )
    }
}