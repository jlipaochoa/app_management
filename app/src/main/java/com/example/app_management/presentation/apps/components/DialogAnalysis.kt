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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.AppInfoAnalysisState
import com.example.domain.models.AnalysisTotalInfo
import com.example.app_management.presentation.detailApp.FullScreenCustomDialog
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.app_management.R

@Composable
fun DialogAnalysis(
    showDialog: Boolean,
    filteredItems: List<AppInfoAnalysisState>?,
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
                Text(stringResource(id = R.string.dialog_analysis_title), style = MaterialTheme.typography.titleLarge)
                Text(
                    stringResource(id = R.string.dialog_analysis_size),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(analysisTotalInfo.descriptionSize, style = MaterialTheme.typography.titleSmall)
                Text(
                    stringResource(id = R.string.dialog_analysis_usage),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    analysisTotalInfo.descriptionUsage,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    stringResource(id = R.string.dialog_analysis_risk),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(analysisTotalInfo.descriptionRisk, style = MaterialTheme.typography.titleSmall)
            }

        }
    }
}
