package com.example.app_management.presentation.apps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.app_management.presentation.detailApp.CustomDialog

@Composable
fun DialogAnalysis(
    showDialog: Boolean,
    description: String?,
    dismissDialog: () -> Unit = {},
) {
    CustomDialog(
        showDialog = showDialog,
        onDismissRequest = { dismissDialog() }
    ) {
        Box(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surface,
                )
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            LazyColumn() {
                item {
                    Text(description?:"", style = MaterialTheme.typography.bodyLarge)
                }
            }

        }
    }
}