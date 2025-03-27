package com.exaple.weather.data.repository

import com.exaple.weather.data.model.*
import com.exaple.weather.data.remote.RetrofitProvider
import java.util.Locale

object Repository {

    private val weatherApiRetrofit = RetrofitProvider.getWeatherApiService()

    suspend fun getForecastByCity( cityName: String, lang: Locale ): Forecast {
        return weatherApiRetrofit.getForecastByCity( cityName = cityName, lang = lang.language )
    }

}