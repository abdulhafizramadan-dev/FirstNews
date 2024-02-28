package com.firstnews.app.ui.news

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.firstnews.app.R
import com.firstnews.app.databinding.SectionNewsVerticalBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class SectionNewsVertical(
    private val owner: LifecycleOwner,
    private val label: String,
    private val news: LiveData<Resource<List<News>>>,
    private val onSeeAllClick: () -> Unit,
    private val onMovieClickListener: OnMovieClickListener
) : BindableItem<SectionNewsVerticalBinding>() {

    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun getLayout(): Int = R.layout.section_news_vertical

    override fun initializeViewBinding(view: View): SectionNewsVerticalBinding = SectionNewsVerticalBinding.bind(view)

    override fun bind(viewBinding: SectionNewsVerticalBinding, position: Int) {
        initView(viewBinding)
        initObserver(viewBinding)
    }

    private fun initView(binding: SectionNewsVerticalBinding) {
        binding.tvLabel.text = label
        binding.btnSeeAll.setOnClickListener { onSeeAllClick() }
    }

    private fun initObserver(binding: SectionNewsVerticalBinding) {
        val context = binding.root.context
        news.observe(owner) { resource ->
            when (resource) {
                Resource.Loading -> binding.msvVerticalNews.showLoading()
                is Resource.Error -> {
                    val newsVerticalItems = resource.data?.map { news ->
                        NewsVerticalItem(
                            news = news,
                            onMovieClickListener = onMovieClickListener
                        )
                    } ?: emptyList()
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsVerticalItems)
                    binding.msvVerticalNews.showContent()
                    binding.rvNewsVertical.adapter = groupieAdapter
                    Toast.makeText(context, resource.error.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val newsVerticalItems = resource.data.map { news ->
                        NewsVerticalItem(
                            news = news,
                            onMovieClickListener = onMovieClickListener
                        )
                    }
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsVerticalItems)
                    binding.msvVerticalNews.showContent()
                    binding.rvNewsVertical.adapter = groupieAdapter
                }
            }
        }
    }

}