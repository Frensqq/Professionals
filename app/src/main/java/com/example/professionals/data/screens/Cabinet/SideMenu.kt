package com.example.professionals.data.screens.Cabinet

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.professionals.data.screens.authentication.MainAuthentication
import com.example.professionals.ui.theme.UserName

@Composable
fun SideMenu(id: String, token: String, navController: NavController, viewModel: viewMarket = viewModel(  )){

    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.ViewUser(id, token)
            isInitialized = true
        }
    }

    val userData by viewModel.userData.collectAsState()

    val userList = remember(userData) {
        if (userData != null) {
            arrayOf(
                userData!!.id,            //0
                userData!!.name,          //1
                userData!!.email,         //2
                userData!!.address,       //3
                userData!!.cart,          //4
                userData!!.phoneNumber,         //5
                userData!!.surname,       //6
                userData!!.avatar,        //7
                userData!!.collectionId   //8
            )
        } else {
            emptyArray()
        }
    }



    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.accent))) {

        Box(modifier = Modifier
            .padding(top = 97.dp, bottom = 97.dp)
            .fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.homepage),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxHeight(),
            )
        }

        Column(modifier = Modifier.padding(top = 68.dp,start = 20.dp, end = 20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

            if (userList.isNotEmpty()) {

                if (userList[7]!!.length > 0) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                viewModel.getImage(
                                    userList[8]!!,
                                    userList[0]!!,
                                    userList[7]!!
                                )
                            )
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(96.dp)
                            .clip(RoundedCornerShape(48.dp)),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.noimagefound),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 70.dp, start = 16.dp)
                            .size(96.dp)
                            .clip(RoundedCornerShape(48.dp)),
                    )
                }



                Text(
                    "${userList[1] ?: ""}  ${userList[6] ?: ""}",
                    modifier = Modifier.padding(top = 15.dp, start = 16.dp),
                    style = UserName,
                    color = Color.White
                )

                Row(
                    modifier = Modifier.padding(top = 30.dp)
                        .clickable(onClick = { navController.navigate("profile") }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.people),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Профиль", modifier = Modifier.padding(start = 20.dp), color = Color.White)
                }
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                        .clickable(onClick = { navController.navigate("mycart") }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.bag),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Корзина", modifier = Modifier.padding(start = 20.dp), color = Color.White)
                }
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                        .clickable(onClick = { navController.navigate("Favorite") }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.heart),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Избранное",
                        modifier = Modifier.padding(start = 20.dp),
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                        .clickable(onClick = { navController.navigate("history") }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.delivery),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Заказы", modifier = Modifier.padding(start = 20.dp), color = Color.White)
                }
                Row(
                    modifier = Modifier.padding(top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.notification),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Уведомления",
                        modifier = Modifier.padding(start = 20.dp),
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.settings),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Настройки",
                        modifier = Modifier.padding(start = 20.dp),
                        color = Color.White
                    )
                }

                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.linestwo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                val context = LocalContext.current

                Row(
                    modifier = Modifier.padding(top = 30.dp).clickable(onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                MainAuthentication::class.java
                            )
                        )
                    }), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.exit),
                        tint = colorResource(R.color.block),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Выйти", modifier = Modifier.padding(start = 20.dp), color = Color.White)
                }
            }
        }
    }
}

