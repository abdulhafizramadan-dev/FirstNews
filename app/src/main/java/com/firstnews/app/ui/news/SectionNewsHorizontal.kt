package com.firstnews.app.ui.news

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.firstnews.app.R
import com.firstnews.app.databinding.SectionNewsHorizontalBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class SectionNewsHorizontal(
    private val owner: LifecycleOwner,
    private val label: String,
    private val onSeeAllClick: () -> Unit,
    private val news: LiveData<Resource<List<News>>>,
    private val onClick: (News) -> Unit
) : BindableItem<SectionNewsHorizontalBinding>() {

    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun getLayout(): Int = R.layout.section_news_horizontal

    override fun initializeViewBinding(view: View): SectionNewsHorizontalBinding = SectionNewsHorizontalBinding.bind(view)

    override fun bind(viewBinding: SectionNewsHorizontalBinding, position: Int) {
        initObserver(viewBinding)
    }

    private fun initObserver(binding: SectionNewsHorizontalBinding) {
        val context = binding.root.context
        news.observe(owner) { resource ->
            when(resource) {
                Resource.Loading -> binding.msvHorizontalNews.showLoading()
                is Resource.Success -> {
                    val newsCarouselItems = resource.data.map { news ->
                        NewsHorizontalItem(news = news, onClick = onClick)
                    }
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsCarouselItems)
                    with(binding) {
                        tvLabel.text = label
                        btnSeeAll.setOnClickListener { onSeeAllClick() }
                        rvNewsHorizontal.adapter = groupieAdapter
                        msvHorizontalNews.showContent()
                    }
                }
                is Resource.Error -> {
                    val newsCarouselItems = resource.data?.map { news ->
                        NewsHorizontalItem(news = news, onClick = onClick)
                    } ?: emptyList()
                    groupieAdapter.clear()
                    groupieAdapter.addAll(newsCarouselItems)
                    with(binding) {
                        tvLabel.text = label
                        btnSeeAll.setOnClickListener { onSeeAllClick() }
                        rvNewsHorizontal.adapter = groupieAdapter
                        msvHorizontalNews.showContent()
                    }
                    Toast.makeText(context, resource.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}