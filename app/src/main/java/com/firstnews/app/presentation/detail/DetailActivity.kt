package com.firstnews.app.presentation.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.content.res.AppCompatResources
import com.firstnews.app.R
import com.firstnews.app.databinding.ActivityDetailBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.util.shareNews
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var news: News

    private val viewModel: DetailViewModel by viewModel()
    private var isFavorite = false

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        news = intent.getParcelableExtra(EXTRA_NEWS) ?: News()

        initToolbar()
        initObserver()
        initWebView()
        initAction()

    }

    private fun initAction() {
        binding.fabSave.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteSavedNews(news)
            } else {
                viewModel.insertSavedNews(news)
            }
        }
    }

    private fun initObserver() {
        viewModel.isNewsSaved(news.url).observe(this) { isSaved ->
            isFavorite = isSaved
            val iconRes = if (isSaved) R.drawable.ic_favorited else R.drawable.ic_favorite
            binding.fabSave.setImageResource(iconRes)
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            title = news.title
            setNavigationOnClickListener { finish() }
            setOnMenuItemClickListener { menu ->
                if (menu.itemId == R.id.action_share) {
                     shareNews(news)
                }
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(binding.webView) {
            loadUrl(news.url)
            settings.javaScriptEnabled = true
        }
    }
}