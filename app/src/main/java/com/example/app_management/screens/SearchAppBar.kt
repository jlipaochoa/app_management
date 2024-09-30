package com.example.app_management.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_management.ui.theme.DarkGrey40

@Composable
fun SearchAppBar(
    query: String,
    changeState: () -> Unit = { },
    search: (String) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .height(65.dp),
        color = DarkGrey40
    ) {
        TextField(
            singleLine = true,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            value = query,
            onValueChange = {
                search(it)
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
                    if (query.isNotEmpty()) {
                        search("")
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