package com.example.professionals.data.screens.Market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.professionals.R
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.data.viewModels.viewMarketCart
import com.example.professionals.data.model.market.LoveList
import com.example.professionals.data.model.market.SneakersList
import com.example.professionals.ui.theme.Cart

class MainMarket : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val token = intent.getStringExtra("KEY_STRING_1") ?: ""
            val id = intent.getStringExtra("KEY_STRING_2") ?: ""

            MarketNavigation(token, id)

        }
    }
}

@Composable
fun listFavorite(filter:String, token:String, viewModel: viewMarket = viewModel()): Array<Array<String>>{

    viewModel.viewFavorites(filter, token, "+created", 150)

    val favorite = viewModel.favorite.collectAsState()

    return ConverToFavoriteArrayArray(favorite)
}

@Composable
fun listCart(filter:String, token:String, viewModel: viewMarketCart = viewModel()): Array<Array<String>>{

    viewModel.viewCart(filter, token, "+created", 150)

    val inCarts = viewModel.Carts.collectAsState()

    val twoDArray = remember(inCarts.value) {
        inCarts.value.map { inCart ->
            arrayOf(
                inCart.id,
                inCart.idsneakers
            )
        }.toTypedArray()
    }
    return twoDArray
}




@Composable
fun productСard(favorite1: Int, Cart1: Int, listFavorite: Array<Array<String>>, listCart: Array<Array<String>>, iduser:String, token: String, imageCart: Int, sneakers:Array<String>, navController: NavController, viewModel: viewMarket = viewModel(), viewModelcart: viewMarketCart = viewModel()) {


    var loveHeart by remember { mutableStateOf(false) }
    var thisInCart by remember { mutableStateOf(false) }
    var CurretidFavorite by remember { mutableStateOf("") }
    var CurretidInCarts by remember { mutableStateOf("") }

    if (favorite1 != (-1)) {
        loveHeart = true
        CurretidFavorite = listFavorite[favorite1][0]
    }
    if (Cart1 != (-1)) {
        thisInCart = true
        CurretidInCarts = listCart[Cart1][0]
    }

    var isInitialized by remember { mutableStateOf(false) }

    val idsneakers = sneakers[0]

    Box(
        modifier = Modifier.padding(top = 20.dp)
            .fillMaxWidth().height(200.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable {  navController.navigate("Details/${sneakers[0]}/${sneakers[1]}/${sneakers[2]}/${sneakers[5]}/${sneakers[3]}/${sneakers[7]}/${CurretidFavorite}/${CurretidInCarts}")}) {

            Column {
                Box(modifier = Modifier.padding(9.dp)) {

                    IconButton(
                        onClick = {
                            if (!loveHeart) {
                                viewModel.addFavourites(iduser, sneakers[0], token)
                                viewModel.viewFavorites(
                                    "(iduser = '$iduser')&&(idsneakers = '${sneakers[0]}')",
                                    token,
                                    "+created",
                                    150
                                )
                                loveHeart = true

                            } else {
                                viewModel.delFavorites(CurretidFavorite, token)
                                loveHeart = false
                            }
                        },
                        modifier = Modifier
                            .padding(start = 9.dp, top = 9.dp)
                            .height(20.dp)
                            .width(20.dp)
                            .clip(CircleShape)
                            .background(colorResource(R.color.background)) // Размер кнопки
                    ) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(R.drawable.heart),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = null,
                            tint = if (loveHeart) colorResource(R.color.red) else colorResource(R.color.text)
                        )
                    }


                    Box(
                        Modifier
                            .padding(top = 21.dp, end = 15.dp)
                            .height(70.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (sneakers[8].isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewModel.getImage(sneakers[7], sneakers[0], sneakers[8]))
                                    .size(Size.ORIGINAL)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.FillWidth,
                            )
                        }
                        else {
                            Image(
                                bitmap = ImageBitmap.imageResource(imageCart),
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
                Column(modifier = Modifier.padding(top = 5.dp, start = 15.dp)) {


                    Text(
                            sneakers[4], style = Cart, color = colorResource(R.color.accent)
                    )

                    Text(sneakers[1], style = Cart, color = colorResource(R.color.hint))


                    Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                        )
                    {

                        Text(
                            "₽" + sneakers[2],
                            style = Cart,
                            color = colorResource(R.color.hint),
                            modifier = Modifier.padding(top = 5.dp)
                        )


                        Box( modifier = Modifier
                            .fillMaxWidth(0.25f)) {

                            IconButton(
                                onClick = {
                                    if (!(thisInCart)) {
                                        viewModelcart.addtocart(iduser, sneakers[0], token)
                                        viewModelcart.viewCart(
                                            "(iduser = '$iduser')&&(idsneakers = '${sneakers[0]}')",
                                            token,
                                            "+created",
                                            150
                                        )
                                        thisInCart = true

                                    } else {
                                        thisInCart = false
                                        viewModelcart.delCart(CurretidInCarts, token)

                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (!thisInCart) {
                                    Image(bitmap = ImageBitmap.imageResource(R.drawable.plus),
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentDescription = null,
                                        alignment = Alignment.BottomEnd
                                    )
                                }
                                else {
                                    Image(

                                        bitmap = ImageBitmap.imageResource(R.drawable.poducts),
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentDescription = null,
                                        alignment = Alignment.BottomEnd
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }





@Composable
fun ConverToFavoriteArrayArray(favorits:  State<List<LoveList>>): Array<Array<String>>{

    val twoDArray = remember(favorits.value) {
        favorits.value.map { favorit ->
            arrayOf(
                favorit.id,
                favorit.idsneakers
            )
        }.toTypedArray()
    }
    return twoDArray
}

@Composable
fun ConverToArrayArray(sneakers:  State<List<SneakersList>>): Array<Array<String>>{

    val twoDArray = remember(sneakers.value) {
        sneakers.value.map { sneaker ->
            arrayOf(
                sneaker.id,          // [0]
                sneaker.name,        // [1]
                sneaker.cost,        // [2]
                sneaker.info,        // [3]
                sneaker.category,    // [4]
                sneaker.gender,      // [5]
                sneaker.watch,       // [6]
                sneaker.collectionId, // [7]
                sneaker.Titleimg //[8]
            )
        }.toTypedArray()
    }
    return twoDArray
}

@Composable
fun isFaforite(idsneakers:String, favoriteList: Array<Array<String>>): Int{

    for(i in favoriteList.indices){
        if(idsneakers == favoriteList[i][1]){
            return i
        }
    }
    return -1
}

@Composable
fun isInCart(idsneakers:String, cartList: Array<Array<String>>): Int{

    for(i in cartList.indices){
        if(idsneakers == cartList[i][1]){
            return i
        }
    }
    return -1
}

@Composable
fun OutputCardSneakers(iduser: String, token: String, twoDArray: Array<Array<String>>, navController: NavController) {
    if (twoDArray.isNotEmpty()) {
        val listFavorite = listFavorite("iduser = '$iduser'", token)

        val listCart = listCart("iduser = '$iduser'", token)

        val img = R.drawable.cross

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 15.dp,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            items(twoDArray.size) { index ->
                productСard(
                    isFaforite(twoDArray[index][0], listFavorite),
                    isFaforite(twoDArray[index][0], listCart),
                    listFavorite,
                    listCart,
                    iduser,
                    token,
                    img,
                    twoDArray[index],
                    navController
                )
            }
        }
    }
}

@Composable
fun OutputCardFavorite(iduser: String, token: String, twoDArray: Array<Array<String>>, navController: NavController) {
    if (twoDArray.isNotEmpty()) {
        val listFavorite = listFavorite("iduser = '$iduser'", token)

        val listCart = listCart("iduser = '$iduser'", token)
        val img = R.drawable.cross

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 0.dp,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            items(twoDArray.size) { index ->
                val i = isFaforite(twoDArray[index][0], listFavorite)

                if (i!=(-1)){
                    productСard(
                        i,
                        isFaforite(twoDArray[index][0], listCart),
                        listFavorite,
                        listCart,
                        iduser,
                        token,
                        img,
                        twoDArray[index],
                        navController
                    )
                }
            }
        }
    }
}