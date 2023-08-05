package com.singaludra.moviep.presentation.detail

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.singaludra.moviep.BuildConfig
import com.singaludra.moviep.R
import com.singaludra.moviep.data.source.Resource
import com.singaludra.moviep.databinding.ActivityDetailBinding
import com.singaludra.moviep.databinding.ActivityMainBinding
import com.singaludra.moviep.databinding.GenreChipBinding
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.model.Review
import com.singaludra.moviep.presentation.detail.adapter.ReviewAdapter
import com.singaludra.moviep.presentation.main.MainViewModel
import com.singaludra.moviep.utils.Utils
import com.singaludra.moviep.utils.loadImage
import com.singaludra.moviep.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

    private val movieId by lazy {
        intent.getIntExtra(EXTRA_DATA, 0)
    }

    private val reviewAdapter by lazy {
        ReviewAdapter()
    }

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchViewModelCallback()
        onViewBind()
        onViewObserve()
    }

    private fun onViewBind() {
        binding.apply {
            rvReview.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL ,false)

                adapter = reviewAdapter
            }
        }
    }

    private fun onViewObserve() {
        viewModel.apply {
            detailMovie.observe(this@DetailActivity){
                when(it){
                    is Resource.Success -> {
                        renderUi(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        this@DetailActivity.shortToast(it.message ?: "Something went wrong")
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                }
            }

            reviewList.observe(this@DetailActivity){
                when(it){
                    is Resource.Success -> {
                        reviewAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        this@DetailActivity.shortToast(it.message ?: "Something went wrong")
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }


    private fun renderUi(data: Movie?) {
        with(binding){
            tvTitle.text = data?.title
            tvOverview.text= data?.overview
            ivImgCourse.loadImage(
                BuildConfig.BACKDROP_PATH + data?.backdropImage
            )

            //region populate genre
            val genreList = data?.genres?.map {it.name}
            genreList?.forEach {
                val chip = createChip(it)
                chipGroup.addView(chip)
            }
            // end region populate genre
        }
    }

    private fun fetchViewModelCallback() {
        viewModel.apply {
            getDetailGame(movieId)
            getMovieReview(movieId)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = GenreChipBinding.inflate(layoutInflater).root
        chip.text = label
        chip.isClickable = false
        return chip
    }

    private fun hideLoading() {
        if (progressDialog != null && progressDialog?.isShowing!!) {
            progressDialog?.cancel()
        }
    }

    private fun showLoading() {
        hideLoading()
        progressDialog = Utils.showLoadingDialog(this)
    }


    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}