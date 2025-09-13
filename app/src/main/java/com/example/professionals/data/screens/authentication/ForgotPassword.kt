package com.example.professionals.data.screens.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.viewModels.CodeAunth
import com.example.professionals.data.state.ResultState
import com.example.professionals.ui.theme.RegularTextTypeONB
import com.example.professionals.ui.theme.buttonType1Text
import kotlinx.coroutines.delay

@Composable
fun ForgotPassword(navController: NavController){

    ButtonBack(61,navController,"SingIn")

    Column(modifier = Modifier.padding(top = 130.dp, start = 20.dp, end = 20.dp).fillMaxWidth()) {

        TitleAunth("Забыл Пароль", "Введите Свою Учетну Запись Для Сброса")

        Spacer(modifier = Modifier.height(40.dp))

        val email = textFieldAunth("ly4dov.s@yandex.ru")

        ButtonForgot(email, navController)

    }
}

@Composable
fun ButtonForgot(email: MutableState<String>, navController: NavController, viewModel: CodeAunth = viewModel()){

    val result = viewModel.resultStateEmail.collectAsState()

    Button(onClick = { viewModel.sendOtp(email.value)},
        modifier = Modifier
            .padding(top = 40.dp)
            .height(50.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = colorResource(R.color.disable),
            containerColor = colorResource(R.color.accent),
            contentColor = colorResource(R.color.block)
        )
    ){
        Text("Отправить", style = buttonType1Text, color = colorResource(R.color.block))
    }

    var otp = viewModel.getOtp()

    when (result.value) {
        is ResultState.Error -> {
            ErrorAunth((result.value as ResultState.Error).message, "Некорректный Email")
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

            nextVerifi(email, otp, navController)

        }
    }
}

@Composable
fun nextVerifi(email:MutableState<String> ,otp:String, navController: NavController){


    val openAlert = remember { mutableStateOf(true)}


    MassageEmail()


    LaunchedEffect(openAlert.value) {
        if (openAlert.value) {
            delay(5000) // 20 секунд
            openAlert.value = false
        }
    }

    if (!openAlert.value){
        navController.navigate("Verification/${email.value}/$otp")
    }


}

@Composable
fun MassageEmail(){

        AlertDialog(
            onDismissRequest = { true },
            containerColor = Color.White,
            icon = {
                Icon(
                    ImageBitmap.imageResource(R.drawable.email),
                    contentDescription = "Информация о приложении",
                    tint = Color(0xFF48B2E7),
                    modifier = Modifier.height(30.dp).width(30.dp)
                )
            },
            title = {
                Text(
                    text = "Проверьте Ваш Email",
                    fontSize = 16.sp,
                    color = Color(0xff2B2B2B),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Text(
                    "Мы отправили код восстановления пароля на вашу электронную почту.",
                    style = RegularTextTypeONB,
                    color = Color(0xff707B81),
                    modifier = Modifier.fillMaxWidth(),
                    )
            },
            confirmButton = {

                Spacer(modifier = Modifier.height(2.dp))
            }
        )
    }

