package com.firstnews.app.ui.news

import android.view.View
import com.firstnews.app.R
import com.firstnews.app.databinding.ItemNewsByCommentBinding
import com.firstnews.app.domain.model.News
import com.xwray.groupie.viewbinding.BindableItem

class NewsByCommentItem(
    private val news: News,
    private val onClick: (News) -> Unit
) : BindableItem<ItemNewsByCommentBinding>() {

    override fun getLayout(): Int = R.layout.item_news_by_comment

    override fun initializeViewBinding(view: View): ItemNewsByCommentBinding = ItemNewsByCommentBinding.bind(view)

    override fun bind(viewBinding: ItemNewsByCommentBinding, position: Int) {
        with(viewBinding) {
            val randomComment = (10..300).random()
            tvTitle.text = news.title
            tvCommentCount.text = randomComment.toString()
            root.setOnClickListener {
                onClick(news)
            }
        }
    }
}