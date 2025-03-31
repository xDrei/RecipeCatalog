package com.avastronomia.recipecatalog.utils

import com.avastronomia.datasource.local.entity.RecipeEntity
import com.avastronomia.recipecatalog.data.remote.dto.RecipeDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {
    private val testRecipeEntity = RecipeEntity(
        id = 1,
        name = "Test Recipe",
        ingredients = listOf("Ingredient 1", "Ingredient 2"),
        instructions = listOf("Step 1", "Step 2"),
        prepTimeMinutes = 5,
        cookTimeMinutes = 6,
        servings = 1,
        difficulty = "Medium",
        cuisine = "Japanese",
        caloriesPerServing = 100,
        tags = listOf("Tempura", "Ramen"),
        userId = 123,
        image = "test image",
        rating = 1.1,
        reviewCount = 100,
        mealType = listOf("Dinner")
    )

    private val testRecipeDto = RecipeDto(
        id = 1,
        name = "Test Recipe DTO",
        ingredients = listOf("Ingredient 1", "Ingredient 1"),
        instructions = listOf("Step 1", "Step 2"),
        prepTimeMinutes = 5,
        cookTimeMinutes = 6,
        servings = 1,
        difficulty = "Medium",
        cuisine = "Japanese",
        caloriesPerServing = 400,
        tags = listOf("Tempura", "Ramen"),
        userId = 123,
        image = "test image",
        rating = 1.1,
        reviewCount = 100,
        mealType = listOf("Dinner")
    )

    @Test
    fun toRecipeMapsCorrectly() {
        val result = testRecipeEntity.toRecipe()

        assertEquals(testRecipeEntity.id, result.id)
        assertEquals(testRecipeEntity.name, result.name)
        assertEquals(testRecipeEntity.ingredients, result.ingredients)
        assertEquals(testRecipeEntity.instructions, result.instructions)
        assertEquals(testRecipeEntity.prepTimeMinutes, result.prepTimeMinutes)
        assertEquals(testRecipeEntity.cookTimeMinutes, result.cookTimeMinutes)
        assertEquals(testRecipeEntity.servings, result.servings)
        assertEquals(testRecipeEntity.difficulty, result.difficulty)
        assertEquals(testRecipeEntity.cuisine, result.cuisine)
        assertEquals(testRecipeEntity.caloriesPerServing, result.caloriesPerServing)
        assertEquals(testRecipeEntity.tags, result.tags)
        assertEquals(testRecipeEntity.userId, result.userId)
        assertEquals(testRecipeEntity.image, result.image)
        assertEquals(testRecipeEntity.reviewCount, result.reviewCount)
        assertEquals(testRecipeEntity.mealType, result.mealType)
    }

    @Test
    fun toRecipeListMapsCorrectly() {
        val entityList = listOf(testRecipeEntity, testRecipeEntity.copy(id = 5))
        val recipeList = entityList.toRecipeList()

        assertEquals(entityList.size, recipeList.size)
        assertEquals(entityList[0].id, recipeList[0].id)
        assertEquals(entityList[1].id, recipeList[1].id)
    }

    @Test
    fun toRecipeEntityMapsCorrectly() {
        val recipeEntity = testRecipeDto.toRecipeEntity()

        assertEquals(testRecipeDto.id, recipeEntity.id)
        assertEquals(testRecipeDto.name, recipeEntity.name)
        assertEquals(testRecipeDto.ingredients, recipeEntity.ingredients)
        assertEquals(testRecipeDto.instructions, recipeEntity.instructions)
        assertEquals(testRecipeDto.prepTimeMinutes, recipeEntity.prepTimeMinutes)
        assertEquals(testRecipeDto.cookTimeMinutes, recipeEntity.cookTimeMinutes)
        assertEquals(testRecipeDto.servings, recipeEntity.servings)
        assertEquals(testRecipeDto.difficulty, recipeEntity.difficulty)
        assertEquals(testRecipeDto.cuisine, recipeEntity.cuisine)
        assertEquals(testRecipeDto.caloriesPerServing, recipeEntity.caloriesPerServing)
        assertEquals(testRecipeDto.tags, recipeEntity.tags)
        assertEquals(testRecipeDto.userId, recipeEntity.userId)
        assertEquals(testRecipeDto.image, recipeEntity.image)
        assertEquals(testRecipeDto.rating, recipeEntity.rating)
        assertEquals(testRecipeDto.reviewCount, recipeEntity.reviewCount)
        assertEquals(testRecipeDto.mealType, recipeEntity.mealType)
    }
}