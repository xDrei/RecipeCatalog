package com.avastronomia.recipecatalog.domain.usecase

import com.avastronomia.recipecatalog.domain.repository.IRecipeRepository
import javax.inject.Inject

class GetRecipeWithPagingUseCase @Inject constructor(
    private val recipeRepository: IRecipeRepository
) {
    operator fun invoke() = recipeRepository.getRecipeWithPager()
}