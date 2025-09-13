package com.example.professionals

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.professionals.data.screens.OnBoards.AppNavigation
import com.example.professionals.data.screens.OnBoards.MainActivityOnBoards
import com.example.professionals.ui.theme.ProfessionalsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfessionalsTheme {


                //MainScreen()
                val intent = Intent(this@MainActivity, MainActivityOnBoards::class.java)
                startActivity(intent)

            }
        }
    }
}

@Composable
fun namesInterfaceElements(name: String){
    Text(name, style = com.example.professionals.ui.theme.namesInterfaceElements, color = colorResource(R.color.text))
}