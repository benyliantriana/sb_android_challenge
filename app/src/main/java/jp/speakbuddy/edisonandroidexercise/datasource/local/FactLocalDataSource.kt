package jp.speakbuddy.edisonandroidexercise.datasource.local

import jp.speakbuddy.edisonandroidexercise.data.Fact

interface FactLocalDataSource {
    suspend fun getLocalFact(): Fact?
    suspend fun saveFactToDataStore(fact: Fact)
}