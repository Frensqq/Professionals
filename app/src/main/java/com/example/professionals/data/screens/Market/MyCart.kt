package com.example.professionals.data.screens.Market


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.professionals.data.viewModels.viewMarketCart
import com.example.professionals.data.viewModels.viewMarketOrders
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.ui.theme.Cart
import com.example.professionals.ui.theme.TitleTypeMarket

@Composable
fun MyCart(id:String, token:String, navController: NavController, viewModelCart: viewMarketCart = viewModel(), viewModel: viewMarket = viewModel(), viewMarketOrder: viewMarketOrders = viewModel ()){

    var isInitialized by remember { mutableStateOf(false) }
    var toChecking by remember { mutableStateOf(false) }
    val stringList = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModelCart.viewCart("iduser = '$id'", token,"+created",150)
            viewModel.outputProducts("id != 'null'", token,"+created",150)
            viewModel.ViewUser(id, token)

            isInitialized = true
        }
    }
    val sneakers = viewModel.sneakers.collectAsState()
    val twoDArray = ConverToArrayArray(sneakers)
    val inCarts = viewModelCart.Carts.collectAsState()

    val ArrayInCart = remember(inCarts.value) {
        inCarts.value.map { inCart ->
            arrayOf(
                inCart.id,
                inCart.idsneakers,
                inCart.count.toString()
            )
        }.toTypedArray()
    }

    LaunchedEffect(ArrayInCart) {
        stringList.clear()
        ArrayInCart.forEach { array ->
            stringList.add(array[1])
        }
    }

    val countString = remember(inCarts.value) {
        inCarts.value.joinToString("") { inCart ->
            inCart.count.toString().padStart(2, '0')
        }
    }

    val userData by viewModel.userData.collectAsState()
    val userList = remember(userData) {
        if (userData != null) {
            arrayOf(
                userData!!.id,
                userData!!.name,
                userData!!.email,
                userData!!.address,
                userData!!.cart,
                userData!!.phoneNumber,
                userData!!.surname,
                userData!!.avatar
            )
        } else {
            emptyArray()
        }
    }

    //var count = ArrayInCart.size
    var count = 0
    var globalCost by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(top = 48.dp, end = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonBack(0, navController, "Home")

            Text("Корзина", modifier = Modifier, style = TitleTypeMarket)

            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .height(44.dp)
                    .width(44.dp)
            ) {
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {

            for (i in 0..ArrayInCart.size-1){
                count += ArrayInCart[i][2].toInt()
            }

            if(!toChecking) {
                if ((count % 10 == 1) && (count % 100 != 11) && (count % 1000 != 111)) {
                    Text("$count товар", style = Cart)
                } else if ((count % 10 in 2..4) && (count % 100 !in 12..14)) {
                    Text("$count товара", style = Cart)
                } else {
                    Text("$count товаров", style = Cart)
                }

                if (twoDArray.isNotEmpty()) {

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(1),
                        verticalItemSpacing = 0.dp,
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        modifier = Modifier
                            .fillMaxHeight(0.65f)
                            .fillMaxWidth()
                    ) {
                        items(twoDArray.size) { index ->

                            val i = isFaforite(twoDArray[index][0], ArrayInCart)

                            if (i != (-1)) {

                                Row(
                                    modifier = Modifier.padding(top = 13.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .height(111.dp)
                                            .width(60.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                colorResource(R.color.accent),
                                            ), contentAlignment = Alignment.TopCenter
                                    ) {

                                        Column(
                                            Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {

                                            var localCost = 0
                                            localCost = twoDArray[index][2].toInt()

                                            IconButton(onClick = {
                                                if (ArrayInCart[i][2].toInt() < 100) {
                                                    val countLocal = ArrayInCart[i][2].toInt() + 1
                                                    viewModelCart.UpdateCart(
                                                        ArrayInCart[i][0],
                                                        countLocal
                                                    )
                                                    viewModelCart.viewCart(
                                                        "iduser = '$id'",
                                                        token,
                                                        "+created",
                                                        150
                                                    )
                                                    globalCost += localCost
                                                }
                                            }) {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = "Добавить",
                                                    tint = Color.White
                                                )

                                            }

                                            Text(
                                                ArrayInCart[i][2],
                                                color = Color.White,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )

                                            IconButton(onClick = {
                                                if (ArrayInCart[i][2].toInt() > 1) {
                                                    val countLocal = ArrayInCart[i][2].toInt() - 1
                                                    viewModelCart.UpdateCart(
                                                        ArrayInCart[i][0],
                                                        countLocal
                                                    )
                                                    viewModelCart.viewCart(
                                                        "iduser = '$id'",
                                                        token,
                                                        "+created",
                                                        150
                                                    )
                                                    globalCost -= localCost
                                                }
                                            }) {
                                                Icon(
                                                    bitmap = ImageBitmap.imageResource(R.drawable.minus),
                                                    contentDescription = "Убавить",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(20.dp)
                                                )

                                            }


                                        }

                                    }
                                    OutputRowCart(
                                        token,
                                        twoDArray[index],
                                        id,
                                        ArrayInCart[i],
                                        token
                                    )
                                }
                            }

                        }
                    }
                }
            }
            else{

                var SelectMod by remember { mutableStateOf(false) }

                Box(modifier = Modifier.padding(top = 30.dp, bottom = 25.dp).fillMaxHeight(0.6f).fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White)){
                    Column(modifier = Modifier.padding(20.dp).fillMaxSize()) {

                        var email by remember { mutableStateOf(userList[2]?: "") }
                        var phone by remember { mutableStateOf(userList[5]?: "**-***-***-****") }
                        var address by remember { mutableStateOf(if( userList[3]!!.length>3) userList[3]?: "" else "1082 Аэропорт, Нигерии") }
                        var numCard by remember { mutableStateOf(userList[4]?: "**** **** 0696 4629") }


                        Text("Контактная информация", color = colorResource(R.color.text))

                        Row(modifier = Modifier.padding(top =5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                            if (!SelectMod) {
                                RowInCartEmpty(email, "Email", R.drawable.email)
                            }
                            else {

                                OutlinedTextField(
                                    email,
                                    { email = it},
                                    textStyle = TextStyle(fontSize =  14.sp),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor= Color(0xff48B2E7),
                                        unfocusedBorderColor = Color.White ,
                                        focusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedTextColor = colorResource(R.color.hint),
                                        focusedTextColor = colorResource(R.color.text)
                                    ),
                                    shape = RoundedCornerShape(15.dp),
                                    modifier = Modifier.height(50.dp).fillMaxWidth(0.75f),)


                            }

                            IconButton(onClick = {SelectMod = !SelectMod}) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.redact),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                        }
                        Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                            if (!SelectMod) {
                                RowInCartEmpty(phone, "Телефон", R.drawable.phone)
                            }
                            else {

                                OutlinedTextField(
                                    phone,
                                    { phone = it},
                                    textStyle = TextStyle(fontSize =  14.sp),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor= Color(0xff48B2E7),
                                        unfocusedBorderColor = Color.White ,
                                        focusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedTextColor = colorResource(R.color.hint),
                                        focusedTextColor = colorResource(R.color.text)
                                    ),
                                    shape = RoundedCornerShape(15.dp),
                                    modifier = Modifier.height(50.dp).fillMaxWidth(0.75f),)
                            }
                            IconButton(onClick = {SelectMod = !SelectMod}) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.redact),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Text("Адрес", color = colorResource(R.color.text), modifier = Modifier.padding(top = 5.dp))

                        Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                            if (!SelectMod) {
                                Text(address, color = colorResource(R.color.hint))
                            }
                            else {

                                OutlinedTextField(
                                    address,
                                    { address = it},
                                    textStyle = TextStyle(fontSize =  14.sp),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor= Color(0xff48B2E7),
                                        unfocusedBorderColor = Color.White ,
                                        focusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedTextColor = colorResource(R.color.hint),
                                        focusedTextColor = colorResource(R.color.text)
                                    ),
                                    shape = RoundedCornerShape(15.dp),
                                    modifier = Modifier.height(50.dp).fillMaxWidth(0.75f),)


                            }

                            IconButton(onClick = {SelectMod = !SelectMod}) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.redact),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                        }

                        Image(bitmap = ImageBitmap.imageResource(R.drawable.map), modifier = Modifier.fillMaxWidth(), contentDescription = null, contentScale = ContentScale.FillWidth)

                        Text("Способ оплаты", color = colorResource(R.color.text))

                        Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(
                                colorResource(R.color.background)), contentAlignment = Alignment.Center)
                            {
                                Image(
                                    bitmap = ImageBitmap.imageResource(R.drawable.cartimg),
                                    contentDescription = null,
                                    Modifier.fillMaxSize(0.8f),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                            if (!SelectMod) {
                                Column(
                                    modifier = Modifier.padding(start = 10.dp).fillMaxWidth(0.7f)
                                ) {
                                    Text("Card")
                                    Text(numCard)
                                }
                            }
                            else {

                                OutlinedTextField(
                                    numCard,
                                    { numCard = it},
                                    textStyle = TextStyle(fontSize =  14.sp),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor= Color(0xff48B2E7),
                                        unfocusedBorderColor = Color.White ,
                                        focusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedContainerColor = Color(0xffeaeaeb),
                                        unfocusedTextColor = colorResource(R.color.hint),
                                        focusedTextColor = colorResource(R.color.text)
                                    ),
                                    shape = RoundedCornerShape(15.dp),
                                    modifier = Modifier.height(50.dp).fillMaxWidth(0.75f),)


                            }

                            IconButton(onClick = {SelectMod = !SelectMod}) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.redact),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

            }

        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){

        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 400.dp)
            .fillMaxHeight(0.7f)
            .background(if (!toChecking) Color.White else colorResource(R.color.whiteBack))
        ) {

            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                ButtonMenu(ArrayInCart,twoDArray, toChecking )

                Button(onClick = { if (!toChecking) {
                    toChecking = true
                }
                    else{
                    if (userList.isNotEmpty()) {
                        viewMarketOrder.CreateOrders(token, id, stringList, countString, userList[2]?:"",userList[5]?:"", userList[3]?:"", userList[4]?:"")
                    }

                }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .heightIn(min = 50.dp, max = 170.dp)
                    .clip(RoundedCornerShape(12.dp)),colors = ButtonDefaults.buttonColors(
                    containerColor =  colorResource(R.color.accent)), shape = RoundedCornerShape(8.dp)
                ) {
                    if (!toChecking) Text("Оформить заказ", style = Cart, color = Color.White)
                    else Text("Подтвердить", style = Cart, color = Color.White)
                }

            }
        }

    }

}

@Composable
fun OutputRowCart(incart:String, Array:Array<String>, id: String, inCart:Array<String>, token: String, viewModelCart: viewMarketCart = viewModel(), viewModel: viewMarket = viewModel()) {

    var thisInCart by remember { mutableStateOf(false) }

    if (incart.length > 3) {
        thisInCart = true
    }
    Box(
        modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .height(111.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {

        Row(
            Modifier
                .padding(start = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            if (Array[8].isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(viewModel.getImage(Array[7], Array[0], Array[8]))
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(R.color.background)),
                    contentScale = ContentScale.FillWidth,
                )
            } else {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.black),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }

            Column(modifier = Modifier.padding(start = 30.dp)) {

                Text(Array[1], style = Cart)
                Text("₽ " + Array[2], style = Cart)

            }
        }
    }

    Box(
        modifier = Modifier
            .padding(start = 10.dp)
            .height(111.dp)
            .width(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                colorResource(R.color.red),
            ), contentAlignment = Alignment.TopCenter
    ) {
        IconButton(onClick = {
            viewModelCart.delCart(inCart[0], token)
            viewModelCart.viewCart("iduser = '$id'", token, "+created", 150)
        }, modifier = Modifier.fillMaxSize()) {
            Icon(
                Icons.Default.Delete, contentDescription = "Удалить", tint = Color.White,
            )
        }
    }
}


@Composable
fun ButtonMenu(  ArrayInCart:Array<Array<String>>, twoDArray:Array<Array<String>>, toChecking:Boolean){

    var globalCost =0
    var count =0

    for (i in 0..ArrayInCart.size-1){

        for (j in 0 ..twoDArray.size-1){
            if (twoDArray[j][0]  ==ArrayInCart[i][1]){
                globalCost += (ArrayInCart[i][2].toInt()*twoDArray[j][2].toInt())
                count+=ArrayInCart[i][2].toInt()
            }
        }
    }

    count*=100

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Сумма", style = Cart, color = colorResource(R.color.subtextdark))
                Text("₽"+globalCost.toString(), style = Cart, color = colorResource(R.color.text))

    }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Доставка", style = Cart,color = colorResource(R.color.subtextdark))
                Text("₽"+count.toString(), style = Cart, color = colorResource(R.color.text))
            }
            Image(bitmap = ImageBitmap.imageResource(R.drawable.lines), modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth, contentDescription = null)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Итого", style = Cart, color = colorResource(R.color.text))
                Text("₽"+(globalCost+count).toString(), style = Cart, color = colorResource(R.color.accent))
            }

}


@Composable
fun RowInCartEmpty(texttitle:String, text:String, leadingIcon:Int):String {
    Row(
        modifier = Modifier.height(45.dp).fillMaxWidth(0.6f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.size(45.dp).clip(RoundedCornerShape(12.dp)).background(
                colorResource(R.color.background)
            ), contentAlignment = Alignment.Center
        )
        {
            Icon(
                bitmap = ImageBitmap.imageResource(leadingIcon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(
            modifier = Modifier.padding(start = 12.dp).height(40.dp).fillMaxHeight()
                .fillMaxSize(0.75f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(texttitle)
            Text(text)
        }

    }
    val string = " "
    return string
}