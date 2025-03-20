package com.exaple.weather.data.remote

import com.exaple.weather.data.model.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object RetrofitProvider {

    private const val BASE_URL_WEATHER_API = "https://api.weatherapi.com/"
    private const val BASE_URL_WEATHER = "https://www.weatherapi.com/"

    private lateinit var retrofitWatherApiService: WeatherApiService
    private lateinit var retrofitWatherService: WeatherService

    private val retrofitWeatherApi: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitWeather: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getWeatherApiService(): WeatherApiService {
        retrofitWatherApiService = retrofitWeatherApi.create(WeatherApiService::class.java)
        return retrofitWatherApiService
    }

    fun getWeatherService(): WeatherService {
        retrofitWatherService = retrofitWeather.create(WeatherService::class.java)
        return retrofitWatherService
    }

}

interface WeatherApiService {

    @GET("v1/forecast.json")
    suspend fun getForecastByCity(
        @Query("key") key: String = weatherApiKey,
        @Query("q") cityName: String,
        @Query("lang") lang: String = "en",
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: Boolean = false,
        @Query("alerts") alerts: Boolean = false
    ): Forecast

}

interface WeatherService {

    @GET("docs/conditions.json")
    suspend fun getConditions(): List<ConditionInfo>

}

private const val weatherApiKey = "da59f176fc13470e95593019251803"