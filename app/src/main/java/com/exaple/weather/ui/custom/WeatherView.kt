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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
                .background( Color( 0x88002255 ) )
        ){
            Container (
                modifier = Modifier
                    .weight(10f)
                    .padding(10.cdp())
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.cdp()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    if ( forecast.forecast.forecastDays.isNotEmpty() ){

                        TextWeater(
                            text = getStringDayDate(
                                isoDate = forecast.forecast.forecastDays[0].date,
                                locale = locale
                            )
                        )

                        TextWeater(
                            text = "${forecast.location.name}, ${forecast.location.region}, ${forecast.location.country}",
                            fontSize = 40
                        )

                        if ( forecast.alerts.alert.isNotEmpty() ){
                            TextWeaterAlert(
                                text = forecast.alerts.alert[0].headline
                            )
                        }

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

                                    TextWeater(
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

                                    TextWeater(
                                        text = "${forecast.current.tempC} ºC",
                                        fontSize = 50
                                    )

                                }

                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ){

                                        Icon(
                                            painter = painterResource( R.drawable.arrow_down ),
                                            modifier = Modifier
                                                .height( 20.cdp() ),
                                            contentDescription = "Arrow Down",
                                            tint = Color( 0x770F0FFF )
                                        )

                                        TextWeater(
                                            text = "${forecast.forecast.forecastDays[0].day.maxTempC}º",
                                            fontSize = 30
                                        )

                                    }

                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ){

                                        Icon(
                                            painter = painterResource( R.drawable.arrow_up ),
                                            modifier = Modifier
                                                .height( 20.cdp() ),
                                            contentDescription = "Arrow Down",
                                            tint = Color( 0x77FF0F0F )
                                        )

                                        TextWeater(
                                            text = "${forecast.forecast.forecastDays[0].day.minTempC}º",
                                            fontSize = 30
                                        )

                                    }

                                }

                            }

                            Column (
                                modifier = Modifier
                                    .weight(1f)
                            ){

                                Column (
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){

                                    Icon(
                                        painter = painterResource( R.drawable.wind),
                                        modifier = Modifier
                                            .height( 80.cdp() ),
                                        contentDescription = "Wind",
                                        tint = Color( 0x77EFFEFF )
                                    )

                                    TextWeater(
                                        text = "${forecast.current.windKph} Km/h\n" +
                                                forecast.current.windDir,
                                        modifier = Modifier
                                            .padding(5.cdp()),
                                        fontSize = 20
                                    )

                                }

                                Row (
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                ){

                                    Column (
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ){

                                        Image(
                                            painter = painterResource( R.drawable.sunrise),
                                            modifier = Modifier
                                                .height( 80.cdp() ),
                                            contentDescription = "Sunrise"
                                        )

                                        TextWeater(
                                            text = forecast.forecast.forecastDays[0].astro.sunrise,
                                            modifier = Modifier
                                                .padding(5.cdp()),
                                            fontSize = 20
                                        )

                                    }

                                    Column (
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ){

                                        Image(
                                            painter = painterResource( R.drawable.sunset),
                                            modifier = Modifier
                                                .height( 80.cdp() ),
                                            contentDescription = "Sunset"
                                        )

                                        TextWeater(
                                            text = forecast.forecast.forecastDays[0].astro.sunset,
                                            modifier = Modifier
                                                .padding(5.cdp()),
                                            fontSize = 20
                                        )

                                    }

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

                        Container (
                            modifier = Modifier
                                .padding(
                                    start = 10.cdp(),
                                    bottom = 10.cdp()
                                )
                        ){

                            Column (
                                modifier = Modifier
                                    .width(300.cdp())
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){

                                TextWeater(
                                    text = getStringMonthDay( forecastDay.date, locale ),
                                    modifier = Modifier
                                        .padding( 5.cdp() )
                                )

                                TextWeater(
                                    text = forecastDay.day.condition.text,
                                    modifier = Modifier
                                        .padding( bottom = 5.cdp() )
                                )

                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ){

                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ){

                                        Icon(
                                            painter = painterResource( R.drawable.arrow_down ),
                                            modifier = Modifier
                                                .height( 10.cdp() ),
                                            contentDescription = "Arrow Down",
                                            tint = Color( 0x770F0FFF )
                                        )

                                        TextWeater(
                                            text = "${forecastDay.day.maxTempC}º",
                                            fontSize = 20
                                        )

                                    }

                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ){

                                        Icon(
                                            painter = painterResource( R.drawable.arrow_up ),
                                            modifier = Modifier
                                                .height( 10.cdp() ),
                                            contentDescription = "Arrow Down",
                                            tint = Color( 0x77FF0F0F )
                                        )

                                        TextWeater(
                                            text = "${forecastDay.day.minTempC}º",
                                            fontSize = 20
                                        )

                                    }

                                }

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

@Composable
fun Container(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.cdp()))
            .background( Color(0x33000000) ),
        contentAlignment = Alignment.Center
    ){
        content()
    }
}

@Composable
fun TextWeater(
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

@Composable
fun TextWeaterAlert(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 16,
    color: Color = Color.White
){

    Row (
        modifier = Modifier
            .clip(RoundedCornerShape(20.cdp()))
            .background( Color(0x22FFFF00) )
            .height( 25.cdp() ),
    ){

        Image(
            painter = painterResource( R.drawable.advertencia ),
            modifier = Modifier
                .padding(2.cdp()),
            contentDescription = "Warning"
        )

        Text(
            text = text,
            modifier = modifier
                .padding(end = 5.cdp()),
            color = color,
            fontSize = fontSize.csp(),
            textAlign = TextAlign.Center
        )

    }

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