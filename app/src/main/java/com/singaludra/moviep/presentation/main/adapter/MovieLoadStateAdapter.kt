package com.singaludra.moviep.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.singaludra.moviep.R
import com.singaludra.moviep.databinding.ItemMoviesLoadStateFooterBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.MovieLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.create(parent, retry)
    }

    class MovieLoadStateViewHolder(
        private val binding: ItemMoviesLoadStateFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnMoviesRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvMoviesErrorDescription.text = loadState.error.localizedMessage
            }
            binding.progressMoviesLoadMore.isVisible = loadState is LoadState.Loading
            binding.btnMoviesRetry.isVisible = loadState is LoadState.Error
            binding.tvMoviesErrorDescription.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movies_load_state_footer, parent, false)
                val binding = ItemMoviesLoadStateFooterBinding.bind(view)
                return MovieLoadStateViewHolder(binding, retry)
            }
        }
    }
}