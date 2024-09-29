package com.example.app_management.screens

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_management.extentions.AppInfoModel
import com.example.app_management.extentions.getLaunchApps
import com.example.app_management.extentions.toAppInfoModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_management.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val filteredItems by viewModel.filteredItems.collectAsState()

    val appBarState by viewModel.appBarState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = "Gestor de Aplicacione Alwa",
                appBarState = appBarState,
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
            AnalysisSection()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(filteredItems) {
                    CardAppItem(it){
                        navController.navigate(route = Screen.DetailAppScreen.route)
                    }
                }
            }
        }
    }
}


@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : ViewModel() {

    private var _items = listOf<AppInfoModel>()

    private val _filteredItems = MutableStateFlow(_items)
    val filteredItems: StateFlow<List<AppInfoModel>> = _filteredItems.asStateFlow()

    private val _appBarState = MutableStateFlow(StateWidgetAppBar.DEFAULT)
    val appBarState: StateFlow<StateWidgetAppBar> = _appBarState

    fun setAppBarState(state: StateWidgetAppBar) {
        _appBarState.value = state
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
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
        viewModelScope.launch {
            _items = withContext(Dispatchers.IO) {
                packageManager.getLaunchApps().map { it.toAppInfoModel(packageManager) }
            }
            _filteredItems.value = _items
        }
    }

}


