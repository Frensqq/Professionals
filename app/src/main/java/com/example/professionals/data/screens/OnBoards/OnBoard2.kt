package com.example.professionals.data.screens.OnBoards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.ui.theme.buttonType1Text

@Composable
fun OnBoard2(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ReplaceImageOnBoards(R.drawable.image_2)

        TitleOnBoardTextT2("Начнем путешествие")

        OnBoardText("Умная, великолепная и модная коллекция Изучите сейчас")

        ReplaceLinesOnBoards(R.drawable.lines_screen2)

        Box(modifier = Modifier.padding(top = 95.dp, bottom = 36.dp, start = 20.dp, end = 20.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){

            Button(onClick = {navController.navigate("screen3")},
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