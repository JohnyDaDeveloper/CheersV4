package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.johnyapps.cheers.dto.Counter
import cz.johnyapps.cheers.dto.Entry

@Entity(tableName = "counter_table")
data class CounterDbEntity(
    @PrimaryKey(autoGenerate = true)
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
}