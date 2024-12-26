package jp.speakbuddy.edisonandroidexercise.api

import jp.speakbuddy.edisonandroidexercise.data.Fact
import retrofit2.Call
import retrofit2.http.GET

interface FactApi {
    @GET("fact")
    fun getFact(): Call<Fact>
}
