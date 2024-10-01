package com.example.app_management.presentation.apps.components

import androidx.compose.runtime.Composable

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
