package com.example.nutri_capture_new.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
    ],
    indices = [Index(value = ["day_id"])]
)
data class Meal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meal_id")
    val mealId: Long = 0,

    // 외래키
    @ColumnInfo(name = "day_id")
    val dayId: Long,

    @ColumnInfo(name = "meal_time")
    var time: LocalTime,

    @ColumnInfo(name = "meal_name")
    var name: String,

    @Embedded
    val nutritionInfo: NutritionInfo,
)