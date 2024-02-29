package com.firstnews.app.util

import android.content.Context
import android.content.Intent
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.presentation.detail.DetailActivity
import com.firstnews.app.presentation.list.ListActivity
import com.firstnews.app.presentation.search.SearchActivity

fun Context.navigateToDetailActivity(news: News) {
    val toDetailIntent = Intent(this, DetailActivity::class.java)
    toDetailIntent.putExtra(DetailActivity.EXTRA_NEWS, news)
    startActivity(toDetailIntent)
}

fun Context.navigateToListActivity(newsCategory: NewsCategory) {
    val toListIntent = Intent(this, ListActivity::class.java)
    toListIntent.putExtra(ListActivity.EXTRA_NEWS_CATEGORY, newsCategory.name)
    startActivity(toListIntent)
}

fun Context.navigateToSearchActivity() {
    val toSearchIntent = Intent(this, SearchActivity::class.java)
    startActivity(toSearchIntent)
}
