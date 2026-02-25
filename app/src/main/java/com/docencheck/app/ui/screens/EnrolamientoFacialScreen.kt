package com.docencheck.app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrolamientoFacialScreen(onBack: () -> Unit = {}) {
    var docenteSeleccionado by remember { mutableStateOf("--seleccionar--") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val docentes = listOf(
        "Giovanni Ramiro Rodríguez Sánchez (ID: 001)",
        "María López García (ID: 002)"
    )

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Enrolamiento Facial",
                welcomeText = "Bienvenido, Administrador",
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Selector docente
            Text("Seleccione un docente", style = MaterialTheme.typography.bodyLarge, color = TextDark,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = docenteSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = TextDark)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = SurfaceGray,
                        focusedContainerColor = SurfaceGray,
                        focusedBorderColor = PrimaryDarkBlue
                    )
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    docentes.forEach { docente ->
                        DropdownMenuItem(
                            text = { Text(docente) },
                            onClick = {
                                docenteSeleccionado = docente
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Área de cámara (placeholder)
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(SurfaceGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = TextMedium,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Vista de cámara", color = TextMedium, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* TODO: iniciar captura facial */ },
                enabled = docenteSeleccionado != "--seleccionar--",
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
            ) {
                Text("Iniciar Enrolamiento", color = BackgroundWhite)
            }
        }
    }
}
