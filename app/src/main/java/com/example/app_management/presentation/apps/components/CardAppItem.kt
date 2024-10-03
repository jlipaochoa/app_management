package com.example.app_management.presentation.apps.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.app_management.R
import com.example.domain.models.AppInfoAnalysisState
import com.example.app_management.presentation.ui.theme.DarkGrey40

@Composable
fun CardAppItem(
    appInfo: AppInfoAnalysisState,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = appInfo.image
                ),
                contentDescription = context.getString(R.string.app_icon),
                modifier = Modifier
                    .size(60.dp)
                    .background(DarkGrey40, shape = RoundedCornerShape(25))
                    .clip(CircleShape)
                    .padding(10.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = appInfo.name,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = context.getString(R.string.app_size, appInfo.size.toString()),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = context.getString(R.string.app_risk, appInfo.percentageRisk.toString()),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color(appInfo.colorRisk)
            )
            Text(
                text = context.getString(R.string.app_usage, appInfo.descriptionUsage),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color(appInfo.colorUsage)
            )
        }
    }
}