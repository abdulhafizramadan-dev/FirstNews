package com.firstnews.app.ui.news

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.firstnews.app.R
import com.firstnews.app.databinding.SectionNewsRecommendationGridBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.ui.adapter.NewsRecommendationAdapter
import com.firstnews.app.ui.decoration.GridSpacingItemDecoration
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import com.xwray.groupie.viewbinding.BindableItem

class SectionNewsRecommendationGrid(
    private val owner: LifecycleOwner,
    private val label: String,
    private val onSeeAllClick: () -> Unit,
    private val news: LiveData<Resource<List<News>>>,
    private val onMovieClickListener: OnMovieClickListener
) : BindableItem<SectionNewsRecommendationGridBinding>() {

    private lateinit var newsRecommendationAdapter: NewsRecommendationAdapter

    override fun getLayout(): Int = R.layout.section_news_recommendation_grid

    override fun initializeViewBinding(view: View): SectionNewsRecommendationGridBinding = SectionNewsRecommendationGridBinding.bind(view)

    override fun bind(viewBinding: SectionNewsRecommendationGridBinding, position: Int) {
        initRecyclerView(viewBinding)
        initObserver(viewBinding)
    }

    private fun initRecyclerView(binding: SectionNewsRecommendationGridBinding) {
        newsRecommendationAdapter = NewsRecommendationAdapter(onMovieClickListener)
        val context = binding.root.context
        val spacingInPixel = context.resources.getDimensionPixelSize(R.dimen._16dp)
        val gridLayoutManager = GridLayoutManager(binding.root.context, 2)
        val decoration = GridSpacingItemDecoration(2, spacingInPixel, false, 0)
        with(binding.rvNewsRecommendationGrid) {
            layoutManager = gridLayoutManager
            addItemDecoration(decoration)
            adapter = newsRecommendationAdapter
        }
    }

    private fun initObserver(binding: SectionNewsRecommendationGridBinding) {
        val context = binding.root.context
        news.observe(owner) { resource ->
            when(resource) {
                Resource.Loading -> binding.msvNewsRecommendationGrid.showLoading()
                is Resource.Success -> {
                    newsRecommendationAdapter.submitList(resource.data)
                    with(binding) {
                        msvNewsRecommendationGrid.showContent()
                        tvLabel.text = label
                        btnSeeAll.setOnClickListener { onSeeAllClick() }
                        rvNewsRecommendationGrid.adapter = newsRecommendationAdapter
                    }
                }
                is Resource.Error -> {
                    newsRecommendationAdapter.submitList(resource.data)
                    with(binding) {
                        msvNewsRecommendationGrid.showContent()
                        tvLabel.text = label
                        btnSeeAll.setOnClickListener { onSeeAllClick() }
                        rvNewsRecommendationGrid.adapter = newsRecommendationAdapter
                    }
                    Toast.makeText(context, resource.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}