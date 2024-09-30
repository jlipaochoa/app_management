package com.example.app_management.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_management.R
import com.example.app_management.extentions.getInternalStorageData
import com.example.app_management.extentions.getRamData

@Composable
fun MemoryAndStorageInfo(context: Context) {
    val (totalRam, usageRam) = remember { context.getRamData() }
    val (totalInternal, usageInternal) = remember { getInternalStorageData() }

    Column {
        ProgressBarRangeInfo(
            label = "RAM",
            icon = R.drawable.ram,
            total = totalRam,
            usage = usageRam,
            suffix = "MB"
        )
        Spacer(modifier = Modifier.height(12.dp))
        ProgressBarRangeInfo(
            label = "Memoria",
            icon = R.drawable.ssd,
            total = totalInternal,
            usage = usageInternal,
            suffix = "MB"
        )
    }
}