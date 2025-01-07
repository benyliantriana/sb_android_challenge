package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.lib_base.exception.remoteExceptionHandler
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factLocalDataSource: FactLocalDataSource,
    private val factRemoteDataSource: FactRemoteDataSource,
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<FactUiData>> = flow {
        emit(BaseResponse.Loading)
        emit(getStoredFact())
    }

    override suspend fun updateFact(): Flow<BaseResponse<FactUiData>> = flow {
        emit(BaseResponse.Loading)
        val remoteFact = getRemoteFact()
        if (remoteFact is BaseResponse.Success) {
            val alreadyInFavorite = factLocalDataSource.alreadyFavoriteFact(remoteFact.data)
            storeFact(
                remoteFact.data.copy(isFavorite = alreadyInFavorite)
            )
            emit(
                BaseResponse.Success(remoteFact.data.copy(isFavorite = alreadyInFavorite))
            )
        } else {
            emit(remoteFact)
        }
    }.catch {
        val exceptionData = remoteExceptionHandler(it)
        emit(BaseResponse.Failed(exceptionData.code, exceptionData.message))
    }

    override suspend fun saveFactToFavoriteDataStore(fact: FactUiData) {
        factLocalDataSource.saveFactToFavoriteDataStore(fact)
    }

    private suspend fun getRemoteFact(): BaseResponse<FactUiData> {
        return factRemoteDataSource.getRemoteFact()
    }

    private suspend fun getStoredFact(): BaseResponse<FactUiData> {
        return factLocalDataSource.getLocalFact()
    }

    private suspend fun storeFact(fact: FactUiData) {
        factLocalDataSource.saveFactToDataStore(fact)
    }
}
