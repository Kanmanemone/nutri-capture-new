package com.example.nutri_capture_new.db

import androidx.room.ColumnInfo

data class NutritionInfo(
    // 과식 정도값
    @ColumnInfo(name = "overeating_excess")
    var overeatingExcess: Int = 0,

    // 정제당 섭취 정도값
    @ColumnInfo(name = "refined_sugar_excess")
    var refinedSugarExcess: Int = 0,

    // 정제 곡물 섭취 정도값
    @ColumnInfo(name = "refined_grain_excess")
    var refinedGrainExcess: Int = 0,

    // 밀가루 섭취 정도값
    @ColumnInfo(name = "flour_excess")
    var flourExcess: Int = 0,

    // 섬유질 섭취 정도값
    @ColumnInfo(name = "fiber_quality")
    var fiberQuality: Int = 0,

    // 단백질 섭취 정도값
    @ColumnInfo(name = "protein_quality")
    var proteinQuality: Int = 0,

    // 나트륨 섭취 정도값
    @ColumnInfo(name = "sodium_excess")
    var sodiumExcess: Int = 0
)