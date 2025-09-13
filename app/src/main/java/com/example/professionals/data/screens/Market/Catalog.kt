package com.example.professionals.data.screens.Market

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.professionals.data.viewModels.viewMarket
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.namesInterfaceElements
import com.example.professionals.ui.theme.TitleTypeMarket

@Composable
fun CatalogSneakers(typeCross: String,token:String,iduser:String, navController: NavController, viewModel: viewMarket = viewModel()){

    var isInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!isInitialized) {
            if (typeCross== "Каталог") viewModel.outputProducts("id != 'null'", token, "+created", 30)
            else viewModel.outputProducts("category= '$typeCross'", token, "+created", 30)

            isInitialized = true
        }
    }

    Column() {
        Row(
            modifier = Modifier.padding(top = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonBack(0, navController, "Home")

            Text(text = typeCross, modifier = Modifier.fillMaxWidth(), style = TitleTypeMarket)
        }


        Box(modifier = Modifier.padding(start = 20.dp, top= 18.dp)) {
            namesInterfaceElements("Категории")
        }

        Spacer(modifier = Modifier.height(15.dp))

        RowСategories(navController)

        Spacer(modifier = Modifier.height(18.dp))

        val sneakers = viewModel.sneakers.collectAsState()

        val twoDArray = ConverToArrayArray(sneakers)

        OutputCardSneakers(iduser,token, twoDArray, navController)

    }

}