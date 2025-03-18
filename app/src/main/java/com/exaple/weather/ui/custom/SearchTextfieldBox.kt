package com.exaple.weather.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exaple.weather.ui.theme.WeatherTheme

@Composable
fun SearchTextfieldBox(
    modifier: Modifier = Modifier,
    onSearch: ( String ) -> Unit
) {

    var cityName by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            modifier = Modifier
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(30.dp)
                )
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth( 0.8f ),
            placeholder = {
                Text("City")
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedPlaceholderColor = Color(0x22000000),
                unfocusedPlaceholderColor = Color(0x55000000)
            ),
            trailingIcon = {
                IconButton(
                    onClick = { onSearch( cityName ) }
                ) {
                    Icon(Icons.TwoTone.Search, "")
                }
            }
        )
    }

}

@Preview
@Composable
private fun SearchTextfieldBoxPreview() {
    WeatherTheme {
        SearchTextfieldBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {_ ->}
    }
}