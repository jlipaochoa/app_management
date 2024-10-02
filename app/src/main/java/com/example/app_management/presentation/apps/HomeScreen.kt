package com.example.app_management.presentation.apps

import android.content.Intent
import android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.app_management.presentation.apps.components.AnalysisSection
import com.example.app_management.presentation.apps.components.AppBar
import com.example.app_management.presentation.apps.components.CardAppItem
import com.example.app_management.presentation.apps.components.DialogAnalysis
import com.example.app_management.presentation.ui.theme.Green40
import com.example.app_management.util.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val filteredItems by viewModel.filteredItems.collectAsState()

    val appBarState by viewModel.appBarState.collectAsState()

    val query by viewModel.query.collectAsState()

    val loadingEvent by viewModel.loadingEvent.collectAsState()

    val typeAnalysis by viewModel.typeAnalysis.collectAsState()

    var hasPermissions by remember { mutableStateOf(viewModel.hasUsageStatsPermission()) }

    val showDialog by viewModel.showDialog.collectAsState()

    DialogAnalysis(
        filteredItems = filteredItems,
        showDialog = showDialog,
        dismissDialog = { viewModel.updateShowDialog() }
    )

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
    }

    OnLifecycleEvent {
        hasPermissions = viewModel.hasUsageStatsPermission()
        if (hasPermissions) {
            viewModel.getLaunchApps()
        }
    }

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.analysisDescription() },
            ) {
                Icon(Icons.Filled.Settings, "Analisa.")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnalysisSection(
                analysisDescription = "",
                analysisCallBack = { type -> viewModel.orderByTypeAnalysis(type) },
                typeAnalysisSelected = typeAnalysis
            )
            if (!hasPermissions) {
                Text("No tienes permisos de Usage Stats.", Modifier.padding(horizontal = 20.dp).align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val intent = Intent(ACTION_USAGE_ACCESS_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        settingsLauncher.launch(intent)
                    },
                    modifier = Modifier.padding(horizontal = 20.dp).align(Alignment.CenterHorizontally)
                ) {
                    Text("Habilitar permisos")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                ) {
                    items(filteredItems) {
                        CardAppItem(it) {
                            navController.navigate(route = Screen.DetailAppScreen.route + "?packageName=${it.packageName}")
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
}

@Composable
fun OnLifecycleEvent(onEvent: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onEvent()
            }
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

