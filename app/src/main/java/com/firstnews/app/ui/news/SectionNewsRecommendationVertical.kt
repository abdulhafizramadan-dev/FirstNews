package com.firstnews.app.ui.news

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.firstnews.app.R
import com.firstnews.app.databinding.SectionNewsRecommendationVerticalBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class SectionNewsRecommendationVertical(
    private val owner: LifecycleOwner,
    private val label: String,
    private val news: LiveData<Resource<List<News>>>,
    private val onMovieClickListener: OnMovieClickListener
) : BindableItem<SectionNewsRecommendationVerticalBinding>() {

    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun getLayout(): Int = R.layout.section_news_recommendation_vertical

    override fun initializeViewBinding(view: View): SectionNewsRecommendationVerticalBinding = SectionNewsRecommendationVerticalBinding.bind(view)

    override fun bind(viewBinding: SectionNewsRecommendationVerticalBinding, position: Int) {
        initObserver(viewBinding)
    }

    private fun initObserver(binding: SectionNewsRecommendationVerticalBinding) {
        val context = binding.root.context
        news.observe(owner) { resource ->
            when (resource) {
                Resource.Loading -> binding.msvNewsRecommendationVertical.showLoading()
                is Resource.Error -> {
                    val newsVerticalItems = resource.data?.map { news ->
                        NewsRecommendationVerticalItem(
                            news = news,
                            onMovieClickListener = onMovieClickListener
                        )
                    } ?: emptyList()
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsVerticalItems)
                    binding.msvNewsRecommendationVertical.showContent()
                    binding.tvLabel.text = label
                    binding.rvNewsRecommendationVertical.adapter = groupieAdapter
                    Toast.makeText(context, resource.error.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val newsVerticalItems = resource.data.map { news ->
                        NewsRecommendationVerticalItem(
                            news = news,
                            onMovieClickListener = onMovieClickListener
                        )
                    }
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsVerticalItems)
                    binding.msvNewsRecommendationVertical.showContent()
                    binding.tvLabel.text = label
                    binding.rvNewsRecommendationVertical.adapter = groupieAdapter
                }
            }
        }
    }

}