package com.example.app_management.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app_management.ui.theme.DarkGrey40

@Composable
fun AnalysisSection() {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(DarkGrey40, shape = RoundedCornerShape(25.dp))
            .padding(start = 18.dp, bottom = 20.dp, end = 18.dp, top = 20.dp)
    ) {
        MemoryAndStorageInfo(context = LocalContext.current)
    }
}
