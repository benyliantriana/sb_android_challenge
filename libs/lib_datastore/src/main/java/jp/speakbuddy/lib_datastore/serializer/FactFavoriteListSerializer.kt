package jp.speakbuddy.lib_datastore.serializer

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import jp.speakbuddy.lib_datastore.FactFavoriteListPreference
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FactFavoriteListSerializer : Serializer<FactFavoriteListPreference> {
    override val defaultValue: FactFavoriteListPreference = FactFavoriteListPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FactFavoriteListPreference {
        return try {
            FactFavoriteListPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw IOException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FactFavoriteListPreference, output: OutputStream) {
        t.writeTo(output)
    }
}
