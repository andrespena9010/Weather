package com.exaple.weather.ui.custom

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exaple.weather.R
import com.exaple.weather.ui.theme.WeatherTheme
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.Locale
import coil3.compose.AsyncImage
import com.exaple.weather.data.model.Forecast
import com.exaple.weather.ui.viewmodel.PrincipalViewModel
import com.exaple.weather.ui.viewmodel.PrincipalViewModelClass
import com.google.gson.Gson

fun Int.csp(): TextUnit {
    val density = Resources.getSystem().displayMetrics.density.toDouble()
    return ( this / (density / 2)  ).sp
}

fun Int.cdp(): Dp {
    val density = Resources.getSystem().displayMetrics.density.toDouble()
    return ( this / (density / 2) ).dp
}

@Composable
fun WeatherView(
    viewModel: PrincipalViewModelClass = PrincipalViewModel
) {

    val forecast by viewModel.forecast.collectAsStateWithLifecycle()
    val locale = Locale.getDefault()

    Box {

        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource( R.drawable.fondo ),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Container (
                modifier = Modifier
                    .weight(10f)
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.cdp()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    if ( forecast.forecast.forecastDays.isNotEmpty() ){

                        TextWeater1(
                            text = getStringDayDate(
                                isoDate = forecast.forecast.forecastDays[0].date,
                                locale = locale
                            )
                        )

                        TextWeater1(
                            text = "${forecast.location.name}, ${forecast.location.region}, ${forecast.location.country}",
                            fontSize = 40
                        )

                        Row (
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ){
                            Column (
                                modifier = Modifier
                                    .padding(5.cdp())
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                if ( forecast.alerts.alert.isNotEmpty() ){
                                    TextWeater1(
                                        text = forecast.alerts.alert[0].headline,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.cdp()))
                                            .background( Color(0x55FFFF00) ),
                                        fontSize = 16
                                    )
                                }

                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ){

                                    AsyncImage(
                                        model = forecast.current.condition.icon,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(200.cdp()),
                                        contentScale = ContentScale.Crop
                                    )

                                    TextWeater1(
                                        text = forecast.current.condition.text,
                                        fontSize = 16
                                    )

                                }

                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ){

                                    TextWeater1(
                                        text = "${forecast.current.tempC} ºC",
                                        fontSize = 50
                                    )

                                    TextWeater1(
                                        text = "Sensacion termica\n${forecast.current.feelslikeC} ºC",
                                        fontSize = 16
                                    )

                                }

                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ){

                                    TextWeater1(
                                        text = "T-max\n${forecast.forecast.forecastDays[0].day.maxTempC}º",
                                        fontSize = 30
                                    )

                                    TextWeater1(
                                        text = "T-min\n${forecast.forecast.forecastDays[0].day.minTempC}º",
                                        fontSize = 30
                                    )

                                }

                            }

                            Row (
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ){

                                Column (
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){

                                    TextWeater1(
                                        text = "Viento: ${forecast.current.windKph} Km/h",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Direccion: ${forecast.current.windDir}",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Rafagas: ${forecast.current.gustKph} Km/h",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Lluvia: ${forecast.forecast.forecastDays[0].day.dailyWillItRain} %",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Nieve: ${forecast.forecast.forecastDays[0].day.dailyChanceOfSnow} %",
                                        fontSize = 20
                                    )

                                }

                                Column (
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){

                                    TextWeater1(
                                        text = "Radiacion UV: ${(forecast.current.uv * 10).toInt()}",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Humedad: ${forecast.current.humidity}%",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Presion: ${forecast.current.pressureMb} mb",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Amanecer: ${forecast.forecast.forecastDays[0].astro.sunrise}",
                                        fontSize = 20
                                    )

                                    TextWeater1(
                                        text = "Atardecer: ${forecast.forecast.forecastDays[0].astro.sunset}",
                                        fontSize = 20
                                    )

                                }

                            }
                        }

                    }

                }
            }

            LazyRow (
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){

                itemsIndexed ( forecast.forecast.forecastDays ){ index, forecastDay ->

                    if ( index != 0 ){

                        Container {

                            Column (
                                modifier = Modifier
                                    .width(300.cdp())
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){

                                TextWeater1(
                                    text = getStringMonthDay( forecastDay.date, locale ),
                                    modifier = Modifier
                                        .padding( 5.cdp() )
                                )

                                Row (
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                ){

                                    Column (
                                        modifier = Modifier
                                            .padding( 5.cdp() )
                                            .weight(1f)
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){

                                        TextWeater1(
                                            text = "T-max: ${forecastDay.day.maxTempC}º"
                                        )

                                        TextWeater1(
                                            text = "T-min: ${forecastDay.day.minTempC}º"
                                        )

                                        TextWeater1(
                                            text = "Lluvia: ${forecastDay.day.dailyWillItRain} %"
                                        )

                                    }

                                    Column (
                                        modifier = Modifier
                                            .padding( 5.cdp() )
                                            .weight(1f)
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){

                                        TextWeater1(
                                            text = "Nieve: ${forecastDay.day.dailyChanceOfSnow} %"
                                        )

                                        TextWeater1(
                                            text = forecastDay.day.condition.text
                                        )

                                        AsyncImage(
                                            model = forecastDay.day.condition.icon,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(50.cdp()),
                                            contentScale = ContentScale.Crop
                                        )

                                    }

                                }

                            }

                        }

                    }

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
            .padding(20.cdp())
            .clip(RoundedCornerShape(10.cdp()))
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
    fontSize: Int = 16,
    color: Color = Color.White
){
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize.csp(),
        textAlign = TextAlign.Center
    )
}

fun getStringHourDate( isoDate: String , locale: Locale): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse( isoDate, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy", locale)
    var formattedDate = dateTime.format(outputFormatter)
    return formattedDate.replaceFirstChar { if (it.isLowerCase()) it.titlecase( locale ) else it.toString() }
}

fun getStringMonthDay( isoDate: String , locale: Locale): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse( isoDate, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd '/' MMMM", locale)
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
    device = Devices.PIXEL_TABLET
)
@Composable
fun WeatherViewPreview(){
    WeatherTheme {
        val viewModel = PrincipalViewModel
        viewModel.test( Gson().fromJson( String( LocalContext.current.resources.openRawResource( R.raw.example_forecast_response ).readAllBytes() ), Forecast::class.java ) )
        WeatherView(
            viewModel = viewModel
        )
    }
}