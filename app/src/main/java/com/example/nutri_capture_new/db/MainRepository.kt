package com.example.nutri_capture_new.db

import java.time.LocalDate

class MainRepository(private val dao: MainDAO) {
    suspend fun insertMeal(meal: Meal, date: LocalDate): Long {
        var dayId = dao.getDayId(date)
        if (dayId == null) {
            dayId = dao.insertDay(Day(date = date))
        }
        return dao.insertMeal(meal.copy(dayId = dayId))
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

    suspend fun getMealsOrderedByTime(targetDate: LocalDate): List<Meal> {
        return dao.getMealsOrderedByTime(targetDate)
    }

    suspend fun getAllDayMeals(): List<DayMealView> {
        return dao.getAllDayMeals()
    }

    suspend fun getNextDayMealsAfter(lastDayMeal: DayMealView, limit: Int): List<DayMealView> {
        return dao.getNextDayMealsAfter(
            lastDayMeal.date, lastDayMeal.time, lastDayMeal.mealId, limit
        )
    }

    suspend fun getDayMeal(mealId: Long): DayMealView {
        return dao.getDayMeal(mealId)
    }
}