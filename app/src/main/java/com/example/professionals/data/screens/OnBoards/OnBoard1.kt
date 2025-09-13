package com.example.professionals.data.screens.OnBoards


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.professionals.data.screens.OnBoards.ui.theme.PreviewText
import com.example.professionals.ui.theme.buttonType1Text


@Composable
fun OnBoard1(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ДОБРО\nПОЖАЛОВАТЬ", style = PreviewText, color = colorResource(R.color.block), modifier = Modifier.width(267.dp).height(192.dp).fillMaxSize())

        val img = R.drawable.image_1

        ReplaceImageOnBoards(img)

        val imgLine = R.drawable.lines_screen1

        ReplaceLinesOnBoards(imgLine)


        Box(modifier = Modifier.padding(top = 100.dp, bottom = 36.dp, start = 20.dp, end = 20.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){

            Button(onClick = {navController.navigate("screen2")},
                modifier = Modifier.height(50.dp).fillMaxSize(),
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =  colorResource(R.color.block) ,
                )
            ){
                Text("Начать", style = buttonType1Text, color = colorResource(R.color.text))
            }


        }


    }
}