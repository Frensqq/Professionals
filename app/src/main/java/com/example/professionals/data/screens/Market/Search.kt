package com.example.professionals.data.screens.Market

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.ui.theme.TitleTypeMarket
import com.example.professionals.ui.theme.buttonType1Text

@Composable
fun Search(id:String,token:String, navController: NavController, viewModel: viewMarket = viewModel()){


    var text by remember { mutableStateOf("") }
    val history = viewModel.history.collectAsState()
    val sneakers = viewModel.sneakers.collectAsState()
    val twoDArray = ConverToArrayArray(sneakers)

    var isInitialized by remember { mutableStateOf(false) }

    var openHistory by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.WatchHistory("iduser = '$id'", token)
            isInitialized = true
        }
    }

    Column {
        Row(
            modifier = Modifier.padding(top = 48.dp, end = 20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonBack(0, navController, "Home")

            Text("Поиск", modifier = Modifier, style = TitleTypeMarket)

            Spacer(modifier = Modifier.width(20.dp))
        }

        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            textStyle = TextStyle(fontSize =  14.sp),
            leadingIcon = {Icon(bitmap = ImageBitmap.imageResource(R.drawable.marker), contentDescription = null, tint = colorResource(R.color.subtextdark))},
            trailingIcon = {Icon(bitmap = ImageBitmap.imageResource(R.drawable.micro), contentDescription = null, tint = colorResource(R.color.subtextdark))},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor= Color(0xff48B2E7),
                unfocusedBorderColor = Color.White ,
                focusedContainerColor = Color(0xffeaeaeb),
                unfocusedContainerColor = Color(0xffeaeaeb),
                unfocusedTextColor = colorResource(R.color.hint),
                focusedTextColor = colorResource(R.color.text)
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions( onSearch ={
                if (text.isNotBlank()) {

                    viewModel.CreateHistory(id,text,token )
                    viewModel.WatchHistory("iduser = '$id'", token)
                    viewModel.outputProducts("(name = '$text')||(name ~ '$text')", token, "+created", 30)
                    text = ""
                    openHistory = false

                }
            } ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp, bottom = 18.dp).height(50.dp).fillMaxWidth(),
            )


        if (openHistory) {
            LazyColumn(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(history.value)
                {

                    Row(modifier = Modifier.padding(10.dp)) {

                        Icon(
                            bitmap = ImageBitmap.imageResource(R.drawable.clock),
                            contentDescription = null,
                            tint = colorResource(R.color.subtextdark)
                        )
                        Text(it.request, modifier = Modifier.padding(start = 12.dp).clickable {
                            val shearch = it.request

                            viewModel.outputProducts(
                                "(name = '$shearch')||(name ~ '$shearch')",
                                token,
                                "+created",
                                30
                            )
                            openHistory = false
                        }, style = buttonType1Text, color = colorResource(R.color.text))
                    }
                }
            }
        }
        else(Column(modifier = Modifier.background(colorResource(R.color.background))
        ) {

            OutputCardSneakers(id,token, twoDArray, navController)
        })


    }
}







