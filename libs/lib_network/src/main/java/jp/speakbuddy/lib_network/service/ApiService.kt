package jp.speakbuddy.lib_network.service

import retrofit2.Retrofit

interface ApiService {
    suspend fun service(): Retrofit
}
