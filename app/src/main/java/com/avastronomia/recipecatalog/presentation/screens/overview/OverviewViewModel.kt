package com.avastronomia.recipecatalog.presentation.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avastronomia.recipecatalog.domain.model.Recipe
import com.avastronomia.recipecatalog.domain.usecase.GetNetworkConnectivityUseCase
import com.avastronomia.recipecatalog.domain.usecase.GetPopularRecipeUseCase
import com.avastronomia.recipecatalog.domain.usecase.GetRecipeWithPagingUseCase
import com.avastronomia.recipecatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getPopularRecipeUseCase: GetPopularRecipeUseCase,
    private val getRecipeWithPagingUseCase: GetRecipeWithPagingUseCase,
    private val networkConnectivityUseCase: GetNetworkConnectivityUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<OverviewScreenState> = MutableStateFlow(OverviewScreenState())
    val state = _state.asStateFlow()

    private var getRecipeJob: Job? = null
    private var getPopularRecipeJob: Job? = null

    init {
        _state.update {
            it.copy(noNetwork = !networkConnectivityUseCase())
        }
    }

    val recipes: Flow<PagingData<Recipe>> =
            getRecipeWithPagingUseCase.invoke()
                .cachedIn(viewModelScope)

    fun callGetPopularRecipe() {
        if (getPopularRecipeJob?.isActive == true) {
            return
        } else {
            getPopularRecipeJob = viewModelScope.launch {
                getPopularRecipe()
            }
        }
    }

    private fun checkIfPageIsLoading(): Boolean {
        return getRecipeJob?.isActive == true || getPopularRecipeJob?.isActive == true
    }

    private suspend fun getPopularRecipe() {
        getPopularRecipeUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = checkIfPageIsLoading(),
                            popularRecipes = result.data ?: emptyList(),
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = checkIfPageIsLoading(),
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}