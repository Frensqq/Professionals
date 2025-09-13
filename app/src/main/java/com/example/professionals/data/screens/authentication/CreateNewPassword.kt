package com.example.professionals.data.screens.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.viewModels.CodeAunth
import com.example.professionals.data.state.ResultState
import com.example.professionals.namesInterfaceElements
import com.example.professionals.ui.theme.buttonType1Text

@Composable
fun CreateNewPassword(token:String,id:String, navController: NavController ){


    ButtonBack(61,navController,"ForgPass")

    Column(modifier = Modifier.padding(top = 130.dp, start = 20.dp, end = 20.dp).fillMaxWidth()) {

        TitleAunth("Задать Новый Пароль", "Установите Новый Пароль Для Входа В\n Вашу Учетную Запись")

        Spacer(modifier = Modifier.height(38.dp))
        namesInterfaceElements("Старый пароль")

        val oldPass = passwordFieldAunth("000000")

        namesInterfaceElements("Пароль")

        val password = passwordFieldAunth("")

        Spacer(modifier = Modifier.height(8.dp))

        namesInterfaceElements("Подтверждение пароля")

        val confirmPass = passwordFieldAunth("000000")

        CreateNewPasswordButton(token,id, oldPass.value, password.value, confirmPass.value, navController)


    }
}

@Composable
fun CreateNewPasswordButton(token: String,id: String,oldPass:String, password: String, confirmPass: String, navController: NavController, viewModel: CodeAunth= viewModel()){

    Button(onClick = {viewModel.changePassword(id, token,oldPass, password, confirmPass)},
        modifier = Modifier
            .padding(top = 24.dp)
            .height(50.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = colorResource(R.color.disable),
            containerColor = colorResource(R.color.accent),
            contentColor = colorResource(R.color.block))
    ) {
        Text("Войти", style = buttonType1Text, color = colorResource(R.color.block))
    }

    val result = viewModel.resultState.collectAsState()

    when (result.value) {
        is ResultState.Error -> {
            ErrorAunth((result.value as ResultState.Error).message, "Функция не работает")
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

            Text("Смена пароля завершена")
            navController.navigate("SingIn")

        }
    }

}

