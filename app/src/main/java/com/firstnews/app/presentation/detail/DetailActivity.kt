package com.firstnews.app.presentation.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firstnews.app.R
import com.firstnews.app.databinding.ActivityDetailBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.util.shareNews

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var news: News

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        news = intent.getParcelableExtra(EXTRA_NEWS) ?: News()

        initToolbar()
        initWebView()

    }

    private fun initToolbar() {
        with(binding.toolbar) {
            title = news.title
            setNavigationOnClickListener { finish() }
            setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.action_save -> {}
                    R.id.action_share -> shareNews(news)
                }
                true
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(binding.webView) {
            loadUrl(news?.url ?: "")
            settings.javaScriptEnabled = true
        }
    }
}