package com.example.nutri_capture_new.db

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: MainDAO) {
    suspend fun insertMeal(meal: Meal, date: LocalDate): Long {
        val dayId = dao.insertDay(Day(date = date))

        return if (dayId == -1L) {
            dao.insertMeal(meal.copy(dayId = dao.getDayId(date)))
        } else {
            dao.insertMeal(meal.copy(dayId = dayId))
        }
    }

    suspend fun deleteMeal(meal: Meal): Int {
        val deletedRowCount = dao.deleteMeal(meal)

        if (deletedRowCount == 0) {
            return 0

        } else {
            val mealCount = dao.getMealCountForDay(meal.dayId)
            if (mealCount == 0) {
                dao.deleteDay(meal.dayId)
            }
            return deletedRowCount
        }
    }

    fun getAllDayMeals(): Flow<List<DayMealView>> {
        return dao.getAllDayMeals()
    }

    suspend fun deleteDayMeal(dayMeal: DayMealView): Int {
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