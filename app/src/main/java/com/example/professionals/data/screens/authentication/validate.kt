package com.example.professionals.data.screens.authentication

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun validateEmail(email: String): Boolean{

    val validEmail = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$".toRegex()

    return email.matches(validEmail)
}

@Composable
fun ErrorValidate(errorText:String){

    var openDialog = remember { mutableStateOf(true) }

    if (openDialog.value)
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Ошибка ввода") },
            text = { Text(errorText) },
            confirmButton = {
                Button(
                    { openDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Text("Ок", fontSize = 22.sp)
                }
            },
            containerColor = Color.DarkGray,
            titleContentColor = Color.LightGray,
            textContentColor = Color.LightGray,
            iconContentColor = Color.LightGray
        )

}