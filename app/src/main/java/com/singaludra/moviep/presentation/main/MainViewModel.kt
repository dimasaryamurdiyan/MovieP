package com.singaludra.moviep.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesRepository: IMovieRepository
): ViewModel(){
    fun getMovies(): Flow<PagingData<Movie>> {
        return moviesRepository.getMovies()
            .cachedIn(viewModelScope)
    }
}