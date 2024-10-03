package com.example.app_management.presentation.detailApp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.domain.models.AppInfo
import com.example.app_management.presentation.ui.theme.DarkGrey40
import com.example.app_management.presentation.ui.theme.Green40

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DialogAddAppInfo(
    showDialog: Boolean,
    appInfo: AppInfo?,
    updateAppInfo: (AppInfo?) -> Unit,
    dismissDialog: () -> Unit = {},
    clickButton: (AppInfo) -> Unit = {}
) {
    Log.e("quack", appInfo?.description.toString())
    val listItems = listOf("Gamer", "Navegador", "Social media", "Productividad")
    CustomDialog(
        showDialog = showDialog,
        onDismissRequest = { dismissDialog() }
    ) {
        Column {
            Box(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surface,
                    )
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column {
                    Text("Ingresa la info de tu app ", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.padding(10.dp))
                    OutlinedTextField(
                        value = appInfo?.description ?: "",
                        onValueChange = { updateAppInfo(appInfo?.copy(description = it)) },
                        label = { Text("Ingresa description") }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(9.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth(1F)

                    ) {
                        listItems.forEach {
                            CustomChip(
                                it,
                                appInfo?.category ?: "Ninguno"
                            ) { updateAppInfo(appInfo?.copy(category = it)) }
                        }
                    }
                }

            }
            Row(
                Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        dismissDialog()
                        clickButton(
                            AppInfo(
                                "",
                                appInfo?.description ?: "",
                                appInfo?.category ?: "Ninguno"
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(Green40)
                ) {
                    Text(text = "Crear")
                }
            }
        }
    }
}

@Composable
fun CustomChip(text: String, selected: String, click: (String) -> Unit) {
    val colorChip = if (text == selected) Green40 else DarkGrey40
    val colorText = if (text == selected) Color.Black else Color.White
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(colorChip, shape = RoundedCornerShape(50))
            .clickable {
                click(text)
            }
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = colorText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier.padding(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }

            }
        }
    }
}

@Composable
fun FullScreenCustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                content()
            }
        }
    }
}

