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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

data class Operador(val id: String, val nombre: String, val usuario: String)

@Composable
fun GestionOperadoresScreen(onBack: () -> Unit = {}) {
    val operadores = remember {
        mutableStateListOf(
            Operador("OP01", "Carlos Méndez", "c.mendez"),
            Operador("OP02", "Ana Torres", "a.torres"),
        )
    }
    var showDeleteDialog by remember { mutableStateOf<Operador?>(null) }

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Gestión de Operadores",
                welcomeText = "Bienvenido, Administrador",
                onBackClick = onBack
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* TODO: agregar operador */ },
                containerColor = PrimaryDarkBlue,
                contentColor = BackgroundWhite,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nuevo Operador") }
            )
        }
    ) { padding ->
        if (operadores.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    text = "No hay operadores registrados.\nHaga clic en \"Nuevo Operador\" para agregar uno.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(operadores) { op ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(48.dp).clip(CircleShape).background(PrimaryMint),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryDarkBlue)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(op.nombre, style = MaterialTheme.typography.bodyLarge, color = TextDark)
                                Text("Usuario: ${op.usuario}", style = MaterialTheme.typography.bodyMedium, color = TextMedium)
                                Text("ID: ${op.id}", style = MaterialTheme.typography.bodyMedium, color = TextMedium)
                            }
                            IconButton(onClick = { /* TODO: editar */ }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = PrimaryDarkBlue)
                            }
                            IconButton(onClick = { showDeleteDialog = op }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = ErrorRed)
                            }
                        }
                    }
                }
            }
        }
    }

    showDeleteDialog?.let { op ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Eliminar Operador") },
            text = { Text("¿Está seguro de eliminar a ${op.nombre}?") },
            confirmButton = {
                TextButton(onClick = { operadores.remove(op); showDeleteDialog = null }) {
                    Text("Eliminar", color = ErrorRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) { Text("Cancelar") }
            }
        )
    }
}
