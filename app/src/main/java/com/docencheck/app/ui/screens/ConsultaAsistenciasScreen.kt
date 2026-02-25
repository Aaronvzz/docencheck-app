package com.docencheck.app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

data class RegistroAsistencia(
    val docente: String,
    val fecha: String,
    val pase: String,
    val hora: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaAsistenciasScreen(onBack: () -> Unit = {}) {
    var docenteFiltro by remember { mutableStateOf("--seleccionar--") }
    var fechaFiltro by remember { mutableStateOf("") }
    var paseFiltro by remember { mutableStateOf("-seleccionar-") }
    var docenteExpanded by remember { mutableStateOf(false) }
    var paseExpanded by remember { mutableStateOf(false) }

    val docentes = listOf("Todos", "Giovanni Ramiro Rodríguez Sánchez", "María López García")
    val pases = listOf("Todos", "Entrada", "Receso", "Salida")

    val registros = listOf(
        RegistroAsistencia("Giovanni R.", "24/02/2026", "Entrada", "08:01"),
        RegistroAsistencia("María L.", "24/02/2026", "Entrada", "08:05"),
        RegistroAsistencia("Giovanni R.", "24/02/2026", "Receso", "10:30"),
    )

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Consulta de Asistencias",
                welcomeText = "Bienvenido, Administrador",
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            // Panel de Filtros
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Filtros de Búsqueda", style = MaterialTheme.typography.titleMedium, color = PrimaryDarkBlue)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Docente
                    Text("Docente", style = MaterialTheme.typography.bodyMedium, color = TextDark)
                    ExposedDropdownMenuBox(expanded = docenteExpanded, onExpandedChange = { docenteExpanded = !docenteExpanded }) {
                        OutlinedTextField(
                            value = docenteFiltro, onValueChange = {}, readOnly = true,
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        ExposedDropdownMenu(expanded = docenteExpanded, onDismissRequest = { docenteExpanded = false }) {
                            docentes.forEach { d -> DropdownMenuItem(text = { Text(d) }, onClick = { docenteFiltro = d; docenteExpanded = false }) }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Fecha y Pase en fila
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Fecha", style = MaterialTheme.typography.bodyMedium, color = TextDark)
                            OutlinedTextField(
                                value = fechaFiltro, onValueChange = { fechaFiltro = it },
                                placeholder = { Text("dd/mm/aaaa", color = TextMedium) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Pase", style = MaterialTheme.typography.bodyMedium, color = TextDark)
                            ExposedDropdownMenuBox(expanded = paseExpanded, onExpandedChange = { paseExpanded = !paseExpanded }) {
                                OutlinedTextField(
                                    value = paseFiltro, onValueChange = {}, readOnly = true,
                                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                ExposedDropdownMenu(expanded = paseExpanded, onDismissRequest = { paseExpanded = false }) {
                                    pases.forEach { p -> DropdownMenuItem(text = { Text(p) }, onClick = { paseFiltro = p; paseExpanded = false }) }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { /* TODO: filtrar */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = BackgroundWhite)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buscar", color = BackgroundWhite)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resultados
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(registros) { reg ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(reg.docente, style = MaterialTheme.typography.bodyLarge, color = TextDark)
                                Text("${reg.fecha}  |  ${reg.hora}", style = MaterialTheme.typography.bodyMedium, color = TextMedium)
                            }
                            Badge(containerColor = PrimaryMint) {
                                Text(reg.pase, color = PrimaryDarkBlue, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
