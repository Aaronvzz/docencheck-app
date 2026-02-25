package com.docencheck.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.docencheck.app.navigation.Routes
import com.docencheck.app.ui.theme.*
import kotlinx.coroutines.launch

private data class DrawerItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val adminDrawerItems = listOf(
    DrawerItem(Routes.GESTION_DOCENTES,    "Gestión de Docentes",     Icons.Default.People),
    DrawerItem(Routes.ENROLAMIENTO,        "Enrolamiento Facial",     Icons.Default.Face),
    DrawerItem(Routes.GESTION_OPERADORES,  "Gestión de Operadores",   Icons.Default.ManageAccounts),
    DrawerItem(Routes.CONSULTA_ASISTENCIAS,"Consulta de Asistencias", Icons.AutoMirrored.Filled.ListAlt),
    DrawerItem(Routes.GENERACION_REPORTES, "Generación de Reportes",  Icons.Default.BarChart),
)

@Composable
fun AdminNavHost(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()
    val drawerState   = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope         = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AdminDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(Routes.GESTION_DOCENTES) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                onLogout = {
                    scope.launch { drawerState.close() }
                    onLogout()
                }
            )
        }
    ) {
        AdminNavGraph(
            navController  = navController,
            onOpenDrawer   = { scope.launch { drawerState.open() } }
        )
    }
}

@Composable
private fun AdminDrawerContent(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet(drawerContainerColor = BackgroundWhite) {
        // ── Header ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryDarkBlue)
                .statusBarsPadding()
                .padding(vertical = 36.dp, horizontal = 20.dp)
        ) {
            Column {
                Text(
                    text = "DocenCheck",
                    style = MaterialTheme.typography.headlineMedium,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Bienvenido, Administrador",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryMint
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Ítems de navegación ──────────────────────────────
        adminDrawerItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (selected) PrimaryDarkBlue else TextMedium
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) PrimaryDarkBlue else TextDark,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                selected = selected,
                onClick  = { onNavigate(item.route) },
                colors   = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor   = PrimaryMint.copy(alpha = 0.35f),
                    unselectedContainerColor = BackgroundWhite
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        // ── Empuja el botón de logout hacia abajo ────────────
        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(
            modifier  = Modifier.padding(horizontal = 16.dp),
            color     = SurfaceGray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(8.dp))

        // ── Cerrar sesión ────────────────────────────────────
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = ErrorRed
                )
            },
            label = {
                Text(
                    text       = "Cerrar Sesión",
                    color      = ErrorRed,
                    fontWeight = FontWeight.SemiBold
                )
            },
            selected = false,
            onClick  = onLogout,
            colors   = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = BackgroundWhite
            ),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun AdminNavGraph(
    navController: NavHostController,
    onOpenDrawer:  () -> Unit
) {
    NavHost(
        navController    = navController,
        startDestination = Routes.GESTION_DOCENTES
    ) {
        composable(Routes.GESTION_DOCENTES) {
            GestionDocentesScreen(onMenuClick = onOpenDrawer)
        }
        composable(Routes.ENROLAMIENTO) {
            EnrolamientoFacialScreen(onMenuClick = onOpenDrawer)
        }
        composable(Routes.GESTION_OPERADORES) {
            GestionOperadoresScreen(onMenuClick = onOpenDrawer)
        }
        composable(Routes.CONSULTA_ASISTENCIAS) {
            ConsultaAsistenciasScreen(onMenuClick = onOpenDrawer)
        }
        composable(Routes.GENERACION_REPORTES) {
            GeneracionReportesScreen(onMenuClick = onOpenDrawer)
        }
    }
}
