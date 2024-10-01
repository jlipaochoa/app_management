package com.example.app_management.presentation.apps

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
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_management.presentation.apps.components.AnalysisSection
import com.example.app_management.presentation.apps.components.AppBar
import com.example.app_management.presentation.apps.components.CardAppItem
import com.example.app_management.ui.theme.Green40
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

    val description by viewModel.description.collectAsState()

    val typeAnalysis by viewModel.typeAnalysis.collectAsState()

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
            AnalysisSection(
                analysisDescription = description,
                analysisCallBack = { type -> viewModel.analysis(type) },
                typeAnalysisSelected = typeAnalysis
            )
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


