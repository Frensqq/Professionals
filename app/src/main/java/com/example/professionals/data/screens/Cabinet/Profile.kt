package com.example.professionals.data.screens.Cabinet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.professionals.R
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.ui.theme.TitleTypeMarket
import com.example.professionals.ui.theme.UserName
import com.example.professionals.ui.theme.buttonType1Text

@Composable
fun Profile(id: String, token: String, navController: NavController, viewModel: viewMarket = viewModel()){



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

    if (userList.isNotEmpty()) {

        var names by remember { mutableStateOf(userList[1] ?: "") }
        var surnames by remember { mutableStateOf(userList[6] ?: "") }
        var address by remember { mutableStateOf(userList[3] ?: "") }
        var phone by remember { mutableStateOf(userList[5] ?: "") }


        var check by remember { mutableStateOf(false) }

        Column() {

            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 48.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (!check) {
                    Box(
                        modifier = Modifier
                            .height(44.dp)
                            .width(44.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.hamburger),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.75f)
                                .clickable { navController.navigate("Menu") }
                        )

                    }

                    Text("Профиль", style = TitleTypeMarket, color = colorResource(R.color.text))

                    Box(
                        modifier = Modifier
                            .height(44.dp)
                            .width(44.dp),
                        contentAlignment = Alignment.Center
                    )
                    {

                        IconButton(
                            onClick = { check = !check },
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(colorResource(R.color.background)) // Размер кнопки
                        ) {
                            Icon(
                                bitmap = ImageBitmap.imageResource(R.drawable.redact),
                                modifier = Modifier.fillMaxSize(0.5f),
                                contentDescription = null,
                                tint = colorResource(R.color.accent)
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                check = !check
                                viewModel.UpdateUser(id, token, names, surnames, address, phone)
                                viewModel.ViewUser(id, token)
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(0.6f),
                            shape = RoundedCornerShape(13.dp),
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = colorResource(R.color.disable),
                                containerColor = colorResource(R.color.accent),
                                contentColor = colorResource(R.color.block)
                            )
                        ) {
                            Text(
                                "Сохранить",
                                style = buttonType1Text,
                                color = colorResource(R.color.block)
                            )
                        }
                    }
                }

            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(), verticalArrangement = Arrangement.Center
            ) {


                if (userList.isNotEmpty()) {

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
                                modifier = Modifier.size(96.dp).clip(RoundedCornerShape(48.dp)),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }

                    Text(
                        "${userList[1] ?: ""}  ${userList[6] ?: ""}",
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = UserName,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )


                    names = RowInProfile("Имя", userList[1] ?: "Введите имя", check)

                    surnames = RowInProfile("Фамилия", userList[6] ?: "Введите фамилию", check)

                    address = RowInProfile("Адрес", userList[3] ?: "Введите адрес", check)

                    phone = RowInProfile("Телефон", userList[5] ?: "+7 ***-***-****", check)

                }

            }
        }
    }
    else Text("Ошибка загрузки данных пользователя с сервера")


    Box(modifier = Modifier
        .fillMaxSize()
        .height(116.dp),  contentAlignment = Alignment.BottomCenter){

        IconButton(
            onClick = {navController.navigate("mycart")},
            modifier =  Modifier
                .padding(bottom = 60.dp)
                .height(60.dp)
                .width(60.dp)
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

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .height(35.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {

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
                    onClick = {},
                    modifier =  Modifier
                        .fillMaxHeight() // Размер кнопки
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.people),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        tint = colorResource(R.color.accent)
                    )
                }



            }
        }

    }

}

@Composable
fun RowInProfile(nameCat: String, valueText:String, check: Boolean): String{


    var catText by remember { mutableStateOf(valueText) }

    Column {

    Text(nameCat, modifier = Modifier.padding(top = 20.dp))

    OutlinedTextField(
        catText,
        { catText = it },
        textStyle = TextStyle(fontSize = 14.sp),
        enabled = check,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xff48B2E7),
            unfocusedBorderColor = Color.White,
            focusedContainerColor = Color(0xffeaeaeb),
            unfocusedContainerColor = Color(0xffeaeaeb),
            unfocusedTextColor = colorResource(R.color.hint),
            focusedTextColor = colorResource(R.color.text)
        ),
        trailingIcon =  {Icon(bitmap = ImageBitmap.imageResource(if(check) R.drawable.checkmark else R.drawable.redact), contentDescription = null, modifier = Modifier.size(20.dp))} ,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 20.dp)
            .height(50.dp)
            .fillMaxWidth(),
    )
    }

   return catText
}