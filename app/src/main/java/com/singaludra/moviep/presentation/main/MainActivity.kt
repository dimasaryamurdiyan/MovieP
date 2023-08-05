package com.singaludra.moviep.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.singaludra.moviep.databinding.ActivityMainBinding
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.presentation.main.adapter.MovieAdapter
import com.singaludra.moviep.presentation.main.adapter.MovieLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewBind()
        onViewObserve()
    }

    private fun onViewObserve() {
        viewModel.apply {
            lifecycleScope.launch {
                getMovies().collectLatest { movies ->
                    movieAdapter.submitData(movies)
                }
            }
        }
    }

    private fun onViewBind() {
        binding.apply {
            movieAdapter = MovieAdapter(object : MovieAdapter.OnClickListener {
                override fun onClickItem(item: Movie) {
                }
            })

            rvMovies.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter{movieAdapter.retry()},
                footer = MovieLoadStateAdapter{movieAdapter.retry()}
            )

            movieAdapter.addLoadStateListener { loadState -> renderUi(loadState) }

            binding.btnMoviesRetry.setOnClickListener { movieAdapter.retry() }
        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && movieAdapter.itemCount == 0

        binding.rvMovies.isVisible = !isListEmpty
        binding.tvMoviesEmpty.isVisible = isListEmpty

        // Only shows the list if refresh succeeds.
        binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
        // Show loading spinner during initial load or refresh.
        binding.progressBarMovies.isVisible = loadState.source.refresh is LoadState.Loading
        // Show the retry state if initial load or refresh fails.
        binding.btnMoviesRetry.isVisible = loadState.source.refresh is LoadState.Error
    }

}