package com.example.nutri_capture_new.db

import androidx.room.ColumnInfo

data class NutritionInfo(
    // 과식 정도값
    @ColumnInfo(name = "overeating_excess")
    val overeatingExcess: Int,

    // 정제당 섭취 정도값
    @ColumnInfo(name = "refined_sugar_excess")
    val refinedSugarExcess: Int,

    // 정제 곡물 섭취 정도값
    @ColumnInfo(name = "refined_grain_excess")
    val refinedGrainExcess: Int,

    // 밀가루 섭취 정도값
    @ColumnInfo(name = "flour_excess")
    val flourExcess: Int,

    // 섬유질 섭취 정도값
    @ColumnInfo(name = "fiber_quality")
    val fiberQuality: Int,

    // 단백질 섭취 정도값
    @ColumnInfo(name = "protein_quality")
    val proteinQuality: Int,

    // 나트륨 섭취 정도값
    @ColumnInfo(name = "sodium_excess")
    val sodiumExcess: Int
)