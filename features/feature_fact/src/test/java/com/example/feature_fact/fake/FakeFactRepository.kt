package com.example.feature_fact.fake

import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val factResponse: BaseResponse<com.example.feature_fact.data.Fact>,
) : com.example.feature_fact.repository.FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<com.example.feature_fact.data.Fact>> =
        flowOf(factResponse)

    override suspend fun updateFact(): Flow<BaseResponse<com.example.feature_fact.data.Fact>> =
        flowOf(factResponse)
}