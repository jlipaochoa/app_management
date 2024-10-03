package com.example.app_management.presentation.apps.components

import androidx.lifecycle.ViewModel
import com.example.domain.models.AnalysisTotalInfo
import com.example.domain.models.AppInfoAnalysisState
import com.example.app_management.domain.useCases.AnalysisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DialogAnalysisViewModel @Inject constructor(
    val analysisUseCase: AnalysisUseCase
) : ViewModel() {

    private val _analysisTotalInfo = MutableStateFlow(AnalysisTotalInfo(""))
    val analysisTotalInfo: StateFlow<AnalysisTotalInfo> = _analysisTotalInfo.asStateFlow()

    fun getAnalysisTotalInfo(apps: List<AppInfoAnalysisState>) {
        _analysisTotalInfo.value = analysisUseCase(apps)
    }
}