package com.example.professionals.data.screens.OnBoards

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.screens.authentication.MainAuthentication
import com.example.professionals.data.screens.authentication.RegisterAccount
import com.example.professionals.ui.theme.buttonType1Text



@SuppressLint("NotConstructor")
    @Composable
    fun OnBoard3(navController: NavController, text: String? = null){

    val context = LocalContext.current


    Column(
            modifier = Modifier
                .fillMaxSize().padding(top = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ReplaceImageOnBoards(R.drawable.image_3)

            TitleOnBoardTextT2("У Вас Есть Сила, Чтобы")

            OnBoardText("В вашей комнате много красивых и привлекательных растений")

            ReplaceLinesOnBoards(R.drawable.lines_screen3)

            Spacer(modifier = Modifier.height(16.dp))

            if (!text.isNullOrEmpty()) {
                Text("Полученный текст: $text")
            }

            Box(modifier = Modifier.padding(top = 60.dp, bottom = 36.dp, start = 20.dp, end = 20.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){

                Button(onClick = {context.startActivity(Intent(context, MainAuthentication::class.java))},
                    modifier = Modifier.height(50.dp).fillMaxSize(),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  colorResource(R.color.block) ,
                    )
                ){
                    Text("Далее", style = buttonType1Text, color = colorResource(R.color.text))
                }

            }
        }
    }


