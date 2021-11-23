package cz.johnyapps.cheers.beveragedatabase

import com.squareup.moshi.*
import java.util.*

class DateAdapter: JsonAdapter<Date>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Date {
        val long = reader.nextLong()
        return Date(long)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.time ?: 0L)
    }
}