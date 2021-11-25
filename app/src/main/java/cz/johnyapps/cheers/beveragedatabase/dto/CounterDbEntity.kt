package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.johnyapps.cheers.global.dto.Beverage
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.dto.Entry

@Entity(tableName = "counter_table")
data class CounterDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "counter_id")
    val id: Long,
    @ColumnInfo(name = "beverage_id")
    val beverageId: Long,
    @ColumnInfo(name = "volume")
    val volume: Float,
    @ColumnInfo(name = "active")
    val active: Boolean,
    @ColumnInfo(name = "entries")
    val entries: MutableList<Entry>
) {
    constructor(counter: Counter): this(
        counter.id,
        counter.beverage.id,
        counter.volume,
        counter.active,
        counter.entries
    )

    fun toGlobalDto(beverageDbEntity: Beverage): Counter {
        return Counter(
            id,
            beverageDbEntity,
            volume,
            active,
            entries
        )
    }
}