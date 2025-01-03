package jp.speakbuddy.feature_fact.fake

import FactSerializer
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import jp.speakbuddy.lib_datastore.FactPreference
import org.junit.jupiter.api.io.TempDir
import java.io.File

@TempDir
@JvmField
// this prevent data store to make fact_preference.pb in the project
val tempFolder: File = File(System.getProperty("java.io.tmpdir")!!)


val testDataStore: DataStore<FactPreference> = DataStoreFactory.create(
    serializer = FactSerializer,
    produceFile = { File(tempFolder, "fact_preference.pb") }
)
