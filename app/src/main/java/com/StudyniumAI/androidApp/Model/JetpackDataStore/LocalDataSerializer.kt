package com.StudyniumAI.androidApp.Model.JetpackDataStore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object LocalDataSerializer: Serializer<LocalData> {
    override suspend fun readFrom(input: InputStream): LocalData {
        try {
            return Json.decodeFromString(
                deserializer = LocalData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e : SerializationException) {
            e.printStackTrace()
            return LocalData()
        }
    }

    override suspend fun writeTo(
        t: LocalData,
        output: OutputStream
    ) {
        output.write(
            Json.encodeToString(
                serializer = LocalData.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

    override val defaultValue: LocalData
        get() = LocalData()
}
