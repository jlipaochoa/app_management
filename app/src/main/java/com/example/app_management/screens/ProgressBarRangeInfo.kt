package com.example.app_management.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app_management.ui.theme.DarkGrey30
import com.example.app_management.ui.theme.Green40

@Composable
fun ProgressBarRangeInfo(
    label: String,
    icon: Int,
    total: Long,
    usage: Long,
    suffix: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.weight(0.3f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(18.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
        Spacer(modifier = Modifier.weight(0.1f))
        Column(modifier = Modifier.weight(0.6f), verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "$usage $suffix",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Text(
                    "$total $suffix",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
            LinearProgressIndicator(
                progress = ((usage.toFloat() / total.toFloat())),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Green40,
                trackColor = DarkGrey30,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}