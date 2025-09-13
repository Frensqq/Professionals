package com.example.professionals.data.screens.authentication

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.viewModels.CodeAunth
import com.example.professionals.data.screens.Market.MainMarket
import com.example.professionals.data.state.ResultState
import com.example.professionals.namesInterfaceElements
import com.example.professionals.ui.theme.RegularTextTypeONB
import com.example.professionals.ui.theme.buttonType1Text
import com.example.professionals.ui.theme.miniTextButton

@Composable
fun SingIn(navController: NavController){

    val context = LocalContext.current

    ButtonBack(61,navController,"RegAcc")

    Column(modifier = Modifier.padding(top = 121.dp, start = 20.dp, end = 20.dp).fillMaxWidth()) {

        TitleAunth("Привет!", "Заполните Свои Данные")

        Spacer(modifier = Modifier.height(54.dp))

        namesInterfaceElements("Email")

        val email = textFieldAunth("xyz@gmail.com")

        Spacer(modifier = Modifier.height(30.dp))

        namesInterfaceElements("Пароль")

        val password = passwordFieldAunth("xxxxxxxx")

        Text("Востановить", textAlign = TextAlign.End, modifier = Modifier.padding(top = 12.dp).fillMaxWidth().clickable { navController.navigate("ForgPass") }, style = miniTextButton)

        ButtonSingIn(email, password, navController)

        Box(modifier = Modifier.padding(top = 100.dp, bottom = 47.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){

            Row { Text("Вы впервые?", style = RegularTextTypeONB, color = colorResource(R.color.hint))

                Text(" Создать", style = RegularTextTypeONB, color = colorResource(R.color.text), modifier = Modifier.clickable { navController.navigate("RegAcc") })
            }

        }

    }

}

@Composable
fun ButtonSingIn(email: MutableState<String>, password: MutableState<String>, navController: NavController, viewModel: CodeAunth = viewModel()){

    val result = viewModel.resultState.collectAsState()

    Button(onClick = { viewModel.signIn(email.value, password.value)},
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
        Text("Войти", style = buttonType1Text, color = colorResource(R.color.block))
    }

    when (result.value) {
        is ResultState.Error -> {
            ErrorAunth((result.value as ResultState.Error).message, "Ошибка Входа")
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


            val context = LocalContext.current

            val token = viewModel.getCurrentToken()
            val id = viewModel.getId()

            val intent = Intent(context, MainMarket::class.java)
            intent.putExtra("KEY_STRING_1", token)
            intent.putExtra("KEY_STRING_2", id)
            context.startActivity(intent)
        }
    }
}