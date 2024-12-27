package com.example.feature_fact.api

import com.example.feature_fact.data.Fact
import retrofit2.Call
import retrofit2.http.GET

interface FactApi {
    @GET("fact")
    fun getFact(): Call<Fact>
}
