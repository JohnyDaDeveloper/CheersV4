package cz.johnyapps.cheers.beveragedatabase

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.johnyapps.cheers.dto.Entry
import cz.johnyapps.cheers.Sound

object Converters {
    private val moshi = buildMoshi()
    private val entriesAdapter = buildEntriesAdapter()
    private val soundsAdapter = buildSoundsAdapter()

    @TypeConverter
    fun entriesToString(entries: List<Entry>): String {
        return entriesAdapter.toJson(entries)
    }

    @TypeConverter
    fun stringToEntries(string: String): List<Entry> {
        return entriesAdapter.fromJson(string) ?: ArrayList()
    }

    @TypeConverter
    fun soundsToString(sounds: List<Sound>): String {
        return soundsAdapter.toJson(sounds)
    }

    @TypeConverter
    fun stringToSounds(string: String): List<Sound> {
        return soundsAdapter.fromJson(string) ?: ArrayList()
    }

    private fun buildSoundsAdapter(): JsonAdapter<List<Sound>> {
        val type = Types.newParameterizedType(
            MutableList::class.java,
            Sound::class.java
        )

        return moshi.adapter(type)
    }

    private fun buildEntriesAdapter(): JsonAdapter<List<Entry>> {
        val type = Types.newParameterizedType(
            MutableList::class.java,
            Entry::class.java
        )

        return moshi.adapter(type)
    }

    private fun buildMoshi(): Moshi {
        return Moshi.Builder()
            .add(DateAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}