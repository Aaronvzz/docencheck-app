package com.docencheck.app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneracionReportesScreen(onMenuClick: () -> Unit = {}) {
    var docenteSeleccionado by remember { mutableStateOf("--seleccionar--") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val docentes = listOf("Todos los docentes", "Giovanni Ramiro Rodríguez Sánchez", "María López García")

    val columnasReporte = listOf("ID Empleado", "Nombre", "Fecha", "Pase", "Hora", "Estado")

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Generación de Reportes",
                welcomeText = "Bienvenido, Administrador",
                onMenuClick = onMenuClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Rango de Fechas", style = MaterialTheme.typography.titleMedium, color = PrimaryDarkBlue)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = { fechaInicio = it },
                            label = { Text("Fecha inicio") },
                            placeholder = { Text("dd/mm/aaaa", color = TextMedium) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = fechaFin,
                            onValueChange = { fechaFin = it },
                            label = { Text("Fecha fin") },
                            placeholder = { Text("dd/mm/aaaa", color = TextMedium) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Docente", style = MaterialTheme.typography.bodyMedium, color = TextDark)
                    ExposedDropdownMenuBox(
                        expanded = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = !dropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = docenteSeleccionado, onValueChange = {}, readOnly = true,
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(8.dp)
                        )
                        ExposedDropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }) {
                            docentes.forEach { d ->
                                DropdownMenuItem(text = { Text(d) }, onClick = { docenteSeleccionado = d; dropdownExpanded = false })
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Vista previa de columnas
            Text("Vista previa - Columnas del reporte", style = MaterialTheme.typography.titleMedium, color = PrimaryDarkBlue)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = PrimaryMint, thickness = 2.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                columnasReporte.forEach { col ->
                    Text(col, style = MaterialTheme.typography.labelMedium, color = TextMedium)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* TODO: generar y descargar reporte */ },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
            ) {
                Icon(Icons.Default.Download, contentDescription = null, tint = BackgroundWhite)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generar y Descargar reporte", color = BackgroundWhite)
            }
        }
    }
}
