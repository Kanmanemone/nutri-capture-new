package com.example.nutri_capture_new.db

import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: MainDAO) {

    @Transaction
    suspend fun insertDayMeal(dayMeal: DayMeal): Long {
        val dayId = dao.insertDay(Day(date = dayMeal.date))

        val meal = Meal(
            dayId = if (dayId == -1L) dao.getDayId(dayMeal.date) else dayId,
            time = dayMeal.time,
            name = dayMeal.name,
            nutritionInfo = dayMeal.nutritionInfo
        )

        return dao.insertMeal(meal)
    }

    fun getAllDayMeals(): Flow<List<DayMeal>> {
        return dao.getAllDayMeals()
    }

    @Transaction
    suspend fun deleteDayMeal(dayMeal: DayMeal): Int {
        val deletedRowCount = dao.deleteMeal(dayMeal.mealId)

        if (deletedRowCount == 0) {
            return 0

        } else {
            val mealCount = dao.getMealCountForDay(dayMeal.dayId)
            if (mealCount == 0) {
                dao.deleteDay(dayMeal.dayId)
            }
            return deletedRowCount
        }
    }
}