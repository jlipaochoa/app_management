package com.example.app_management.presentation.apps.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_management.R
import com.example.app_management.extentions.getInternalStorageData
import com.example.app_management.extentions.getRamData
import com.example.app_management.presentation.apps.OnLifecycleEvent

@Composable
fun MemoryAndStorageInfo(context: Context) {
    var totalRam by remember { mutableLongStateOf(context.getRamData().first) }
    var usageRam by remember { mutableLongStateOf(context.getRamData().second) }
    var totalInternal by remember { mutableLongStateOf(getInternalStorageData().first) }
    var usageInternal by remember { mutableLongStateOf(getInternalStorageData().second) }

    OnLifecycleEvent {
        totalRam = context.getRamData().first
        usageRam = context.getRamData().second
        totalInternal = getInternalStorageData().first
        usageInternal = getInternalStorageData().second
    }

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