package com.example.app_management.presentation.apps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_management.domain.models.AppInfoAnalysis
import com.example.app_management.domain.useCases.AnalysisUseCase
import com.example.app_management.domain.useCases.GetAppsUseCase
import com.example.app_management.presentation.apps.components.StateWidgetAppBar
import com.example.app_management.presentation.apps.components.TypeAnalysis
import com.example.app_management.presentation.ui.PermissionChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getAppsUseCase: GetAppsUseCase,
    private val application: Application,
    private val permissionChecker: PermissionChecker,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private var jobOrderApps: Job? = null

    private var searchJob: Job? = null

    private var getAppJobs: Job? = null

    private var _items = listOf<AppInfoAnalysis>()

    private val _loadingEvent = MutableStateFlow(false)
    val loadingEvent: StateFlow<Boolean> = _loadingEvent.asStateFlow()

    private val _filteredItems = MutableStateFlow(_items)
    val filteredItems: StateFlow<List<AppInfoAnalysis>> = _filteredItems.asStateFlow()

    private val _appBarState = MutableStateFlow(StateWidgetAppBar.DEFAULT)
    val appBarState: StateFlow<StateWidgetAppBar> = _appBarState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

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
            val apps = withContext(coroutineContextProvider.backgroundContext) {
                getAppsUseCase(typeAnalysis.value)
            }
            _items = apps
            _loadingEvent.value = false
            onSearchQueryChanged(query.value)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _query.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch(coroutineContextProvider.backgroundContext) {
            _filteredItems.value =
                if (query.isEmpty()) {
                    _items
                } else {
                    _items.filter { it.name.contains(query, ignoreCase = true) }
                }
        }

    }

    fun orderByTypeAnalysis(typeAnalysis: TypeAnalysis) {
        if (!hasUsageStatsPermission()) {
            permissionChecker.requestUsageStatsPermission(application)
            return
        }
        _typeAnalysis.value = typeAnalysis
        jobOrderApps?.cancel()
        jobOrderApps = viewModelScope.launch(coroutineContextProvider.backgroundContext) {
            val sortedItems = _items.sortedByDescending {
                when (typeAnalysis) {
                    TypeAnalysis.Memory -> it.size
                    TypeAnalysis.Security -> it.percentageRisk
                    TypeAnalysis.Usage -> it.percentageUsage
                }
            }
            _filteredItems.value = sortedItems
        }
    }

    fun updateShowDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun analysisDescription() {
        viewModelScope.launch(coroutineContextProvider.backgroundContext) {
            if (!hasUsageStatsPermission()) {
                permissionChecker.requestUsageStatsPermission(application)
                return@launch
            }
            updateShowDialog()
        }
    }

    fun hasUsageStatsPermission(): Boolean {
        return permissionChecker.hasUsageStatsPermission(application)
    }
}


data class CoroutineContextProvider(
    val mainContext: CoroutineContext,
    val backgroundContext: CoroutineContext
)