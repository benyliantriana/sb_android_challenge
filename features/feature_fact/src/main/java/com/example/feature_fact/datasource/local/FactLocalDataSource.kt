package com.example.feature_fact.datasource.local

import com.example.feature_fact.data.Fact
import jp.speakbuddy.network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<Fact>
    suspend fun saveFactToDataStore(fact: Fact)
}