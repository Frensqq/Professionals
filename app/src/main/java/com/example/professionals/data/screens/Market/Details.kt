package com.example.professionals.data.screens.Market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.professionals.R
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.data.viewModels.viewMarketCart
import com.example.professionals.data.screens.OnBoards.ui.theme.TitleDetails
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.ui.theme.RaleWayFamily
import com.example.professionals.ui.theme.RegularTextTypeONB
import com.example.professionals.ui.theme.TitleTypeMarket
import com.example.professionals.ui.theme.buttonType1Text
import com.example.professionals.ui.theme.infoDetails


@Composable
fun Details(idSneakers:String, name:String, cost:String, gender:String, info:String, collectionId:String, favorites:String, incart:String, token:String, id:String, navController: NavController, viewModel:viewMarket = viewModel(), viewModelcart: viewMarketCart = viewModel()){

    var loveHeart by remember { mutableStateOf(false) }
    var thisInCart by remember { mutableStateOf(false) }

    if(favorites.length > 3){
        loveHeart = true
    }

    if (incart.length > 3) {
        thisInCart = true
    }

    Column {
        Row(
            modifier = Modifier.padding(top = 48.dp, end = 20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonBack(0, navController, "Home")

            Text("Sneaker", modifier = Modifier, style = TitleTypeMarket)


            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .height(44.dp)
                    .width(44.dp)
            ) {

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(colorResource(R.color.background)) // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.bag),
                        contentDescription = null,
                        tint = colorResource(R.color.text)
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            //Text( collectionId, style = TitleDetails,modifier = Modifier.padding(top=5.dp))
            //Text( idSneakers, style = TitleDetails,modifier = Modifier.padding(top=5.dp))
            Text(name , style = TitleDetails,  modifier = Modifier.padding(top=5.dp).fillMaxWidth(0.75f))
            var curretGender = ""

            if (gender=="male"){
                curretGender="Men’s Shoes"
            }
            else{
                curretGender="Girl’s Shoes"
            }
            Text(curretGender , style = RegularTextTypeONB,modifier = Modifier.padding(top=5.dp), color = colorResource(R.color.hint))

            Text("₽" + cost, style = TitleDetails, modifier = Modifier.padding(top=5.dp))

            imageoutput(collectionId, idSneakers, token)

            var MaxLines = remember { mutableStateOf(3) }

            Text(info, style = infoDetails,  modifier = Modifier.padding(top=33.dp), maxLines = MaxLines.value, color = colorResource(R.color.hint), overflow = TextOverflow.Ellipsis)

            Row(modifier = Modifier.padding(top = 10.dp).fillMaxWidth()) {
                 Text("Подробнее", modifier = Modifier.fillMaxWidth().clickable(onClick = {
                     if (MaxLines.value < 10) MaxLines.value = 15
                     else MaxLines.value = 1
                 }), style = buttonType1Text, color = colorResource(R.color.accent), textAlign = TextAlign.End)

            }

            Box(modifier =Modifier.padding(bottom = 40.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(
                        onClick = {
                            if (!loveHeart) {
                                viewModel.addFavourites(id, idSneakers, token)
                                viewModel.viewFavorites(
                                    "(iduser = '$id')&&(idsneakers = '${idSneakers}')",
                                    token,
                                    "+created",
                                    150
                                )
                                loveHeart = true
                                //navController.navigate("mycart")

                            } else {
                                viewModel.delFavorites(favorites, token)
                                loveHeart = false
                            }
                        },
                        modifier = Modifier
                            .padding(start = 9.dp, top = 9.dp)
                            .height(25.dp)
                            .width(25.dp)
                            .clip(CircleShape)
                            .background(colorResource(R.color.background))
                    ) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(R.drawable.heart),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = null,
                            tint = if (loveHeart) colorResource(R.color.red) else colorResource(R.color.text)
                        )
                    }

                    Button(
                        onClick = {
                            if (!(thisInCart)) {
                                viewModelcart.addtocart(id, idSneakers, token)
                                viewModelcart.viewCart(
                                    "(iduser = '$id')&&(idsneakers = '$idSneakers')",
                                    token,
                                    "+created",
                                    150
                                )
                                thisInCart = true
                                navController.navigate("mycart")

                            } else {
                                thisInCart = false
                                viewModelcart.delCart(incart, token)

                            }

                        },
                        modifier = Modifier
                            .height(50.dp).padding(start = 40.dp)
                            .fillMaxWidth(0.85f).shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(16.dp)
                            )
                        ,
                        shape = RoundedCornerShape(13.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.accent),
                            contentColor = Color.White
                        ),
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Image(bitmap = ImageBitmap.imageResource(R.drawable.bag),contentDescription = null)
                            Text(
                                text = if (thisInCart) "Добавлено" else "В корзину",
                                fontFamily = RaleWayFamily,
                                modifier = Modifier.padding(start = 12.dp),
                                fontSize = 12.sp,
                                color = colorResource(R.color.block),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

        }

    }
}



@Composable
fun imageoutput(collectionId:String, id: String, token: String,viewModel: viewMarket = viewModel()){


    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.OutputImageProducts(id, "id != 'null'", token)
            isInitialized = true
        }
    }
    val listImage = viewModel.sneakersImage.collectAsState().value

    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.padding(top = 30.dp)) {
        // Основное изображение
        if (listImage.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(viewModel.getImage(collectionId, id, listImage[selectedIndex]))
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)).background(Color.White),
                contentScale = ContentScale.FillWidth,
            )
        }
        // Миниатюры
        if (listImage.isNotEmpty()) {
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {

                for (i in 0..listImage.size-1) {

                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {selectedIndex = i}.clip(shape = RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(viewModel.getImage(collectionId, id, listImage[i]))
                                .size(Size.ORIGINAL)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp).clip(RoundedCornerShape(4.dp)).background(Color.White),
                                contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
    }
}
