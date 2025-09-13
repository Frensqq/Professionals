package com.example.professionals.data.screens.authentication

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.screens.OnBoards.MainActivityOnBoards
import com.example.professionals.namesInterfaceElements
import com.example.professionals.ui.theme.RaleWayFamily
import com.example.professionals.ui.theme.RegularTextTypeONB
import com.example.professionals.ui.theme.buttonType1Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionals.data.viewModels.CodeAunth
import com.example.professionals.data.state.ResultState


@Composable
fun RegisterAccount(navController: NavController, viewModel: CodeAunth = viewModel() ){

    val context = LocalContext.current
    var check: Boolean by remember { mutableStateOf(false) }

    val result = viewModel.resultState.collectAsState()


    Box(modifier = Modifier
        .padding(top = 66.dp, start = 20.dp)
        .height(44.dp)
        .width(44.dp)){

        IconButton(
            onClick = {context.startActivity(Intent(context, MainActivityOnBoards::class.java))},
            modifier =  Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(colorResource(R.color.background)) // Размер кнопки
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = colorResource(R.color.text)
            )
        }
    }


    Column(modifier = Modifier
        .padding(top = 121.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth()) {

        Box(modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center){

            TitleAunth("Регистрация","Заполните свои данные")

        }


        Spacer(modifier = Modifier.height(54.dp))

        namesInterfaceElements("Ваше имя")

        //val name = remember{mutableStateOf("xxxxxxxx")}
        //val email = remember{mutableStateOf("xyz@gmail.com")}
        //val password = remember{mutableStateOf("xxxxxxxx")}

        val name = textFieldAunth("xxxxxxxx")

        Spacer(modifier = Modifier.height(12.dp))

        namesInterfaceElements("Email")

        val email = textFieldAunth("xyz@gmail.com")

        Spacer(modifier = Modifier.height(12.dp))

        namesInterfaceElements("Пароль")

        val password = passwordFieldAunth("xxxxxxxx")

        Row(modifier = Modifier.padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {


            Box(modifier = Modifier
                .padding(end = 12.dp)
                .width(22.dp).height(22.dp).clip(RoundedCornerShape(6.dp))
                .height(38.dp).background(if (check) colorResource(R.color.accent) else colorResource(R.color.background)), contentAlignment = Alignment.Center){
                IconButton(onClick = { check = !check })
                {
                    Icon(
                        ImageBitmap.imageResource(R.drawable.shield),
                        contentDescription = "Подтверждение персональных данных",
                        modifier = Modifier.fillMaxSize(0.5f),
                        tint = colorResource(R.color.text)
                    )
                }
            }

            val context = LocalContext.current
            val intent = remember { Intent(Intent.ACTION_VIEW, "https://github.com/Frensqq".toUri()) }

            Text("Даю согласие на обработку\n персональных данных", fontFamily = RaleWayFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 17.sp,
                letterSpacing = 0.sp, textDecoration = TextDecoration.Underline , color = colorResource(R.color.hint),
                modifier = Modifier.clickable { context.startActivity(intent) }
            )

        }


        Button(onClick = { viewModel.signUp(email.value, password.value, name.value)},
            enabled = check,
            modifier = Modifier
                .padding(top = 24.dp)
                .height(50.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = colorResource(R.color.disable),
                containerColor = colorResource(R.color.accent),
                contentColor = colorResource(R.color.block)
            )
        ){
            Text("Зарегистрироваться", style = buttonType1Text, color = colorResource(R.color.block))
        }


        Box(modifier = Modifier.padding(top = 100.dp, bottom = 47.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){

            Row { Text("Есть аккаунт?", style = RegularTextTypeONB, color = colorResource(R.color.hint))

                Text(" Войти", style = RegularTextTypeONB, color = colorResource(R.color.text), modifier = Modifier.clickable { navController.navigate("SingIn") })
            }
        }
    }

    when (result.value) {
        is ResultState.Error -> {
            ErrorAunth((result.value as ResultState.Error).message, "Ошибка Регистрации")
        }
        ResultState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center

            ) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize().padding(top= 400.dp), strokeWidth = 10.dp, color = colorResource(R.color.accent))
            }
        }
        ResultState.Initialized -> {

        }
        is ResultState.Success -> {
            navController.navigate("SingIn")
        }
    }


}





