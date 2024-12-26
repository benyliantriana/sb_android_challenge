package jp.speakbuddy.edisonandroidexercise.ui.fake

import FactSerializer
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import jp.speakbuddy.lib_datastore.FactPreference
import org.junit.rules.TemporaryFolder

private val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
val testDataStore: DataStore<FactPreference> = DataStoreFactory.create(
    serializer = FactSerializer,
    produceFile = { tmpFolder.newFile("fact_preference.pb") }
)