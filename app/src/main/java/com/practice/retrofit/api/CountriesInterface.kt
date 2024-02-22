package com.practice.retrofit.api

import com.practice.retrofit.models.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesInterface {

    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun getCountries(): Response<List<Country>>
}