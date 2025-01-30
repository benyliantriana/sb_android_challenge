package jp.speakbuddy.feature_fact.datasource.remote

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_network.response.BaseResponse

interface FactRemoteDataSource {
    suspend fun getRemoteFact(): BaseResponse<FactUiData>
}
