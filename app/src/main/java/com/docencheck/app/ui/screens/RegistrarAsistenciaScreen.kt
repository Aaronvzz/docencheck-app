package com.docencheck.app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarAsistenciaScreen(onBack: () -> Unit = {}) {
    var docenteSeleccionado by remember { mutableStateOf("--seleccionar--") }
    var paseSeleccionado by remember { mutableStateOf("") } // Entrada, Receso, Salida
    var dropdownExpanded by remember { mutableStateOf(false) }

    val docentes = listOf(
        "Giovanni Ramiro Rodríguez Sánchez (ID: 001)",
        "María López García (ID: 002)"
    )
    val pases = listOf("Entrada", "Receso", "Salida")

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Registrar asistencia",
                welcomeText = "Bienvenido, Operador",
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Selector de Docente
            Text("Seleccionar docente", style = MaterialTheme.typography.bodyLarge, color = TextDark)
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
                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
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

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de Pase
            Text("Favor de seleccionar el pase", style = MaterialTheme.typography.bodyLarge, color = TextDark)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pases.forEach { pase ->
                    PaseButton(
                        label = pase,
                        selected = paseSeleccionado == pase,
                        onClick = { paseSeleccionado = pase },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Registrar
            Button(
                onClick = { /* TODO: abrir cámara para verificación facial */ },
                enabled = docenteSeleccionado != "--seleccionar--" && paseSeleccionado.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
            ) {
                Text("Registrar Asistencia", color = BackgroundWhite)
            }
        }
    }
}

@Composable
private fun PaseButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) PrimaryDarkBlue else SurfaceGray,
            contentColor = if (selected) BackgroundWhite else TextDark
        )
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium)
    }
}
