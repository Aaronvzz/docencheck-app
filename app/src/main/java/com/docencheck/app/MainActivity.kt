package com.docencheck.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.docencheck.app.navigation.Routes
import com.docencheck.app.ui.screens.*
import com.docencheck.app.ui.theme.DocenCheckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DocenCheckTheme {
                DocenCheckApp()
            }
        }
    }
}

@Composable
fun DocenCheckApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { role ->
                    when (role) {
                        "ADMINISTRADOR" -> navController.navigate(Routes.ADMIN_HOST) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                        "OPERADOR" -> navController.navigate(Routes.REGISTRAR_ASISTENCIA) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.ADMIN_HOST) {
            AdminNavHost(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTRAR_ASISTENCIA) {
            RegistrarAsistenciaScreen(onBack = { navController.navigateUp() })
        }
    }
}
