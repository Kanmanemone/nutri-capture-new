package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Ignore

data class NutritionDetail(
    @Ignore
    val name: String = "기본 이름",

    @ColumnInfo(name = "value")
    var value: Int = 0,

    @Ignore
    val iconId: Int = R.drawable.default_nutrition,

    @Ignore
    val nutritionCategory: NutritionCategory = NutritionCategory.MODERATE
)