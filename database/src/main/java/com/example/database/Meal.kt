package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(
    tableName = "meal_table",
    foreignKeys = [
        ForeignKey(
            entity = Day::class,
            parentColumns = ["day_id"],
            childColumns = ["day_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Meal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meal_id")
    val mealId: Long = 0,

    // 외래키
    @ColumnInfo(name = "day_id")
    val dayId: Long = 0,

    @ColumnInfo(name = "meal_time")
    var time: LocalTime,

    @ColumnInfo(name = "meal_name")
    var name: String,

    @Embedded
    val nutritionInfo: NutritionInfo,
)