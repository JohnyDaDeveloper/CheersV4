package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.johnyapps.cheers.Icon

@Entity(tableName = "category_table")
data class CategoryDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Icon,
    @ColumnInfo(name = "selected_counter_id")
    val selectedCounterId: Int,
    @ColumnInfo(name = "order")
    val order: Int
)