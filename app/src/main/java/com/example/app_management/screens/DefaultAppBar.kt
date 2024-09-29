package com.example.app_management.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_management.ui.theme.DarkGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DefaultAppBar(
    title: String = "Gestor de Aplicacione Alwa",
    changeState: () -> Unit = { },
) {
    TopAppBar(
        title = {
            Column (modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center){
                Text(
                    "Gestor de Aplicaciones Alwa",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = Modifier.height(65.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkGrey40),
        actions = {
            IconButton(onClick = { changeState() }, modifier = Modifier.fillMaxHeight()) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        },
    )
}