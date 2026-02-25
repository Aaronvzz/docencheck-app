package com.docencheck.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Nota: agrega los archivos .ttf en res/font/ con estos nombres
// dm_sans_bold.ttf, red_hat_text_regular.ttf, red_hat_display_regular.ttf

val RedHatText = FontFamily.Default   // Reemplazar con FontFamily(Font(R.font.red_hat_text_regular))
val RedHatDisplay = FontFamily.Default
val DmSans = FontFamily.Default

val Typography = Typography(
    // Título principal de pantalla (DM Sans Black 44sp)
    displayLarge = TextStyle(
        fontFamily = DmSans,
        fontWeight = FontWeight.Black,
        fontSize = 44.sp
    ),
    // Subtítulo / nombre de módulo (Red Hat Display 27sp)
    headlineMedium = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp
    ),
    // Título de sección (Red Hat Text Medium 24sp)
    titleMedium = TextStyle(
        fontFamily = RedHatText,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    // Cuerpo principal (Red Hat Text Regular 20sp)
    bodyLarge = TextStyle(
        fontFamily = RedHatText,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    // Texto secundario / hints (Red Hat Text Light 20sp)
    bodyMedium = TextStyle(
        fontFamily = RedHatText,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),
    // Labels pequeños
    labelMedium = TextStyle(
        fontFamily = RedHatText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
)
