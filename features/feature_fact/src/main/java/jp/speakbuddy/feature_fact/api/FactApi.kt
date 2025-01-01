package jp.speakbuddy.feature_fact.api

import jp.speakbuddy.feature_fact.data.Fact
import retrofit2.Call
import retrofit2.http.GET

interface FactApi {
    @GET("fact")
    fun getFact(): Call<Fact>
}