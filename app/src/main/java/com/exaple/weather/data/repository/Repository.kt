package com.exaple.weather.data.repository

import com.exaple.weather.data.model.*
import com.exaple.weather.data.remote.RetrofitProvider

object Repository {

    private val weatherApiRetrofit = RetrofitProvider.getWeatherApiService()

    suspend fun getForecastByCity( cityName: String ): ForecastResponse {
        return weatherApiRetrofit.getForecastByCity( cityName = cityName )
    }

}