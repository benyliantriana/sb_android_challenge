package com.example.feature_fact.repository

import com.example.feature_fact.data.Fact
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    suspend fun getSavedFact(): Flow<BaseResponse<Fact>>
    suspend fun updateFact(): Flow<BaseResponse<Fact>>
}
