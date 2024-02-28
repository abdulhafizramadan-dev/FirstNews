package com.firstnews.app.ui.news

import android.view.View
import coil.load
import com.firstnews.app.R
import com.firstnews.app.databinding.ItemNewsVerticalBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.toRelativeDateFormat
import com.xwray.groupie.viewbinding.BindableItem

class NewsVerticalItem(
    private val news: News,
    private val onMovieClickListener: OnMovieClickListener
) : BindableItem<ItemNewsVerticalBinding>() {

    override fun getLayout(): Int = R.layout.item_news_vertical

    override fun initializeViewBinding(view: View): ItemNewsVerticalBinding = ItemNewsVerticalBinding.bind(view)

    override fun bind(viewBinding: ItemNewsVerticalBinding, position: Int) {
        with(viewBinding) {
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