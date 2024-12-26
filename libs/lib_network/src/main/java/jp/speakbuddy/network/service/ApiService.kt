package jp.speakbuddy.network.service

import retrofit2.Retrofit

interface ApiService {
    suspend fun service(): Retrofit
}