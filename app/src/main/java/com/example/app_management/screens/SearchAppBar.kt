package com.example.app_management.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_management.ui.theme.DarkGrey40

@Composable
@Preview
fun SearchAppBar(changeState: () -> Unit = { }, search: (String) -> Unit = {}) {
    var text by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkGrey40)
            .height(65.dp),
        color = DarkGrey40
    ) {
        TextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            value = text,
            onValueChange = {
                text = it
                search(text)
            },
            placeholder = {
                Text(
                    text = "Busca tu aplicacion",
                    color = Color.White
                )
            },
            trailingIcon =
            {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        text = ""
                        return@IconButton
                    }
                    changeState()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close icon",
                        tint = Color.White
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White
            ),
        )
    }
}