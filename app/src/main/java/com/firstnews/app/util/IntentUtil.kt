package com.firstnews.app.util

import android.content.Context
import android.content.Intent
import com.firstnews.app.R
import com.firstnews.app.domain.model.News

fun Context.shareNews(news: News) {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type="text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, news.url);
    startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
}
