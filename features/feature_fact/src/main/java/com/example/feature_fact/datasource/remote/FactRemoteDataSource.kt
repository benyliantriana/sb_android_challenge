package com.example.feature_fact.datasource.remote

import com.example.feature_fact.data.Fact
import jp.speakbuddy.network.response.BaseResponse

interface FactRemoteDataSource {
    suspend fun getRemoteFact(): BaseResponse<Fact>
}
