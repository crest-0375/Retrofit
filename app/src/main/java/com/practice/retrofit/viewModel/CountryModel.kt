package com.practice.retrofit.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.retrofit.api.CountriesService
import com.practice.retrofit.models.Country
import com.practice.retrofit.util.ResultOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryModel : ViewModel() {
    val countries = MutableLiveData<ResultOf<ArrayList<Country>>>()
    private val countriesService = CountriesService.getCountriesService()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, thrawable ->
        onError("Exception : ${thrawable.message}")
    }
    var job: Job? = null
    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        countries.value = ResultOf.Loading()

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = countriesService.getCountries()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countries.value = ResultOf.Success(response.body() as ArrayList<Country>)
                } else
                    countries.value = ResultOf.Failure(response.message())
            }
        }

    }

    private fun onError(message: String) {
        countries.value = ResultOf.Failure(message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}