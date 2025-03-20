package com.exaple.weather.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exaple.weather.ui.custom.SearchTextfieldBox
import com.exaple.weather.ui.custom.WeatherView
import com.exaple.weather.ui.theme.WeatherTheme
import com.exaple.weather.ui.viewmodel.PrincipalViewModel
import com.exaple.weather.ui.viewmodel.PrincipalViewModelClass

@Composable
fun Principal(
    viewModel: PrincipalViewModelClass = PrincipalViewModel
){

    Scaffold { paddings ->
        Column (
            modifier = Modifier
                .padding( paddings )
                .fillMaxSize()
        ) {
            SearchTextfieldBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ){ cityName ->
                viewModel.updateForecastByName( cityName )
            }
            WeatherView()
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_TABLET
)
@Composable
fun PrincipalPreview(){
    WeatherTheme {
        Principal()
    }
}