package com.example.app_management.presentation.detailApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.app_management.presentation.detailApp.components.PermissionCheck
import com.example.app_management.presentation.detailApp.components.SectionDetail
import com.example.app_management.presentation.ui.theme.Green40

@Composable
fun DetailAppScreen(
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val appDetail by detailViewModel.appDetail.collectAsState()

    val analysisUsage by detailViewModel.analysisUsage.collectAsState()

    val analysisPermissions by detailViewModel.analysisPermissions.collectAsState()

    val showDialog by detailViewModel.showDialog.collectAsState()

    val appInfo by detailViewModel.appInfo.collectAsState()

    DialogAddAppInfo(
        showDialog,
        appInfo,
        { detailViewModel.updateDescription(it) },
        { detailViewModel.updateShowDialog() },
        { detailViewModel.insertAppInfo(it) }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = appDetail?.image,
                        ),
                        contentDescription = "App Icon",
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = appDetail?.name ?: "Cargando ...",
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                style = MaterialTheme.typography.titleSmall,
                                text = appDetail?.category ?: "Cargando ...",
                                modifier = Modifier
                                    .background(color = Green40, shape = RoundedCornerShape(10.dp))
                                    .padding(5.dp),
                                color = Color.Black
                            )
                        }
                    }
                    IconButton(onClick = { detailViewModel.updateShowDialog() }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Descripcion",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        appDetail?.description ?: "Aun no cuenta con una descripcion",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                ) {
                    SectionDetail(
                        title = "Version App",
                        data = appDetail?.version ?: "Cargando ...",
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    VerticalDivider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    SectionDetail(
                        title = "Nº de app",
                        data = appDetail?.versionCode?.toString() ?: "Cargando ...",
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    VerticalDivider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    SectionDetail(
                        title = "Del Sistema",
                        data = appDetail?.isSystemApp?.toString() ?: "Cargando ...",
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    )
                }
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                ) {
                    SectionDetail(
                        title = "Fecha de Instalacion",
                        data = appDetail?.firstInstallTime ?: "Cargando ...",
                        modifier = Modifier
                            .weight(0.5f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    VerticalDivider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    SectionDetail(
                        title = "Fecha de Actualizacion",
                        data = appDetail?.lastUpdateTime ?: "Cargando ...",
                        modifier = Modifier
                            .weight(0.5f)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Esta aplicacion pesa: ${appDetail?.sizeApp} MB",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Porcentaje de uso en los ultimos 7 dias: ${appDetail?.percentageUsage} %",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        analysisUsage.first,
                        style = MaterialTheme.typography.titleMedium,
                        color = analysisUsage.second
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Permisos de la App",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        analysisPermissions.first,
                        color = analysisPermissions.second,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            items(appDetail?.permissions ?: listOf()) {
                PermissionCheck(
                    it.first,
                    it.second,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

