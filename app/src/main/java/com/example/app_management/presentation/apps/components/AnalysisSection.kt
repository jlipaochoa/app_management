package com.example.app_management.presentation.apps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app_management.ui.theme.DarkGrey40
import com.example.app_management.ui.theme.Green40
import com.example.app_management.ui.theme.PurpleGrey80
import com.example.app_management.ui.theme.Yellow80

@Composable
fun AnalysisSection(
    analysisCallBack: (TypeAnalysis) -> Unit,
    analysisDescription: String = "",
    typeAnalysisSelected: TypeAnalysis
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(DarkGrey40, shape = RoundedCornerShape(25.dp))
            .padding(start = 18.dp, bottom = 10.dp, end = 18.dp, top = 20.dp)
    ) {
        MemoryAndStorageInfo(context = LocalContext.current)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Analisis Por :", style = MaterialTheme.typography.titleMedium, color = Color.White)
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { analysisCallBack(TypeAnalysis.Usage) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleGrey80,
                    contentColor = Color.Black
                )
            ) {
                Text("Uso", style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button(
                onClick = { analysisCallBack(TypeAnalysis.Security) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow80,
                    contentColor = Color.Black
                )
            ) {
                Text("Riesgo", style = MaterialTheme.typography.titleSmall, maxLines = 1)
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button(
                onClick = { analysisCallBack(TypeAnalysis.Memory) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green40,
                    contentColor = Color.Black
                )
            ) {
                Text("Memoria", style = MaterialTheme.typography.titleSmall)
            }
            if (analysisDescription.isNotEmpty()) {
                Text("asdfasdf")
            }
        }
    }
}

enum class TypeAnalysis {
    Usage,
    Memory,
    Security
}
