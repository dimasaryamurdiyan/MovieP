package com.singaludra.moviep.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singaludra.moviep.data.source.Resource
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.model.Review
import com.singaludra.moviep.domain.model.Video
import com.singaludra.moviep.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: IMovieRepository
): ViewModel() {
    private val _detailMovie = MutableLiveData<Resource<Movie>>()
    val detailMovie: LiveData<Resource<Movie>> get() = _detailMovie

    fun getDetailGame(id: Int) {
        viewModelScope.launch {
            repository.getDetailMovie(id).collect{
                _detailMovie.postValue(it)
            }
        }
    }

    private val _reviewList = MutableLiveData<Resource<List<Review>>>()
    val reviewList: LiveData<Resource<List<Review>>> get() = _reviewList

    fun getMovieReview(id: Int) {
        viewModelScope.launch {
            repository.getMovieReviews(id).collect{
                _reviewList.postValue(it)
            }
        }
    }

    private val _videoList = MutableLiveData<Resource<List<Video>>>()
    val videoList: LiveData<Resource<List<Video>>> get() = _videoList

    fun getMovieVideos(id: Int) {
        viewModelScope.launch {
            repository.getMovieVideos(id).collect{
                _videoList.postValue(it)
            }
        }
    }
}