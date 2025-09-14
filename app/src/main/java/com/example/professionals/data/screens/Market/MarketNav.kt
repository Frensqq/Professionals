package com.example.professionals.data.screens.Market

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.professionals.data.screens.Cabinet.BarcodeFullScreen
import com.example.professionals.data.screens.Cabinet.Profile
import com.example.professionals.data.screens.Cabinet.SideMenu
import com.example.professionals.data.screens.Cabinet.historyOrders


@Composable
fun MarketNavigation(token:String, id:String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") { Home(id, token, navController) }
        composable("CatalogSneakers/{typeCross}") { backStackEntry ->
            val typeCross = backStackEntry.arguments?.getString("typeCross") ?: ""
            CatalogSneakers(typeCross = typeCross,token,id, navController) }
        composable("Popular") { Popular(navController) }
        composable("Favorite") { Favorite(token,id, navController) }
        composable("Search") { Search(id,token,navController) }
        composable("Details/{idSneakers}/{name}/{cost}/{gender}/{info}/{collectionId}/{favorites}/{incart}",){ backStackEntry ->
            val idsneakers = backStackEntry.arguments?.getString("idSneakers") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val cost = backStackEntry.arguments?.getString("cost") ?: ""
            val gender = backStackEntry.arguments?.getString("gender") ?: ""
            val info = backStackEntry.arguments?.getString("info") ?: ""
            val favorites = backStackEntry.arguments?.getString("favorites") ?: ""
            val collectionId  = backStackEntry.arguments?.getString("collectionId") ?: ""
            val incart  = backStackEntry.arguments?.getString("incart") ?: ""
            Details(idSneakers = idsneakers, name = name, cost = cost, gender = gender, info = info,collectionId,
                favorites = favorites, incart, token, id, navController) }
        composable("mycart") { MyCart(id, token, navController) }
        composable("Menu") { SideMenu(id, token, navController) }
        composable("profile") { Profile(id, token,navController) }
        //composable("Map") { WorkingMapScreen( ) }
        composable("history") { historyOrders(id, token,navController) }
        composable("barcode") { BarcodeFullScreen(id, navController) }

    }
}