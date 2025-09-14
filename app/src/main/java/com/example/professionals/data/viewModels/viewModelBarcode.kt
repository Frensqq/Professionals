package com.example.professionals.data.viewModels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import android.provider.Settings


class viewModelBarcode: ViewModel() {

    fun changeBrightness(context: Context, brightness:Int){

        if(Settings.System.canWrite(context)){
            setBrightness(context, brightness )
        }
        else{
            context.startActivity(Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS))
        }
    }

    fun setBrightness(context: Context, brightness: Int){

        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )

        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            brightness
        )

    }



}