package com.example.professionals.data.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionals.R
import com.example.professionals.data.viewModels.CodeAunth
import com.example.professionals.data.state.ResultState
import com.example.professionals.namesInterfaceElements
import com.example.professionals.ui.theme.buttonType1Text

@Composable
fun Verification(text: String, OtpIdCod: String ,navController: NavController){

    var OtpCode by remember { mutableStateOf(OtpIdCod) }

    ButtonBack(61,navController,"ForgPass")

    Column(modifier = Modifier.padding(top = 130.dp, start = 20.dp, end = 20.dp).fillMaxWidth()) {

        TitleAunth("OTP проверка", "Пожалуйста, Проверьте Свою\n Электронную Почту, Чтобы Увидеть Код\n Подтверждения")

        Spacer(modifier = Modifier.height(16.dp))

        namesInterfaceElements("OTP Код")

        val Otp = SixDigitInput()

        //Text(Otp)

        val temp = CountdownTimer(text)

        if (temp.isNotEmpty()){
            OtpCode = temp
        }

        OtpSingIn(Otp,OtpCode, navController)

    }
}

@Composable
fun CountdownTimer(email: String, viewModel: CodeAunth = viewModel()): String {
    var timeLeft by remember { mutableStateOf(30) } // Начальное время - 30 секунд
    var isRunning by remember { mutableStateOf(true) }
    var otp by remember { mutableStateOf("") }

    otp = viewModel.getOtp()

    LaunchedEffect(key1 = isRunning, key2 = timeLeft) {
        if (isRunning && timeLeft > 0) {
            kotlinx.coroutines.delay(
                1000
            )

            timeLeft-- // Уменьшаем оставшееся время
        } else if (timeLeft == 0) {
            isRunning = false // Таймер завершил отсчёт
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth(0.5f), contentAlignment = Alignment.TopStart) {

            if (!isRunning) {
                TextButton(
                    onClick = {
                        timeLeft = 30
                        isRunning = !isRunning
                        viewModel.sendOtp(email.toString())
                    },
                ) {
                    Text(
                        "Отправить заново",
                        fontSize = 12.sp,
                        color = colorResource(R.color.subtextdark)
                    )
                }

            }
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {

            if (isRunning) Text(
                text = if (timeLeft > 9) "00:" + timeLeft.toString() else "00:0" + timeLeft.toString(),
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 20.dp),
                color = colorResource(
                    R.color.subtextdark
                )
            )
        }
    }
    return otp
}


@Composable
fun OtpSingIn(Otp:String, OtpIdCod:String, navController: NavController,  viewModel: CodeAunth = viewModel()){

    val result = viewModel.resultState.collectAsState()

    Column {

        Button(
            onClick = {viewModel.sigInWithOtp(Otp, OtpIdCod)},
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
        ) {
            Text("Войти", style = buttonType1Text, color = colorResource(R.color.block))
        }
    }
    when (result.value) {
        is ResultState.Error -> {
            ErrorAunth((result.value as ResultState.Error).message, "Некорректный Otp")
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

            val CurretToken = viewModel.getOtp()
            val id = viewModel.getId()
            navController.navigate("CreateNewPass/$CurretToken/$id")

        }
    }
}


@Composable
fun inputOtpCode(){

    var Code by remember { mutableStateOf("") }

    for (i in 0..6){
        OutlinedTextField(
            Code,
            {Code = it},
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
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier.padding(top = 12.dp).height(100.dp).height(50.dp),)
    }


}

@Composable
fun SixDigitInput(): String {
    val focusManager = LocalFocusManager.current
    var codes by remember { mutableStateOf(List(6) { "" }) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(6) { index ->
            OutlinedTextField(
                value = codes[index],
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        val newCodes = codes.toMutableList()
                        newCodes[index] = newValue
                        codes = newCodes

                        if (newValue.isNotEmpty() && index < 5) {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    }
                },
                modifier = Modifier.padding(start = 15.dp, top = 20.dp)
                    .width(45.dp)
                    .height(100.dp).clip(RoundedCornerShape(12.dp)),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor= colorResource(R.color.red),
                    unfocusedBorderColor = Color(0xffeaeaeb),
                    focusedContainerColor = Color(0xffeaeaeb),
                    unfocusedContainerColor = Color(0xffeaeaeb),
                    unfocusedTextColor = colorResource(R.color.text),
                    focusedTextColor = colorResource(R.color.text)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                maxLines = 1
            )
        }
    }

    // Возвращаем объединенную строку из всех символов
    return codes.joinToString("")
}


@Preview
@Composable
fun inputOtpCodeP(){




}






