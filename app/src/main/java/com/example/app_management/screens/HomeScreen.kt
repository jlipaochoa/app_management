package com.example.app_management.screens

import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_management.data.AppInfoModel
import com.example.app_management.extentions.getLaunchApps
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_management.extentions.hasUsageStatsPermission
import com.example.app_management.extentions.requestUsageStatsPermission
import com.example.app_management.extentions.timesUsageStats
import com.example.app_management.mappers.toAppInfoModel
import com.example.app_management.ui.theme.Green40
import com.example.app_management.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import kotlin.text.Typography.times

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val filteredItems by viewModel.filteredItems.collectAsState()

    val appBarState by viewModel.appBarState.collectAsState()

    val query by viewModel.query.collectAsState()

    val loadingEvent by viewModel.loadingEvent.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = "Gestor de Aplicacione Alwa",
                appBarState = appBarState,
                query = query,
                onSearch = { viewModel.onSearchQueryChanged(it) },
                onChangeState = { viewModel.setAppBarState(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnalysisSection { type -> viewModel.analysis(type) }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
            ) {
                items(filteredItems) {
                    CardAppItem(it) {
                        navController.navigate(route = Screen.DetailAppScreen.route)
                    }
                }
                item { Spacer(modifier = Modifier.height(200.dp)) }
            }
            if (loadingEvent) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxHeight(), color = Green40
                )
            }
        }
    }
}


@HiltViewModel
class HomeViewModel @Inject constructor(val application: Application) : ViewModel() {

    private val _isPermissionStatsEnabled get() = application.hasUsageStatsPermission()

    private var jobOrderApps: Job? = null

    private var searchJob: Job? = null

    private var _items = listOf<AppInfoModel>()

    private val _loadingEvent = MutableStateFlow(false)
    val loadingEvent: StateFlow<Boolean> = _loadingEvent.asStateFlow()

    private val _filteredItems = MutableStateFlow(_items)
    val filteredItems: StateFlow<List<AppInfoModel>> = _filteredItems.asStateFlow()

    private val _appBarState = MutableStateFlow(StateWidgetAppBar.DEFAULT)
    val appBarState: StateFlow<StateWidgetAppBar> = _appBarState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query


    fun setAppBarState(state: StateWidgetAppBar) {
        _appBarState.value = state
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

    init {
        getLaunchApps(application.packageManager)
    }

    private fun getLaunchApps(packageManager: PackageManager) {
        val times = application.timesUsageStats()

        viewModelScope.launch {
            _loadingEvent.value = true
            _items = withContext(Dispatchers.IO) {
                packageManager.getLaunchApps()
                    .map { it.toAppInfoModel(packageManager, application.applicationContext, times) }
            }
            _loadingEvent.value = false
            _filteredItems.value = _items
        }
    }

    fun analysis(typeAnalysis: TypeAnalysis) {
        if (!_isPermissionStatsEnabled) {
            application.requestUsageStatsPermission()
            return
        }
        jobOrderApps?.cancel()
        jobOrderApps = viewModelScope.launch {
            val sortedItems = _items.sortedByDescending {
                when (typeAnalysis) {
                    TypeAnalysis.Memory -> it.size
                    TypeAnalysis.Security -> it.percentageRisk
                    TypeAnalysis.Usage -> it.percentageUsage
                    else -> it.size
                }
            }
            withContext(Dispatchers.Main) {
                _filteredItems.value = sortedItems
            }
        }
    }
}

