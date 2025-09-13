package com.example.professionals.data.screens.Market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.ui.theme.TitleTypeMarket

@Composable
fun Favorite(token: String, iduser: String, navController: NavController, viewModel: viewMarket = viewModel()){

    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.outputProducts("id != 'null'", token, "+created", 30)
            isInitialized = true
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(top = 48.dp, end = 20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonBack(0, navController, "Home")

            Text("Избранное", modifier = Modifier, style = TitleTypeMarket)


            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .height(44.dp)
                    .width(44.dp)
            ) {

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize(0.7f)
                        .clip(CircleShape)
                        .background(colorResource(R.color.background)) // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.heart),
                        contentDescription = null,
                        tint = colorResource(R.color.red)
                    )
                }
            }

        }

        Spacer(modifier = Modifier.fillMaxWidth().height(18.dp))

        val sneakers = viewModel.sneakers.collectAsState()

        val twoDArray = ConverToArrayArray(sneakers)

        OutputCardFavorite(iduser,token, twoDArray, navController)
    }


    Box(modifier = Modifier.fillMaxSize().height(116.dp),  contentAlignment = Alignment.BottomCenter){

        IconButton(
            onClick = {navController.navigate("mycart")},
            modifier =  Modifier.padding(bottom = 60.dp).height(60.dp).width(60.dp)
                .fillMaxSize()
                .clip(CircleShape)
                .background(colorResource(R.color.accent)) // Размер кнопки
        ) {
            Icon(
                bitmap = ImageBitmap.imageResource(R.drawable.bag),
                modifier = Modifier.fillMaxSize(0.5f),
                contentDescription = null,
                tint = colorResource(R.color.block)
            )
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){

            Image(bitmap = ImageBitmap.imageResource(R.drawable.button_hub), contentDescription =null, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth )

            Row(modifier = Modifier.fillMaxWidth().padding(30.dp).height(35.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {

                IconButton(
                    onClick = {navController.navigate("Home")},
                    modifier =  Modifier
                        .fillMaxHeight() // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.home),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        tint = colorResource(R.color.hint)
                    )
                }

                IconButton(
                    onClick = {navController.navigate("Favorite")},
                    modifier =  Modifier
                        .fillMaxHeight() // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.heart),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        tint = colorResource(R.color.accent)
                    )
                }

                Spacer(modifier = Modifier.width(30.dp))

                IconButton(
                    onClick = {},
                    modifier =  Modifier
                        .fillMaxHeight() // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.notification),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        tint = colorResource(R.color.hint)
                    )
                }

                IconButton(
                    onClick = { navController.navigate("profile") },
                    modifier =  Modifier
                        .fillMaxHeight() // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.people),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        tint = colorResource(R.color.hint)
                    )
                }



            }
        }

    }

}