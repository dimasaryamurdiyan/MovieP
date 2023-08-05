package com.singaludra.moviep.presentation.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.singaludra.moviep.databinding.ItemReviewBinding
import com.singaludra.moviep.domain.model.Review

class ReviewAdapter(): RecyclerView.Adapter<ReviewAdapter.ViewHolder>()  {
    inner class ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review){
            binding.apply {
                tvAuthor.text = item.author
                tvReview.text = item.content
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemReviewBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ReviewAdapter.ViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount()= differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Review>(){
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
}