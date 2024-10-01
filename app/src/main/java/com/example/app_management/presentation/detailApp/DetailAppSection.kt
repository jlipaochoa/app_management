package com.example.app_management.presentation.detailApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailAppSection() {
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        Column {

        }
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .width(1.dp)
                .height(100.dp)
        )
    }
}