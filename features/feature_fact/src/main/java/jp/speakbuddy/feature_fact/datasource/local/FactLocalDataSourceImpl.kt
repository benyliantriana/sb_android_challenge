package jp.speakbuddy.feature_fact.datasource.local

import androidx.datastore.core.DataStore
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_base.di.IODispatcher
import jp.speakbuddy.lib_base.exception.localExceptionHandler
import jp.speakbuddy.lib_datastore.FactFavoriteListPreference
import jp.speakbuddy.lib_datastore.FactPreference
import jp.speakbuddy.lib_datastore.copy
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FactLocalDataSourceImpl @Inject constructor(
    private val factDataStore: DataStore<FactPreference>,
    private val favoriteFactDataStore: DataStore<FactFavoriteListPreference>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactLocalDataSource {
    companion object {
        private const val NO_SAVED_FACT = "No saved fact"
    }

    override suspend fun getLocalFact(): BaseResponse<FactUiData> = withContext(ioDispatcher) {
        var result: BaseResponse<FactUiData>
        try {
            val savedFact = factDataStore.data.first()
            result = if (savedFact.fact.isEmpty()) {
                BaseResponse.Failed(404, NO_SAVED_FACT)
            } else {
                val favoriteList = getLocalFavoriteFactList()
                val isFavorite = favoriteList.find { it.fact == savedFact.fact } != null
                BaseResponse.Success(
                    FactUiData(savedFact.fact, savedFact.length, isFavorite)
                )
            }
        } catch (ioException: IOException) {
            val exceptionData = localExceptionHandler(ioException)
            result = BaseResponse.Failed(
                code = exceptionData.code,
                message = exceptionData.message
            )
        }

        return@withContext result
    }

    override suspend fun saveFactToDataStore(factData: FactUiData) {
        withContext(ioDispatcher) {
            try {
                factDataStore.updateData {
                    it.copy {
                        this.fact = factData.fact
                        this.length = factData.length
                        this.isFavorite = factData.isFavorite
                    }
                }
            } catch (ioException: IOException) {
                // need a better approach for this
                throw ioException
            }
        }
    }

    override suspend fun saveFactToFavoriteDataStore(factData: FactUiData) {
        withContext(ioDispatcher) {
            try {
                if (factData.isFavorite) {
                    addFavoriteFact(factData)
                } else {
                    removeFavoriteFact(factData)
                }

            } catch (ioException: IOException) {
                throw ioException
            }
        }
    }

    override suspend fun alreadyFavoriteFact(factData: FactUiData) = withContext(ioDispatcher) {
        val favoriteList = getLocalFavoriteFactList()
        return@withContext favoriteList.find { it.fact == factData.fact } != null
    }

    private suspend fun getLocalFavoriteFactList(): MutableList<FactPreference> =
        withContext(ioDispatcher) {
            return@withContext favoriteFactDataStore.data.first().factFavoriteListList.toMutableList()
        }

    private suspend fun addFavoriteFact(factData: FactUiData) {
        withContext(ioDispatcher) {
            val favoriteFact = FactPreference.getDefaultInstance().copy {
                this.fact = factData.fact
                this.length = factData.length
                this.isFavorite = true
            }

            val favoriteList = getLocalFavoriteFactList()
            favoriteList.add(favoriteFact)
            favoriteFactDataStore.updateData {
                it.toBuilder()
                    .clearFactFavoriteList()
                    .addAllFactFavoriteList(favoriteList)
                    .build()
            }
        }
    }

    private suspend fun removeFavoriteFact(factData: FactUiData) {
        withContext(ioDispatcher) {
            val favoriteList = getLocalFavoriteFactList()
            val favoriteFact = favoriteList.find { it.fact == factData.fact }
            favoriteFact?.let {
                favoriteList.remove(it)
            }

            favoriteFactDataStore.updateData {
                it.toBuilder()
                    .clearFactFavoriteList()
                    .addAllFactFavoriteList(favoriteList)
                    .build()
            }
        }
    }
}
