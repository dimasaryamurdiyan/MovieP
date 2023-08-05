package com.singaludra.moviep.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.singaludra.moviep.R
import com.singaludra.moviep.databinding.ActivityMainBinding
import com.singaludra.moviep.domain.model.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewBind()
        onViewObserve()
    }

    private fun onViewObserve() {
        TODO("Not yet implemented")
    }

    private fun onViewBind() {
        binding.apply {
            movieAdapter = MovieAdapter(object : MovieAdapter.OnClickListener{
                override fun onClickItem(item: Movie) {
                }
            })

            rvMovies.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header =,
                footer = 
            )

        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

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