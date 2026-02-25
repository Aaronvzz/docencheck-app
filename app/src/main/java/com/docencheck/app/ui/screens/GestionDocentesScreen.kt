package com.docencheck.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.docencheck.app.ui.components.DocenCheckTopBar
import com.docencheck.app.ui.theme.*

private val NAME_REGEX = Regex("^[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ ]+$")

data class Docente(
    val id: String,
    val nombre: String,
    val enrolado: Boolean,
    val contrasena: String = ""
)

private fun calcularSiguienteId(docentes: List<Docente>): String {
    val maxId = docentes
        .mapNotNull { it.id.trimStart('0').ifEmpty { "0" }.toIntOrNull() }
        .maxOrNull() ?: 0
    return String.format("%03d", maxId + 1)
}

@Composable
fun GestionDocentesScreen(onMenuClick: () -> Unit = {}) {
    val docentes = remember {
        mutableStateListOf(
            Docente("001", "Giovanni Ramiro Rodríguez Sánchez", true),
            Docente("002", "María López García", false),
        )
    }
    var showDeleteDialog by remember { mutableStateOf<Docente?>(null) }
    var showRegistrarDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DocenCheckTopBar(
                title = "Gestión de Docentes",
                welcomeText = "Bienvenido, Administrador",
                onMenuClick = onMenuClick
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showRegistrarDialog = true },
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
                    textAlign = TextAlign.Center
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

    // ── Diálogo: Registrar Nuevo Docente ────────────────────────
    if (showRegistrarDialog) {
        RegistrarDocenteDialog(
            nextId = calcularSiguienteId(docentes),
            onDismiss = { showRegistrarDialog = false },
            onGuardar = { nuevoDocente ->
                docentes.add(nuevoDocente)
                showRegistrarDialog = false
            }
        )
    }

    // ── Diálogo: Confirmar eliminación ───────────────────────────
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

// ────────────────────────────────────────────────────────────────
// Dialog: formulario para registrar un nuevo docente
// ────────────────────────────────────────────────────────────────
@Composable
private fun RegistrarDocenteDialog(
    nextId: String,
    onDismiss: () -> Unit,
    onGuardar: (Docente) -> Unit
) {
    var nombres by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorNombres by remember { mutableStateOf("") }
    var errorApellidoPaterno by remember { mutableStateOf("") }
    var errorApellidoMaterno by remember { mutableStateOf("") }
    var errorContrasena by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // ── Título ───────────────────────────────────────
                Text(
                    text = "Registrar Nuevo Docente",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryDarkBlue,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider(color = PrimaryMint, thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // ── Campos (con scroll) ──────────────────────────
                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DialogField(
                        label = "Nombre(s)",
                        value = nombres,
                        onValueChange = { nombres = it; errorNombres = "" },
                        placeholder = "Ej. Giovanni Ramiro",
                        error = errorNombres
                    )
                    DialogField(
                        label = "Apellido Paterno",
                        value = apellidoPaterno,
                        onValueChange = { apellidoPaterno = it; errorApellidoPaterno = "" },
                        placeholder = "Ej. Rodríguez",
                        error = errorApellidoPaterno
                    )
                    DialogField(
                        label = "Apellido Materno",
                        value = apellidoMaterno,
                        onValueChange = { apellidoMaterno = it; errorApellidoMaterno = "" },
                        placeholder = "Ej. Sánchez",
                        error = errorApellidoMaterno
                    )

                    // ID Empleado — solo lectura
                    Column {
                        Text(
                            text = "ID Empleado",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = nextId,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = SurfaceGray,
                                disabledTextColor = TextMedium,
                                disabledBorderColor = SurfaceGray
                            )
                        )
                        Text(
                            text = "Asignado automáticamente",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMedium,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }

                    // Contraseña
                    Column {
                        Text(
                            text = "Contraseña",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = contrasena,
                            onValueChange = { contrasena = it; errorContrasena = "" },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Mínimo 8 caracteres", color = TextMedium) },
                            visualTransformation = if (passwordVisible) VisualTransformation.None
                                                   else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.Visibility
                                                      else Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = TextMedium
                                    )
                                }
                            },
                            isError = errorContrasena.isNotEmpty(),
                            supportingText = if (errorContrasena.isNotEmpty()) {
                                { Text(errorContrasena, color = ErrorRed) }
                            } else null,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = SurfaceGray,
                                focusedContainerColor = SurfaceGray,
                                focusedBorderColor = PrimaryDarkBlue
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ── Botones ──────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = TextMedium)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            var valid = true
                            if (nombres.isBlank()) {
                                errorNombres = "El campo es requerido"
                                valid = false
                            } else if (!NAME_REGEX.matches(nombres.trim())) {
                                errorNombres = "Solo letras y espacios"
                                valid = false
                            }
                            if (apellidoPaterno.isBlank()) {
                                errorApellidoPaterno = "El campo es requerido"
                                valid = false
                            } else if (!NAME_REGEX.matches(apellidoPaterno.trim())) {
                                errorApellidoPaterno = "Solo letras y espacios"
                                valid = false
                            }
                            if (apellidoMaterno.isBlank()) {
                                errorApellidoMaterno = "El campo es requerido"
                                valid = false
                            } else if (!NAME_REGEX.matches(apellidoMaterno.trim())) {
                                errorApellidoMaterno = "Solo letras y espacios"
                                valid = false
                            }
                            if (contrasena.isBlank()) {
                                errorContrasena = "La contraseña es requerida"
                                valid = false
                            } else if (contrasena.length < 8) {
                                errorContrasena = "Mínimo 8 caracteres"
                                valid = false
                            }
                            if (valid) {
                                val nombreCompleto =
                                    "${nombres.trim()} ${apellidoPaterno.trim()} ${apellidoMaterno.trim()}"
                                onGuardar(
                                    Docente(
                                        id = nextId,
                                        nombre = nombreCompleto,
                                        enrolado = false,
                                        contrasena = contrasena
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
                    ) {
                        Text("Guardar", color = BackgroundWhite)
                    }
                }
            }
        }
    }
}

// ────────────────────────────────────────────────────────────────
// Campo de texto reutilizable dentro del Dialog
// ────────────────────────────────────────────────────────────────
@Composable
private fun DialogField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextDark,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = TextMedium) },
            isError = error.isNotEmpty(),
            supportingText = if (error.isNotEmpty()) {
                { Text(error, color = ErrorRed) }
            } else null,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceGray,
                focusedContainerColor = SurfaceGray,
                focusedBorderColor = PrimaryDarkBlue
            )
        )
    }
}

// ────────────────────────────────────────────────────────────────
// Tarjeta de cada docente en la lista
// ────────────────────────────────────────────────────────────────
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
                Text(
                    "ID de Empleado: ${docente.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMedium
                )
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
