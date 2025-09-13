package com.example.professionals.data.screens.Market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.ui.theme.TitleTypeMarket

@Composable
fun Popular(navController: NavController){

        Row(
            modifier = Modifier.padding(top = 48.dp, end = 20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonBack(0, navController, "Home")

            Text("Популярное", modifier = Modifier, style = TitleTypeMarket)


            Box(modifier = Modifier
                .padding( start = 20.dp)
                .height(44.dp)
                .width(44.dp)){

                IconButton(
                    onClick = {navController.navigate("Favorite")},
                    modifier =  Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(colorResource(R.color.background)) // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.heart),
                        contentDescription = null,
                        tint = colorResource(R.color.text)
                    )
                }
            }
        }
}