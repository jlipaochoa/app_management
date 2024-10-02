package com.example.app_management.presentation.apps.components

import androidx.compose.foundation.background
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_management.domain.models.AppInfoAnalysis
import com.example.app_management.domain.models.AnalysisTotalInfo
import com.example.app_management.presentation.detailApp.FullScreenCustomDialog
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DialogAnalysis(
    showDialog: Boolean,
    filteredItems: List<AppInfoAnalysis>?,
    dismissDialog: () -> Unit = {},
    viewModel: DialogAnalysisViewModel = hiltViewModel()
) {

    val analysisTotalInfo: AnalysisTotalInfo by viewModel.analysisTotalInfo.collectAsState()
    viewModel.getAnalysisTotalInfo(filteredItems ?: listOf())
    FullScreenCustomDialog(
        showDialog = showDialog,
        onDismissRequest = { dismissDialog() }
    ) {
        LazyColumn(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surface,
                )
                .fillMaxWidth()
                .padding(20.dp)

        ) {

            item {
                Text("Resumen de analisis", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Estas apps son las más pesadas, revisa si es necesario tenerlas:\n",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(analysisTotalInfo.descriptionSize, style = MaterialTheme.typography.titleSmall)
                Text(
                    "Estas apps son las has usado poco en estos ultimos dias, considera eliminarlas:\n",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    analysisTotalInfo.descriptionUsage,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    "Estas apps tienen permisos sensibles, verifica si los otorgaste:\n",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(analysisTotalInfo.descriptionRisk, style = MaterialTheme.typography.titleSmall)
            }

        }
    }
}


