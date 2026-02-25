package com.docencheck.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

data class Docente(
    val id: String,
    val nombre: String,
    val enrolado: Boolean
)

@Composable
fun GestionDocentesScreen(onBack: () -> Unit = {}) {
    val docentes = remember {
        mutableStateListOf(
            Docente("001", "Giovanni Ramiro Rodríguez Sánchez", true),
            Docente("002", "María López García", false),
        )
    }
    var showDeleteDialog by remember { mutableStateOf<Docente?>(null) }

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Gestión de Docentes",
                welcomeText = "Bienvenido, Administrador",
                onBackClick = onBack
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* TODO: navegar a agregar docente */ },
                containerColor = PrimaryDarkBlue,
                contentColor = BackgroundWhite,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nuevo Docente") }
            )
        }
    ) { padding ->
        if (docentes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay docentes registrados.\nHaga clic en \"Nuevo Docente\" para agregar uno.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMedium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(docentes) { docente ->
                    DocenteCard(
                        docente = docente,
                        onEdit = { /* TODO */ },
                        onDelete = { showDeleteDialog = docente }
                    )
                }
            }
        }
    }

    // Diálogo confirmación eliminar
    showDeleteDialog?.let { docente ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Eliminar Docente") },
            text = { Text("¿Está seguro de eliminar a ${docente.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    docentes.remove(docente)
                    showDeleteDialog = null
                }) { Text("Eliminar", color = ErrorRed) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun DocenteCard(
    docente: Docente,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryMint),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryDarkBlue)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(docente.nombre, style = MaterialTheme.typography.bodyLarge, color = TextDark)
                Text("ID de Empleado: ${docente.id}", style = MaterialTheme.typography.bodyMedium, color = TextMedium)
                Text(
                    text = if (docente.enrolado) "Enrolado" else "Sin Enrolar",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (docente.enrolado) SuccessGreen else ErrorRed
                )
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = PrimaryDarkBlue)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = ErrorRed)
            }
        }
    }
}
