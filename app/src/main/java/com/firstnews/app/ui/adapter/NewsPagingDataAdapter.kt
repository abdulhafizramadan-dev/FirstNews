package com.firstnews.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.firstnews.app.R
import com.firstnews.app.databinding.ItemNewsVerticalBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.toRelativeDateFormat

class NewsPagingDataAdapter(
    private val onMovieClickListener: OnMovieClickListener
) : PagingDataAdapter<News, NewsPagingDataAdapter.NewsVerticalSectionViewHolder>(NewsComparator) {

    inner class NewsVerticalSectionViewHolder(private val binding: ItemNewsVerticalBinding) : ViewHolder(binding.root) {
        fun bind(news: News) {
            with(binding) {
                tvTitle.text = news.title
                tvFooter.text = root.context.getString(R.string.format_footer, news.source, news.publishedAt.toRelativeDateFormat())
                ivPhoto.load(news.urlToImage) {
                    placeholder(R.drawable.img_placeholder_small)
                    error(R.drawable.img_placeholder_small)
                    crossfade(true)
                }
                btnSave.setOnClickListener {
                    onMovieClickListener.onSaveMovieClick(news)
                }
                root.setOnClickListener {
                    onMovieClickListener.onMovieClick(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsVerticalSectionViewHolder {
        val binding = ItemNewsVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsVerticalSectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsVerticalSectionViewHolder, position: Int) {
        val news = getItem(position)
        if (news != null) {
            holder.bind(news)
        }
    }
}