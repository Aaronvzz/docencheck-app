package com.docencheck.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.docencheck.app.ui.theme.*
import java.util.UUID

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    var nombres by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmarPasswordVisible by remember { mutableStateOf(false) }

    var errorNombres by remember { mutableStateOf("") }
    var errorApellidoPaterno by remember { mutableStateOf("") }
    var errorApellidoMaterno by remember { mutableStateOf("") }
    var errorContrasena by remember { mutableStateOf("") }
    var errorConfirmarContrasena by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var idGenerado by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryDarkBlue)
                .padding(vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "DocenCheck",
                    style = MaterialTheme.typography.displayLarge,
                    color = BackgroundWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.bodyLarge,
                    color = PrimaryMint
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Completa los datos para registrarte",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo Nombres
        RegisterField(
            label = "Nombres",
            value = nombres,
            onValueChange = { nombres = it; errorNombres = "" },
            placeholder = "Ingrese sus nombres",
            error = errorNombres
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Apellido Paterno
        RegisterField(
            label = "Apellido Paterno",
            value = apellidoPaterno,
            onValueChange = { apellidoPaterno = it; errorApellidoPaterno = "" },
            placeholder = "Ingrese su apellido paterno",
            error = errorApellidoPaterno
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Apellido Materno
        RegisterField(
            label = "Apellido Materno",
            value = apellidoMaterno,
            onValueChange = { apellidoMaterno = it; errorApellidoMaterno = "" },
            placeholder = "Ingrese su apellido materno",
            error = errorApellidoMaterno
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Crear Contraseña
        PasswordField(
            label = "Crear contraseña",
            value = contrasena,
            onValueChange = { contrasena = it; errorContrasena = "" },
            placeholder = "Mínimo 8 caracteres",
            passwordVisible = passwordVisible,
            onToggleVisibility = { passwordVisible = !passwordVisible },
            error = errorContrasena
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Confirmar Contraseña
        PasswordField(
            label = "Confirmar contraseña",
            value = confirmarContrasena,
            onValueChange = { confirmarContrasena = it; errorConfirmarContrasena = "" },
            placeholder = "Repita su contraseña",
            passwordVisible = confirmarPasswordVisible,
            onToggleVisibility = { confirmarPasswordVisible = !confirmarPasswordVisible },
            error = errorConfirmarContrasena
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Registrarse
        Button(
            onClick = {
                var valid = true

                if (nombres.isBlank()) {
                    errorNombres = "El campo nombres es requerido"
                    valid = false
                }
                if (apellidoPaterno.isBlank()) {
                    errorApellidoPaterno = "El apellido paterno es requerido"
                    valid = false
                }
                if (apellidoMaterno.isBlank()) {
                    errorApellidoMaterno = "El apellido materno es requerido"
                    valid = false
                }
                if (contrasena.isBlank()) {
                    errorContrasena = "La contraseña es requerida"
                    valid = false
                } else if (contrasena.length < 8) {
                    errorContrasena = "La contraseña debe tener al menos 8 caracteres"
                    valid = false
                }
                if (confirmarContrasena.isBlank()) {
                    errorConfirmarContrasena = "Confirme su contraseña"
                    valid = false
                } else if (contrasena != confirmarContrasena) {
                    errorConfirmarContrasena = "Las contraseñas no coinciden"
                    valid = false
                }

                if (valid) {
                    isLoading = true
                    // Generar ID único formato PROF-XXXX
                    val suffix = UUID.randomUUID().toString()
                        .replace("-", "")
                        .take(4)
                        .uppercase()
                    idGenerado = "PROF-$suffix"
                    // TODO: Enviar datos al backend (nombre, apellidos, contraseña, id generado)
                    isLoading = false
                    showSuccessDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = BackgroundWhite,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Registrarse", style = MaterialTheme.typography.labelMedium, color = BackgroundWhite)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = PrimaryDarkBlue)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Diálogo de registro exitoso con ID generado
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = "¡Registro Exitoso!",
                    color = PrimaryDarkBlue,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Tu cuenta ha sido creada correctamente.",
                        color = TextDark,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tu ID único es:",
                        color = TextMedium,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceGray, RoundedCornerShape(8.dp))
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = idGenerado,
                            color = PrimaryDarkBlue,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "⚠ Guarda este ID, lo necesitarás para iniciar sesión.",
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onRegisterSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue)
                ) {
                    Text("Entendido, ir al Login", color = BackgroundWhite)
                }
            },
            containerColor = BackgroundWhite
        )
    }
}

@Composable
private fun RegisterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = TextDark)
        Spacer(modifier = Modifier.height(6.dp))
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
                focusedBorderColor = PrimaryDarkBlue,
            )
        )
    }
}

@Composable
private fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    passwordVisible: Boolean,
    onToggleVisibility: () -> Unit,
    error: String
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = TextDark)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = TextMedium) },
            visualTransformation = if (passwordVisible) VisualTransformation.None
                                   else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility
                                      else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = TextMedium
                    )
                }
            },
            isError = error.isNotEmpty(),
            supportingText = if (error.isNotEmpty()) {
                { Text(error, color = ErrorRed) }
            } else null,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceGray,
                focusedContainerColor = SurfaceGray,
                focusedBorderColor = PrimaryDarkBlue,
            )
        )
    }
}
