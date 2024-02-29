package com.firstnews.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.firstnews.app.R
import com.firstnews.app.databinding.ItemNewsRecommendationGridBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.listener.OnMovieClickListener

class NewsRecommendationAdapter(private val onMovieClickListener: OnMovieClickListener) : ListAdapter<News, NewsRecommendationAdapter.NewsRecommendationViewHolder>(NewsComparator) {

    inner class NewsRecommendationViewHolder(
        private val binding: ItemNewsRecommendationGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            with(binding) {
                tvTitle.text = news.title
                ivPhoto.load(news.urlToImage) {
                    placeholder(R.drawable.img_placeholder_small)
                    error(R.drawable.img_placeholder_small)
                    crossfade(true)
                }
                root.setOnClickListener { onMovieClickListener.onMovieClick(news) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsRecommendationViewHolder {
        val binding = ItemNewsRecommendationGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsRecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsRecommendationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}