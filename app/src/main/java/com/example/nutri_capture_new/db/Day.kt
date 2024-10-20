package com.example.nutri_capture_new.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "day_table",
    indices = [Index(value = ["day_date"], unique = true)]
)
data class Day(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "day_id")
    val dayId: Long = 0,

    @ColumnInfo(name = "day_date")
    var date: LocalDate,
)