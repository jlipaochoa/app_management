package com.example.app_management.presentation.detailApp

import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.app_management.R
import com.example.app_management.ui.theme.Green40
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun DetailAppScreen(
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
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
                            data = R.drawable.profiler
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
                                text = "App de aqui",
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                style = MaterialTheme.typography.titleSmall,
                                text = "Game",
                                modifier = Modifier
                                    .background(color = Green40, shape = RoundedCornerShape(10.dp))
                                    .padding(5.dp)
                            )
                        }
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
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
                        "Esta es una aplicacion para alsdfasdfl lkasdjf aldkjfa asdlfkj",
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
                        data = "2.4.6v",
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    SectionDetail(
                        title = "Nº de app",
                        data = "128",
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    SectionDetail(
                        title = "Del Sistema",
                        data = "Si",
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    )
                }
                Divider(
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
                        data = "12/12/2024",
                        modifier = Modifier
                            .weight(0.5f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    SectionDetail(
                        title = "Fecha de Actualizacion",
                        data = "12/12/2024",
                        modifier = Modifier
                            .weight(0.5f)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            item {
                Text(
                    "Permisos de la App",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp).fillMaxWidth()
                )
            }
            items(detailViewModel.listPermission) {
                PermissionCheck(
                    it.first,
                    it.second,
                    modifier = Modifier.padding(start = 30.dp, top = 5.dp).fillMaxWidth()
                )
            }
        }
    }
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        savedStateHandle.get<String>("packageName")?.let { noteId ->
            if(noteId != "") {
                viewModelScope.launch {
                    Log.e("quack 123", noteId)
                }
            }
        }
    }
    val listPermission = listOf(
        Pair("Camara", true),
        Pair("Camara", false),
        Pair("Camara", true),
        Pair("Camara", true)
    )
}

@Composable
fun PermissionCheck(label: String, isChecked: Boolean, modifier: Modifier) {
    val icon = if (isChecked) Icons.Filled.CheckCircle else Icons.Filled.Clear
    Row(modifier) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White
        )
        Spacer(Modifier.width(10.dp))
        Text(
            label,
            color = Color.White,
        )
    }
}

@Composable
fun SectionDetail(modifier: Modifier, title: String, data: String) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                title,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                data,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

