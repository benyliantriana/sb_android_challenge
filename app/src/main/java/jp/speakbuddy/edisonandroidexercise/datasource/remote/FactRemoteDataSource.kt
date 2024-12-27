package jp.speakbuddy.edisonandroidexercise.datasource.remote

import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.network.response.BaseResponse

interface FactRemoteDataSource {
    suspend fun getRemoteFact(): BaseResponse<Fact>
}
