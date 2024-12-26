import androidx.datastore.core.IOException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import jp.speakbuddy.lib_datastore.FactPreference
import java.io.InputStream
import java.io.OutputStream

object FactSerializer : Serializer<FactPreference> {
    override val defaultValue: FactPreference = FactPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FactPreference {
        return try {
            FactPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw IOException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FactPreference, output: OutputStream) {
        t.writeTo(output)
    }
}
