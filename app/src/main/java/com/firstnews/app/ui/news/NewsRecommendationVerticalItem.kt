package com.firstnews.app.ui.news

import android.view.View
import coil.load
import com.firstnews.app.R
import com.firstnews.app.databinding.ItemNewsRecommendationVerticalBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.xwray.groupie.viewbinding.BindableItem

class NewsRecommendationVerticalItem(
    private val news: News,
    private val onMovieClickListener: OnMovieClickListener
) : BindableItem<ItemNewsRecommendationVerticalBinding>() {

    override fun getLayout(): Int = R.layout.item_news_recommendation_vertical

    override fun initializeViewBinding(view: View): ItemNewsRecommendationVerticalBinding = ItemNewsRecommendationVerticalBinding.bind(view)

    override fun bind(viewBinding: ItemNewsRecommendationVerticalBinding, position: Int) {
        with(viewBinding) {
            tvTitle.text = news.title
            tvFooter.text = news.source
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