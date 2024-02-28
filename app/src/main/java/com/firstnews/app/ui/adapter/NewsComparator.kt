package com.firstnews.app.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.firstnews.app.domain.model.News

object NewsComparator : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}
