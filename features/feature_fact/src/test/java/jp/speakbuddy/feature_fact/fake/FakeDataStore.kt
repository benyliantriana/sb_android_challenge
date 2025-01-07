package jp.speakbuddy.feature_fact.fake

import FactSerializer
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import jp.speakbuddy.lib_datastore.FactFavoriteListPreference
import jp.speakbuddy.lib_datastore.FactPreference
import jp.speakbuddy.lib_datastore.serializer.FactFavoriteListSerializer
import org.junit.Rule
import org.junit.rules.TemporaryFolder

// this prevent data store to make fact_preference.pb in the project
@get:Rule
val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()


val testFactDataStore: DataStore<FactPreference> = DataStoreFactory.create(
    serializer = FactSerializer,
    produceFile = { tmpFolder.newFile("fact_preference.pb") }
)


val testFactFavoriteListDataStore: DataStore<FactFavoriteListPreference> = DataStoreFactory.create(
    serializer = FactFavoriteListSerializer,
    produceFile = { tmpFolder.newFile("fact_facorite_preference.pb") }
)
