package com.example.app_management.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel

@Composable
fun AppBar(
    title: String = "Gestor de Aplicacione Alwa", onSearch: (String) -> Unit = {},
    appBarState: StateWidgetAppBar,
    query:String,
    onChangeState: (StateWidgetAppBar) -> Unit
) {
    when (appBarState) {
        StateWidgetAppBar.SEARCH ->
            SearchAppBar(
                query = query,
                changeState = { onChangeState(StateWidgetAppBar.DEFAULT) },
                search = onSearch
            )

        StateWidgetAppBar.DEFAULT ->
            DefaultAppBar(
                title = title,
                changeState = { onChangeState(StateWidgetAppBar.SEARCH)}
            )
    }
}
