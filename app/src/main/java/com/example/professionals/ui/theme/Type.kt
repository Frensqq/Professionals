package com.example.professionals.ui.theme

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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


@Composable
fun RaleWayTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = RaleWayTypography,
        content = content
    )
}

val PreviewText = TextStyle(
    fontFamily = com.example.professionals.ui.theme.RaleWayFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 30.sp,
    lineHeight = 37.5.sp,
    textAlign = TextAlign.Center,
    letterSpacing = 0.sp,
)

val RegularTextTypeONB = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(400),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
    textAlign = TextAlign.Center

)

val miniTextButton = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,

)

val TitleText32N = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
    textAlign = TextAlign.Center
)

val namesInterfaceElements = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
    textAlign = TextAlign.Start
)


val buttonType1Text = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(400),
    fontSize = 14.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
    textAlign = TextAlign.Center
)


val Cart = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(400),
    fontSize = 16.sp,
    lineHeight = 16.sp ,
    textAlign = TextAlign.Start

)

val TitleTypeMarket = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(600),
    fontSize = 16.sp,
    lineHeight = 20.sp ,
    letterSpacing = 0.sp,
    textAlign = TextAlign.Center

)

val infoDetails = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(400),
    fontSize = 14.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,

)


val UserName = TextStyle(

    fontFamily = RaleWayFamily,
    fontWeight = FontWeight(400),
    fontSize = 20.sp,
    lineHeight = 20.sp ,
    letterSpacing = 0.sp,

)

