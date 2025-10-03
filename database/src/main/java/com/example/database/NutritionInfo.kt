package com.example.database

import androidx.room.Embedded

data class NutritionInfo(
    // 과식 정도값
    @Embedded(prefix = "overeating_")
    val overeating: NutritionDetail = NutritionDetail(
        name = "과식한 정도",
        value = 0,
        iconId = R.drawable.overeating,
        nutritionCategory = NutritionCategory.BAD
    ),

    // 정제당 섭취 정도값
    @Embedded(prefix = "refined_sugar_")
    val refinedSugar: NutritionDetail = NutritionDetail(
        name = "정제당",
        value = 0,
        iconId = R.drawable.refined_sugar,
        nutritionCategory = NutritionCategory.BAD
    ),

    // 정제 곡물 섭취 정도값
    @Embedded(prefix = "refined_grain_")
    val refinedGrain: NutritionDetail = NutritionDetail(
        name = "정제 곡물",
        value = 0,
        iconId = R.drawable.refined_grain,
        nutritionCategory = NutritionCategory.BAD
    ),

    // 밀가루 섭취 정도값
    @Embedded(prefix = "flour_")
    val flour: NutritionDetail = NutritionDetail(
        name = "밀가루",
        value = 0,
        iconId = R.drawable.flour,
        nutritionCategory = NutritionCategory.BAD
    ),

    // 섬유질 섭취 정도값
    @Embedded(prefix = "fiber_")
    val fiber: NutritionDetail = NutritionDetail(
        name = "식이섬유",
        value = 0,
        iconId = R.drawable.fiber,
        nutritionCategory = NutritionCategory.GOOD
    ),

    // 단백질 섭취 정도값
    @Embedded(prefix = "protein_")
    val protein: NutritionDetail = NutritionDetail(
        name = "단백질",
        value = 0,
        iconId = R.drawable.protein,
        nutritionCategory = NutritionCategory.GOOD
    ),

    // 나트륨 섭취 정도값
    @Embedded(prefix = "sodium_")
    val sodium: NutritionDetail = NutritionDetail(
        name = "나트륨",
        value = 0,
        iconId = R.drawable.sodium,
        nutritionCategory = NutritionCategory.BAD
    ),
) {
    fun toMutableMap(): MutableMap<String, NutritionDetail> {
        return mutableMapOf(
            "overeating" to overeating,
            "refinedSugar" to refinedSugar,
            "refinedGrain" to refinedGrain,
            "flour" to flour,
            "fiber" to fiber,
            "protein" to protein,
            "sodium" to sodium
        )
    }

    fun updateNutritionDetail(nutritionKey: String, update: (Int) -> Int): NutritionInfo {
        val updatedMap = this.toMutableMap().apply {
            get(nutritionKey)?.let { detail ->
                this[nutritionKey] = detail.copy(value = update(detail.value))
            }
        }

        return NutritionInfo(
            overeating = updatedMap["overeating"] ?: this.overeating,
            refinedSugar = updatedMap["refinedSugar"] ?: this.refinedSugar,
            refinedGrain = updatedMap["refinedGrain"] ?: this.refinedGrain,
            flour = updatedMap["flour"] ?: this.flour,
            fiber = updatedMap["fiber"] ?: this.fiber,
            protein = updatedMap["protein"] ?: this.protein,
            sodium = updatedMap["sodium"] ?: this.sodium
        )
    }
}