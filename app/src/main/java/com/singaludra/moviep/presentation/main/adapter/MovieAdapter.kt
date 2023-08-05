package com.singaludra.moviep.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.singaludra.moviep.BuildConfig
import com.singaludra.moviep.databinding.ItemMoviePosterBinding
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.utils.loadImage

class MovieAdapter(private val itemClick: OnClickListener) : PagingDataAdapter<Movie, MovieAdapter.MoviePosterViewHolder>(
    MovieDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewHolder {
        val holder = MoviePosterViewHolder(
            ItemMoviePosterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnClickListener {
        fun onClickItem(item: Movie)
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    inner class MoviePosterViewHolder(
        val binding: ItemMoviePosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) {
            item?.let {
                binding.ivMoviePoster.loadImage(BuildConfig.POSTER_PATH+it.image)
            }

            binding.root.setOnClickListener {
                item?.let { movie -> itemClick.onClickItem(movie) }
            }
        }
    }

}

