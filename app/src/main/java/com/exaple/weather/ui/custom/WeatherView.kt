package com.exaple.weather.ui.custom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.exaple.weather.R
import com.exaple.weather.ui.theme.WeatherTheme
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.Locale
import coil3.compose.AsyncImage
import com.exaple.weather.data.model.Forecast
import com.google.gson.Gson

@Composable
fun WeatherView(
    forecast: Forecast
) {

    val locale = Locale.getDefault()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource( R.drawable.fondo ),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column (
                modifier = Modifier
                    .weight(2f)
            ){
                Container (
                    modifier = Modifier
                        .weight(2f)
                ){
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        TextWeater1(
                            text = getStringDayDate(
                                isoDate = forecast.forecast.forecastDays[0].date,
                                locale = locale
                            )
                        )
                        /*TextWeater1(
                            text = uiData.city
                        )
                        TextWeater1(
                            text = "${uiData.currentTemp}º"
                        )
                        TextWeater1(
                            text = uiData.detailText
                        )
                        AsyncImage( uiData.days[0].conditionIcon, "conditionIcon" )*/
                    }
                }
                Container (
                    modifier = Modifier
                        .weight(1f)
                ){

                }
            }
            Column (
                modifier = Modifier
                    .weight(1f)
            ){
                Container (
                    modifier = Modifier
                        .weight(1f)
                ){

                }
                Container (
                    modifier = Modifier
                        .weight(1f)
                ){

                }
            }
        }
    }

}

@Composable
fun Container(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .clip(RoundedCornerShape(50.dp))
            .background( Color(0x77000000) ),
        contentAlignment = Alignment.Center
    ){
        content()
    }
}

@Composable
fun TextWeater1(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified
){
    Text(
        text = text,
        modifier = modifier,
        color = Color.White,
        fontSize = fontSize
    )
}

fun getStringHourDate( isoDate: String , locale: Locale): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse( isoDate, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy", locale)
    var formattedDate = dateTime.format(outputFormatter)
    return formattedDate.replaceFirstChar { if (it.isLowerCase()) it.titlecase( locale ) else it.toString() }
}

fun getStringDayDate( isoDate: String , locale: Locale): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse( isoDate, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy", locale)
    var formattedDate = dateTime.format(outputFormatter)
    return formattedDate.replaceFirstChar { if (it.isLowerCase()) it.titlecase( locale ) else it.toString() }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    widthDp = 1920,
    heightDp = 1080
)
@Composable
fun WeatherViewPreview(){
    WeatherTheme {
        WeatherView(
            forecast = Gson().fromJson( String( LocalContext.current.resources.openRawResource( R.raw.example_forecast_response ).readAllBytes() ), Forecast::class.java )
        )
    }
}