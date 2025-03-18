package com.exaple.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exaple.weather.data.model.City
import com.exaple.weather.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class PrincipalViewModelClass (): ViewModel() {

    private val repository = Repository

    private var _city = MutableStateFlow( City() )
    val city: StateFlow<City> = _city.asStateFlow()

    fun getForecastByName( cityName: String ){

        viewModelScope.launch (Dispatchers.IO ){
            _city.update { current ->
                val copy = current.copy(
                    name = cityName,
                    forecast = repository.getForecastByCity( cityName )
                )
                copy
            }
        }

    }

}

object PrincipalViewModel: PrincipalViewModelClass()