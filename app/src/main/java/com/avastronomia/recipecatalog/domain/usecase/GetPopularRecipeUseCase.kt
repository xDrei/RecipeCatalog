package com.avastronomia.recipecatalog.domain.usecase

import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.domain.repository.IRecipeRepository
import com.avastronomia.recipecatalog.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularRecipeUseCase @Inject constructor(
    private val recipeRepository: IRecipeRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Recipe>>> {
        return recipeRepository.getPopularRecipe()
    }

}