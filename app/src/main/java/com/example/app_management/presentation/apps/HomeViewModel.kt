package com.example.app_management.presentation.apps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_management.domain.models.AppInfoAnalysis
import com.example.app_management.extentions.hasUsageStatsPermission
import com.example.app_management.extentions.requestUsageStatsPermission
import com.example.app_management.domain.useCases.AnalysisUseCase
import com.example.app_management.domain.useCases.GetAppsUseCase
import com.example.app_management.presentation.apps.components.StateWidgetAppBar
import com.example.app_management.presentation.apps.components.TypeAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getAppsUseCase: GetAppsUseCase,
    val analysisUseCase: AnalysisUseCase,
    private val application: Application
) : ViewModel() {

    private var jobOrderApps: Job? = null

    private var searchJob: Job? = null

    private var getAppJobs: Job? = null

    private var analysisJob: Job? = null

    private var _items = listOf<AppInfoAnalysis>()

    private val _loadingEvent = MutableStateFlow(false)
    val loadingEvent: StateFlow<Boolean> = _loadingEvent.asStateFlow()

    private val _filteredItems = MutableStateFlow(_items)
    val filteredItems: StateFlow<List<AppInfoAnalysis>> = _filteredItems.asStateFlow()

    private val _appBarState = MutableStateFlow(StateWidgetAppBar.DEFAULT)
    val appBarState: StateFlow<StateWidgetAppBar> = _appBarState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _typeAnalysis = MutableStateFlow(TypeAnalysis.Memory)
    val typeAnalysis: StateFlow<TypeAnalysis> = _typeAnalysis.asStateFlow()

    fun setAppBarState(state: StateWidgetAppBar) {
        _appBarState.value = state
    }

    fun getLaunchApps() {
        getAppJobs?.cancel()
        getAppJobs = viewModelScope.launch {
            _filteredItems.value = listOf()
            _loadingEvent.value = true
            _items = withContext(Dispatchers.IO) { getAppsUseCase(typeAnalysis.value) }
            _loadingEvent.value = false
            onSearchQueryChanged(query.value)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _query.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _filteredItems.value = withContext(Dispatchers.Default) {
                if (query.isEmpty()) {
                    _items
                } else {
                    _items.filter { it.name.contains(query, ignoreCase = true) }
                }
            }
        }
    }

    fun orderByTypeAnalysis(typeAnalysis: TypeAnalysis) {
        if (!hasUsageStatsPermission()) {
            application.requestUsageStatsPermission()
            return
        }
        _typeAnalysis.value = typeAnalysis
        jobOrderApps?.cancel()
        jobOrderApps = viewModelScope.launch(Dispatchers.IO) {
            val sortedItems = _items.sortedByDescending {
                when (typeAnalysis) {
                    TypeAnalysis.Memory -> it.size
                    TypeAnalysis.Security -> it.percentageRisk
                    TypeAnalysis.Usage -> it.percentageUsage
                }
            }
            withContext(Dispatchers.Main) {
                _filteredItems.value = sortedItems
            }
        }
    }

    fun updateShowDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun analysisDescription() {
        if (!hasUsageStatsPermission()) {
            application.requestUsageStatsPermission()
            return
        }
        analysisJob?.cancel()
        analysisJob = viewModelScope.launch(Dispatchers.IO) {
            _description.value = analysisUseCase(filteredItems.value)
            updateShowDialog()
        }
    }

    fun hasUsageStatsPermission(): Boolean {
        return application.hasUsageStatsPermission()
    }


}