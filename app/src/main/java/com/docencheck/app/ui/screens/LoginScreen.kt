package com.docencheck.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.docencheck.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorUsuario by remember { mutableStateOf("") }
    var errorContrasena by remember { mutableStateOf("") }
    var errorGeneral by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryDarkBlue)
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "DocenCheck",
                style = MaterialTheme.typography.displayLarge,
                color = BackgroundWhite
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bienvenido, ingrese sus datos para acceder al sistema",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Usuario
        Column(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
            Text(
                text = "Usuario",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it; errorUsuario = ""; errorGeneral = "" },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Correo o ID de usuario", color = TextMedium) },
                isError = errorUsuario.isNotEmpty(),
                supportingText = if (errorUsuario.isNotEmpty()) {
                    { Text(errorUsuario, color = ErrorRed) }
                } else null,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = SurfaceGray,
                    focusedContainerColor = SurfaceGray,
                    focusedBorderColor = PrimaryDarkBlue,
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        Column(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
            Text(
                text = "Contraseña",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it; errorContrasena = ""; errorGeneral = "" },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ingrese su contraseña", color = TextMedium) },
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
                    focusedBorderColor = PrimaryDarkBlue,
                )
            )
        }

        // Error general (credenciales inválidas)
        if (errorGeneral.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorGeneral,
                color = ErrorRed,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Iniciar Sesión
        Button(
            onClick = {
                var valid = true
                if (usuario.isBlank()) {
                    errorUsuario = "El campo usuario es requerido"
                    valid = false
                }
                if (contrasena.isBlank()) {
                    errorContrasena = "El campo contraseña es requerido"
                    valid = false
                }
                if (valid) {
                    isLoading = true
                    // TODO: llamar a la API de autenticación
                    // Por ahora simulamos credenciales inválidas para mostrar el mensaje
                    errorGeneral = "Credenciales inválidas. Verifique su usuario y contraseña."
                    isLoading = false
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
                Text("Iniciar Sesión", style = MaterialTheme.typography.labelMedium, color = BackgroundWhite)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Enlace de Registro
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = TextMedium)) {
                    append("¿No tienes cuenta? ")
                }
                withStyle(SpanStyle(color = PrimaryDarkBlue)) {
                    append("Regístrate aquí")
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable { onNavigateToRegister() }
        )
    }
}
