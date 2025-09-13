package com.example.professionals.data.screens.Cabinet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.professionals.data.viewModels.viewMarketOrders
import com.example.professionals.data.screens.authentication.ButtonBack
import com.example.professionals.ui.theme.TitleTypeMarket


@Composable
fun historyOrders(id: String, token:String, navController: NavController, viewModel: viewMarketOrders = viewModel()) {

    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.ViewOrders(token, "id_users = '$id'",  "-created", 150)
            isInitialized = true
        }
    }

    val userData by viewModel.Orders.collectAsState()




    Row(
        modifier = Modifier
            .padding(top = 48.dp, end = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ButtonBack(0, navController, "Menu")

        Text("В разработке", modifier = Modifier, style = TitleTypeMarket)

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .height(44.dp)
                .width(44.dp)
        ) {
        }
    }

}
