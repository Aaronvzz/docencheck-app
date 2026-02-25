package com.docencheck.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.theme.PrimaryDarkBlue
import com.docencheck.app.ui.theme.BackgroundWhite

@Composable
fun DocenCheckTopBar(
    title: String,
    welcomeText: String = "",
    onMenuClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryDarkBlue)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = BackgroundWhite
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }

            Text(
                text = "Control de Asistencia",
                style = MaterialTheme.typography.titleMedium,
                color = BackgroundWhite
            )

            if (onMenuClick != null) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = BackgroundWhite
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }

        if (welcomeText.isNotEmpty()) {
            Text(
                text = welcomeText,
                style = MaterialTheme.typography.bodyMedium,
                color = BackgroundWhite.copy(alpha = 0.85f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = BackgroundWhite,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
