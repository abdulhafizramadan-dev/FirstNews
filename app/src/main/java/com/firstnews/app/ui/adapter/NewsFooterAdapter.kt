package com.firstnews.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.firstnews.app.databinding.PlaceholderSearchNewsBinding

class NewsFooterAdapter : LoadStateAdapter<NewsFooterAdapter.NewsFooterViewHolder>() {
    inner class NewsFooterViewHolder(binding: PlaceholderSearchNewsBinding) : ViewHolder(binding.root)

    override fun onBindViewHolder(holder: NewsFooterViewHolder, loadState: LoadState) {}

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NewsFooterViewHolder {
        val binding = PlaceholderSearchNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFooterViewHolder(binding)
    }

}