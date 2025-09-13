package com.example.professionals.data.screens.OnBoards


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.professionals.R
import com.example.professionals.data.screens.OnBoards.ui.theme.PreviewTextT2
import com.example.professionals.data.screens.OnBoards.ui.theme.ProfessionalsTheme
import com.example.professionals.ui.theme.RegularTextTypeONB

class MainActivityOnBoards : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfessionalsTheme {

                AppNavigation()



            }
        }

    }


}


@Composable
fun ReplaceImageOnBoards(img: Int){
    Image(
        bitmap = ImageBitmap.imageResource(img),
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}

@Composable
fun ReplaceLinesOnBoards(img: Int){
    Image(
        bitmap = ImageBitmap.imageResource(img),
        modifier = Modifier.padding(top = 40.dp, start = 126.dp, end = 126.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}

@Composable
fun TitleOnBoardTextT2(text: String){
    Text(text, style = PreviewTextT2, modifier = Modifier.padding(top = 60.dp).width(315.dp), color = colorResource(R.color.block))
}

@Composable
fun OnBoardText(text: String){
    Text(text, style = RegularTextTypeONB, modifier = Modifier.padding(top = 13.dp).width(315.dp), color = colorResource(R.color.block))
}
