package cz.johnyapps.cheers.counter.dto

import cz.johnyapps.cheers.global.dto.Beverage
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.dto.Entry
import cz.johnyapps.cheers.global.utils.TextUtils
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

data class CounterEntity(
    val id: Long,
    val beverage: Beverage,
    val volume: Float,
    val active: Boolean,
    val entries: MutableStateFlow<MutableList<Entry>>
) {
    constructor(counter: Counter): this(
        counter.id,
        counter.beverage,
        counter.volume,
        counter.active,
        MutableStateFlow(counter.entries)
    )

    suspend fun increaseCount() {
        val list = entries.value.toMutableList()
        list.add(Entry(Date()))
        entries.emit(list)
    }

    suspend fun decreaseCount() {
        val list = entries.value.toMutableList()
        if (list.isNotEmpty()) {
            list.removeLast()
            entries.emit(list)
        }
    }

    fun toGlobalDto(): Counter {
        return Counter(
            id,
            beverage,
            volume,
            active,
            entries.value
        )
    }

    fun getFullName(): String {
        return "${beverage.name} ${TextUtils.decimalToStringWithTwoDecimalDigits(volume.toDouble())}L"
    }
}