package com.firstnews.app.ui.news

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.firstnews.app.R
import com.firstnews.app.databinding.SectionNewsByCommentBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class SectionNewsByComment(
    private val owner: LifecycleOwner,
    private val label: String,
    private val news: LiveData<Resource<List<News>>>,
    private val onMovieClickListener: OnMovieClickListener
) : BindableItem<SectionNewsByCommentBinding>() {

    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun getLayout(): Int = R.layout.section_news_by_comment

    override fun initializeViewBinding(view: View): SectionNewsByCommentBinding = SectionNewsByCommentBinding.bind(view)

    override fun bind(viewBinding: SectionNewsByCommentBinding, position: Int) {
        initObserver(viewBinding)
    }

    private fun initObserver(binding: SectionNewsByCommentBinding) {
        news.observe(owner) { resource ->
            when(resource) {
                Resource.Loading -> binding.msvVerticalNews.showLoading()
                is Resource.Error -> {
                    val newsByCommentItems = resource.data?.map { news ->
                        NewsByCommentItem(news = news, onClick = onMovieClickListener::onMovieClick)
                    } ?: emptyList()
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsByCommentItems)
                    binding.tvLabel.text = label
                    binding.msvVerticalNews.showContent()
                    binding.rvNewsVertical.adapter = groupieAdapter
                }
                is Resource.Success -> {
                    val newsByCommentItems = resource.data.map { news ->
                        NewsByCommentItem(news = news, onClick = onMovieClickListener::onMovieClick)
                    }
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsByCommentItems)
                    binding.tvLabel.text = label
                    binding.msvVerticalNews.showContent()
                    binding.rvNewsVertical.adapter = groupieAdapter
                }
            }
        }

    }
}