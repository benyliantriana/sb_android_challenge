package jp.speakbuddy.feature_fact.fake

import FactSerializer
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import jp.speakbuddy.lib_datastore.FactPreference
import org.junit.jupiter.api.io.TempDir
import java.io.File

@TempDir
@JvmField
var tempFolder: File = File("fact_preference.pb")

val testDataStore: DataStore<FactPreference> = DataStoreFactory.create(
    serializer = FactSerializer,
    produceFile = { tempFolder }
)
