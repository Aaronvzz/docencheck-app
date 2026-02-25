package com.docencheck.app.navigation

object Routes {
    const val LOGIN              = "login"
    const val ADMIN_HOST         = "admin_host"
    const val GESTION_DOCENTES   = "gestion_docentes"
    const val ENROLAMIENTO       = "enrolamiento_facial"
    const val REGISTRAR_ASISTENCIA = "registrar_asistencia"
    const val GESTION_OPERADORES = "gestion_operadores"
    const val CONSULTA_ASISTENCIAS = "consulta_asistencias"
    const val GENERACION_REPORTES  = "generacion_reportes"
    const val CONFIG_INICIAL       = "configuracion_inicial"
}

enum class UserRole { ADMINISTRADOR, OPERADOR }
