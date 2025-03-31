package com.avastronomia.recipecatalog.presentation.screens.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avastronomia.recipecatalog.domain.usecase.GetNetworkConnectivityUseCase
import com.avastronomia.recipecatalog.domain.usecase.GetRecipeDetailsUseCase
import com.avastronomia.recipecatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val getNetworkConnectivityUseCase: GetNetworkConnectivityUseCase
): ViewModel() {

    private val _state: MutableStateFlow<RecipeDetailsState> = MutableStateFlow(RecipeDetailsState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(noNetwork = !getNetworkConnectivityUseCase())
        }
    }

    fun callGetRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            getRecipeDetails(recipeId)
        }
    }


    suspend fun getRecipeDetails(recipeId: String) {
        getRecipeDetailsUseCase(recipeId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            recipe = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}