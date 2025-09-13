package com.example.professionals.data.screens.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.professionals.R
import com.example.professionals.data.screens.authentication.ui.theme.ProfessionalsTheme
import com.example.professionals.ui.theme.RegularTextTypeONB
import com.example.professionals.ui.theme.TitleText32N
import kotlinx.coroutines.launch

class MainAuthentication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfessionalsTheme {

                AuthNavigation()

            }
        }
    }
}

@Composable
fun TitleAunth(Str1:String,Str2:String){

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(Str1, style = TitleText32N, color = colorResource(R.color.text), modifier = Modifier.fillMaxWidth())
        Text(Str2, style = RegularTextTypeONB, color = colorResource(R.color.subtextdark), modifier = Modifier.padding(top = 8.dp).fillMaxWidth())
    }
}



@Composable
fun textFieldAunth(prew: String): MutableState<String> {

    val text = remember{mutableStateOf(prew)}

    OutlinedTextField(
        text.value,
        {text.value = it},
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
        modifier = Modifier.padding(top = 12.dp).height(50.dp).fillMaxWidth(),)

    return text
}

@Composable
fun passwordFieldAunth(prew: String): MutableState<String> {

    val password = remember{mutableStateOf(prew)}
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    val IconIf: Int

    if (passwordVisibility){
        IconIf  =R.drawable.eyeopen }
    else {
        IconIf = R.drawable.eye
    }

    OutlinedTextField(
        password.value, { password.value = it },
        textStyle = TextStyle(fontSize = 14.sp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xff48B2E7),
            unfocusedBorderColor = Color.White,
            focusedContainerColor = Color(0xffeaeaeb),
            unfocusedContainerColor = Color(0xffeaeaeb),
            unfocusedTextColor = colorResource(R.color.hint),
            focusedTextColor = colorResource(R.color.text)

        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 12.dp).height(50.dp)
            .fillMaxWidth(),


        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility })
            {
                    Icon(
                    ImageBitmap.imageResource(IconIf),
                    contentDescription = "Скрытие пароля",
                    modifier = Modifier.fillMaxSize(0.5f),
                    tint = if (passwordVisibility) Color(0xFF48B2E7) else Color(0xff6A6A6A)
                )
            }
        }
    )
    return password
}

@Composable
fun ErrorAunth(RegError: String, TypeErroe: String){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = TypeErroe+"\n"+RegError,
                duration = SnackbarDuration.Long
            )
        }
    }


    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                containerColor = colorResource(R.color.accent),
                contentColor = Color.White,
                content = { Text(data.visuals.message) },
                modifier = Modifier.padding(20.dp),
                shape = RoundedCornerShape(13.dp),


            )
        }
    )
}

@Composable
fun ButtonBack(topPad: Int, navController: NavController, way: String){


    Box(modifier = Modifier
        .padding(top = topPad.dp, start = 20.dp)
        .height(44.dp)
        .width(44.dp)){

        IconButton(
            onClick = {navController.navigate(way)},
            modifier =  Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(colorResource(R.color.background))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = colorResource(R.color.text)
            )
        }
    }

}


