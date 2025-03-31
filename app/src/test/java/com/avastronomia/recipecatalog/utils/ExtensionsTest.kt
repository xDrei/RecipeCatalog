package com.avastronomia.recipecatalog.utils

import com.avastronomia.recipecatalog.components.RecipeCardData
import com.avastronomia.recipecatalog.components.RecipeTileData
import com.avastronomia.recipecatalog.domain.model.Recipe
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    private val testRecipes = listOf(
        Recipe(
            id = 1,
            name = "Test Recipe 1",
            cuisine = "Test Cuisine 1",
            rating = 4.5,
            difficulty = "Medium",
            prepTimeMinutes = 30,
            cookTimeMinutes = 45,
            image = "test image",
            ingredients = listOf("test1", "test2"),
            instructions = listOf("test1", "test2")
        ),
        Recipe(
            id = 2,
            name = "Test Recipe 2",
            cuisine = "Test Cuisine 2",
            rating = 3.5,
            difficulty = "Easy",
            prepTimeMinutes = 20,
            cookTimeMinutes = 30,
            image = "test image 2",
            ingredients = listOf("test1", "test2"),
            instructions = listOf("test1", "test2")
        )
    )

    @Test
    fun toTimeReturnsEmptyString() {
        assertEquals("", 0.toTime())
    }

    @Test
    fun toTimereturnsOnlyMinutes() {
        val testValue = 30
        val testValue2 = 1

        assertEquals("30 minutes", testValue.toTime())
        assertEquals("1 minute", testValue2.toTime())
    }

    @Test
    fun toTimeReturnsOnlyHours() {
        val testValue = 60

        assertEquals("1 hour", testValue.toTime())
    }

    @Test
    fun toTimeReturnsHoursAndMinutes() {
        val testValue = 90
        val testvalue2 = 120
        val testValue3 = 181

        assertEquals("1 hour and 30 minutes", testValue.toTime())
        assertEquals("2 hours", testvalue2.toTime())
        assertEquals("3 hours and 1 minute", testValue3.toTime())
    }

    @Test
    fun convertToRecipeCardDataListReturnsEmptyList() {
        val result = emptyList<Recipe>().convertToRecipeCardDataList()
        assertEquals(emptyList<RecipeCardData>(), result)
    }

    @Test
    fun convertToRecipeTileDataListReturnsEmptyList() {
        val result = emptyList<Recipe>().convertToRecipeTileDataList()
        assertEquals(emptyList<RecipeTileData>(), result)
    }

    @Test
    fun convertToRecipeCardDataListReturnsListOfRecipeCardData() {
        val result = testRecipes.convertToRecipeCardDataList()
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Test Recipe 1", result[0].name)
        assertEquals("test image", result[0].imageUrl)
        assertEquals("2", result[1].id)
        assertEquals("Test Recipe 2", result[1].name)
        assertEquals("test image 2", result[1].imageUrl)
    }

    @Test
    fun convertToRecipeTileDataListReturnsListOfRecipeTileData() {
        val result = testRecipes.convertToRecipeTileDataList()
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Test Recipe 1", result[0].name)
        assertEquals("test image", result[0].imageUrl)
        assertEquals("Test Cuisine 1", result[0].cuisine)
        assertEquals(30, result[0].prepTime)
        assertEquals(45, result[0].cookTime)
        assertEquals("2", result[1].id)
        assertEquals("Test Recipe 2", result[1].name)
        assertEquals("test image 2", result[1].imageUrl)
        assertEquals("Test Cuisine 2", result[1].cuisine)
        assertEquals(20, result[1].prepTime)
        assertEquals(30, result[1].cookTime)
    }

    @Test
    fun convertToRecipeTileDataReturnsCorrectRecipeTileData() {
        val testValue = testRecipes.first()
        val result = testValue.convertToRecipeTileData()

        assertEquals("1", result.id)
        assertEquals("Test Recipe 1", result.name)
        assertEquals("test image", result.imageUrl)
        assertEquals("Test Cuisine 1", result.cuisine)
        assertEquals(30, result.prepTime)
        assertEquals(45, result.cookTime)
    }
}