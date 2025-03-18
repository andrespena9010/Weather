package com.exaple.weather.data.remote

import com.exaple.weather.data.model.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object RetrofitProvider {

    private const val BASE_URL_WEATHER_API = "https://api.weatherapi.com/v1/"

    private lateinit var retrofitWatherApiService: WeatherApiService

    private val retrofitWeatherApi: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getWeatherApiService(): WeatherApiService {
        retrofitWatherApiService = retrofitWeatherApi.create(WeatherApiService::class.java)
        return retrofitWatherApiService
    }
}

interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getForecastByCity(
        @Query("key") key: String = weatherApiKey,
        @Query("q") cityName: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: Boolean = false,
        @Query("alerts") alerts: Boolean = false
    ): ForecastResponse

}

private const val weatherApiKey = "da59f176fc13470e95593019251803"