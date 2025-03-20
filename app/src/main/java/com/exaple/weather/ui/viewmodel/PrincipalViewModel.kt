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

    private var _conditionsInfo = MutableStateFlow( Conditions() )
    val conditionsInfo: StateFlow<Conditions> = _conditionsInfo.asStateFlow()

    private var _forecast = MutableStateFlow( Forecast() )
    var forecast: StateFlow<Forecast> = _forecast.asStateFlow()

    init {

        viewModelScope.launch {
            _conditionsInfo.update {
                repository.getConditions()
            }
        }

    }

    fun updateForecastByName(cityName: String ){

        viewModelScope.launch (Dispatchers.IO ){

            _forecast.update {
                val new = repository.getForecastByCity( cityName, lang )
                new.copy(
                    current = new.current.copy(
                        condition = new.current.condition.copy(
                            text = getLanguageText( new.current.condition.code, ( new.current.isDay == 1 ) )
                        )
                    ),
                    forecast = new.forecast.copy(
                        forecastDays = new.forecast.forecastDays.map { day ->
                            day.copy(
                                day =  day.day.copy(
                                    condition = day.day.condition.copy(
                                        text = getLanguageText( day.day.condition.code, true )
                                    )
                                ),
                                hour = day.hour.map { hour ->
                                    hour.copy(
                                        condition = hour.condition.copy(
                                            text = getLanguageText( hour.condition.code, ( hour.isDay == 1 ) )
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

    fun getLanguageText( code: Int , day: Boolean ): String {
        var txt = ""
        conditionsInfo.value.conditions.forEachIndexed { index, condition ->
            if ( code == condition.code ){
                conditionsInfo.value.conditions[ index ].languages.forEach { language ->
                    if ( language.langIso == lang.language ){
                        txt = if ( day ) language.dayText else language.nightText
                    }
                }
            }
        }
        return txt
    }

}

object PrincipalViewModel: PrincipalViewModelClass()