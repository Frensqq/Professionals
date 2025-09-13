package com.example.professionals.data.screens.Market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.namesInterfaceElements
import com.example.professionals.ui.theme.RaleWayFamily
import com.example.professionals.ui.theme.TitleText32N

@Composable
fun Home(iduser:String, token:String, navController: NavController, viewModel: viewMarket = viewModel()){

    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.outputProducts("id != 'null'", token, "+created", 2)
            isInitialized = true
        }
    }

    Column(modifier = Modifier.padding(top = 48.dp)) {

        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            TitleRowHome(navController)

            SecondRowHome(navController)

            Spacer(modifier = Modifier.padding(top = 23.dp))

            namesInterfaceElements("Категории")

            Spacer(modifier = Modifier.padding(top = 15.dp))

        }

        RowСategories(navController)

        Spacer(modifier = Modifier.padding(top = 23.dp))

        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            Row() {
                namesInterfaceElements("Популярное")
                Text(
                    "Все",
                    modifier = Modifier.clickable { navController.navigate("Popular") }
                        .fillMaxWidth(),
                    fontFamily = RaleWayFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = colorResource(R.color.accent),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.padding(top = 34.dp))


        }
        val sneakers = viewModel.sneakers.collectAsState()

        val twoDArray = ConverToArrayArray(sneakers)

        OutputCardSneakers(iduser,token, twoDArray, navController)

        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            Spacer(modifier = Modifier.padding(top = 29.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                namesInterfaceElements("Акции")
                Text("Все",
                    modifier = Modifier.clickable {  }.fillMaxWidth(),
                    fontFamily = RaleWayFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 20.sp ,
                    color = colorResource(R.color.accent),
                    textAlign = TextAlign.End
                )

            }

            Image(bitmap = ImageBitmap.imageResource(R.drawable.reklama), contentDescription =null, modifier = Modifier.padding(top = 20.dp).fillMaxWidth(), contentScale = ContentScale.FillWidth)

        }


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

            RowInBox(navController)
        }

    }
}

@Composable
fun TitleRowHome(navController: NavController){

    Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth().height(44.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {


        Box(modifier = Modifier
                .height(44.dp)
                .width(44.dp),
            contentAlignment = Alignment.Center
        ) {

            Image(bitmap = ImageBitmap.imageResource(R.drawable.hamburger),
                contentDescription =null,
                modifier = Modifier.fillMaxSize(0.75f).clickable { navController.navigate("Menu") }
            )

        }

        Text("Главная", style = TitleText32N, color = colorResource(R.color.text))

        Box(modifier = Modifier
            .height(44.dp)
            .width(44.dp),
            contentAlignment = Alignment.Center
           )
        {

            IconButton(
                onClick = {},
                modifier =  Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(colorResource(R.color.background)) // Размер кнопки
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.bag),
                    modifier = Modifier.fillMaxSize(0.75f),
                    contentDescription = null,
                    tint = colorResource(R.color.text)
                )
            }
        }

    }


}

@Composable
fun SecondRowHome(navController: NavController){

    Row(modifier = Modifier.padding(top = 21.dp, end = 20.dp).fillMaxWidth().height(60.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Button(
            onClick = {navController.navigate("Search")},
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.85f).shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp)
                )
            ,
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.background),
            ),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                Image(bitmap = ImageBitmap.imageResource(R.drawable.marker),contentDescription = null )
                Text(
                    "Поиск",
                    fontFamily = RaleWayFamily,
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 12.sp,
                    color = colorResource(R.color.hint),
                    textAlign = TextAlign.Left
                )
            }
        }

        Box(modifier = Modifier
            .height(52.dp)
            .width(52.dp).padding(start = 14.dp),
            contentAlignment = Alignment.CenterEnd
        )
        {
            IconButton(
                onClick = {},
                modifier =  Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(colorResource(R.color.accent)) // Размер кнопки
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.sliders),
                    modifier = Modifier.fillMaxSize(0.5f),
                    contentDescription = "Назад",
                    tint = colorResource(R.color.block)
                )
            }
        }

    }


}

@Composable
fun RowСategories(navController: NavController){

    var message = "Каталог"

    Row(modifier = Modifier.width(500.dp).horizontalScroll(rememberScrollState())) {

        Button(

            onClick = { navController.navigate("CatalogSneakers/$message")},
            modifier = Modifier.width(125.dp).padding(start = 20.dp).shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(13.dp)
                ),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.background),
            ),
        ) {
                Text(
                    "Все",
                    fontFamily = RaleWayFamily,
                    fontSize = 12.sp,
                    color = colorResource(R.color.hint),
                    textAlign = TextAlign.Center
                )
        }

        Button(
            onClick = {
                message = "Outdoor"
                navController.navigate("CatalogSneakers/$message")},
            modifier = Modifier.padding(start = 16.dp).width(120.dp).shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(13.dp)
                ),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.background),
            ),
        ) {
            Text(
                "Outdoor",
                fontFamily = RaleWayFamily,
                fontSize = 12.sp,
                color = colorResource(R.color.hint),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { message = "Tennis"
                navController.navigate("CatalogSneakers/$message")},
            modifier = Modifier.padding(start = 16.dp).width(120.dp).shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(13.dp)
                ),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.background),
            ),
        ) {
            Text(
                "Tennis",
                fontFamily = RaleWayFamily,
                fontSize = 12.sp,
                color = colorResource(R.color.hint),
                textAlign = TextAlign.Center
            )
        }

        Button(

            onClick = { message = "Running"
                navController.navigate("CatalogSneakers/$message")},
            modifier = Modifier.padding(start = 16.dp, end =  20.dp).width(120.dp).shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(13.dp)
                ),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.background),
            ),
        ) {
            Text(
                "Running",
                fontFamily = RaleWayFamily,
                fontSize = 12.sp,
                color = colorResource(R.color.hint),
                textAlign = TextAlign.Center
            )
        }
    }


}

@Composable
fun RowInBox(navController: NavController){


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
                tint = colorResource(R.color.accent)
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
                tint = colorResource(R.color.hint)
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