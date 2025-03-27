package com.exaple.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exaple.weather.data.model.*
import com.exaple.weather.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

open class PrincipalViewModelClass (): ViewModel() {

    private var lang: Locale = Locale.getDefault()

    private val repository = Repository

    private var _forecast = MutableStateFlow( Forecast() )
    var forecast: StateFlow<Forecast> = _forecast.asStateFlow()

    fun updateForecastByName(cityName: String ){

        viewModelScope.launch (Dispatchers.IO ){

            _forecast.update {
                val new = repository.getForecastByCity( cityName, lang ) // Implementar errores
                new.copy(
                    current = new.current.copy(
                        condition = new.current.condition.copy(
                            icon = "https:${new.current.condition.icon.replace("64x64", "128x128")}"
                        )
                    ),
                    forecast = new.forecast.copy(
                        forecastDays = new.forecast.forecastDays.map { day ->
                            day.copy(
                                day =  day.day.copy(
                                    condition = day.day.condition.copy(
                                        icon = "https:${day.day.condition.icon.replace("64x64", "128x128")}"
                                    )
                                ),
                                hour = day.hour.map { hour ->
                                    hour.copy(
                                        condition = hour.condition.copy(
                                            icon = "https:${hour.condition.icon.replace("64x64", "128x128")}"
                                        )
                                    )
                                }
                            )
                        }
                    )
                )
            }

        }

    }

    fun test( forecast: Forecast ){
        _forecast.update {
            forecast
        }
    }

}

object PrincipalViewModel: PrincipalViewModelClass()