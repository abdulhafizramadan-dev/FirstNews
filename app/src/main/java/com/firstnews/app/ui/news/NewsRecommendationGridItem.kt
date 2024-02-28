package com.firstnews.app.ui.news

import android.view.View
import coil.load
import com.firstnews.app.R
import com.firstnews.app.databinding.ItemNewsRecommendationGridBinding
import com.firstnews.app.domain.model.News
import com.xwray.groupie.viewbinding.BindableItem

class NewsRecommendationGridItem(
    private val news: News,
    private val onClick: (News) -> Unit
) : BindableItem<ItemNewsRecommendationGridBinding>() {

    override fun getLayout(): Int = R.layout.item_news_recommendation_grid

    override fun initializeViewBinding(view: View): ItemNewsRecommendationGridBinding = ItemNewsRecommendationGridBinding.bind(view)

    override fun bind(viewBinding: ItemNewsRecommendationGridBinding, position: Int) {
        with(viewBinding) {
            tvTitle.text = news.title
            ivPhoto.load(news.urlToImage) {
                placeholder(R.drawable.img_placeholder_small)
                error(R.drawable.img_placeholder_small)
                crossfade(true)
            }
            root.setOnClickListener {
                onClick(news)
            }
        }
    }
}