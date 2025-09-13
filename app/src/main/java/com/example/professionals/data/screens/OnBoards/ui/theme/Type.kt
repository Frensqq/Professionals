package com.example.professionals.data.screens.OnBoards.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.professionals.R

val RaleWayFamily = FontFamily(
    Font(R.font.raleway_regular, FontWeight.Normal),
    Font(R.font.raleway_bold, FontWeight.Bold),
    Font(R.font.raleway_medium, FontWeight.Medium),
    Font(R.font.raleway_semibold, FontWeight.SemiBold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

private val RaleWayTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = RaleWayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    // можно определить и другие текстовые стили
)

val PreviewText = TextStyle(
    fontFamily = com.example.professionals.ui.theme.RaleWayFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 30.sp,
    lineHeight = 37.5.sp,
    textAlign = TextAlign.Center
)

val PreviewTextT2 = TextStyle(
    fontFamily = com.example.professionals.ui.theme.RaleWayFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 34.sp,
    lineHeight = 44.sp,
    letterSpacing = 0.sp,
    textAlign = TextAlign.Center
)

val TitleDetails = TextStyle(
    fontFamily = com.example.professionals.ui.theme.RaleWayFamily,
    fontWeight = FontWeight(400),
    fontSize = 26.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.sp,
)