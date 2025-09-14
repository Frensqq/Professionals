package com.example.professionals.data.screens.Cabinet


import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionals.data.viewModels.viewModelBarcode
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType


@Composable
fun BarcodeFullScreen(idUser:String, viewModel: viewModelBarcode = viewModel()){

    val context = LocalContext.current
    val currentBrightness = remember { mutableStateOf(Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)) }

    DisposableEffect(Unit) {

        viewModel.changeBrightness(context,80)

        onDispose {
            viewModel.changeBrightness(context, currentBrightness.value)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Barcode(
            modifier = Modifier
                .fillMaxSize()
                .rotate(90f).
                padding(2.dp),
            resolutionFactor = 1,
            type = BarcodeType.CODE_128,
            value = idUser
        )
    }

}