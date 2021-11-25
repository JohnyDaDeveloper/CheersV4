package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.johnyapps.cheers.global.dto.Beverage

@Entity(tableName = "beverage_table")
data class BeverageDbEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "beverage_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "alcohol")
    val alcohol: Float,
    @ColumnInfo(name = "color")
    val color: Int,
    @ColumnInfo(name = "text_color")
    val textColor: Int,
    @ColumnInfo(name = "description")
    val description: String?
) {
    constructor(beverage: Beverage): this(
        beverage.id,
        beverage.name,
        beverage.alcohol,
        beverage.color,
        beverage.textColor,
        beverage.description
    )

    fun toGlobalDto(): Beverage {
        return Beverage(
            id,
            name,
            alcohol,
            color,
            textColor,
            description
        )
    }
}