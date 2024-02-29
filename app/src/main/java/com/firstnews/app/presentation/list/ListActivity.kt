package com.firstnews.app.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.firstnews.app.R
import com.firstnews.app.databinding.ActivityListBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.ui.adapter.NewsFooterAdapter
import com.firstnews.app.ui.adapter.NewsPagingDataAdapter
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.navigateToDetailActivity
import com.firstnews.app.util.navigateToSearchActivity
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity(), OnMovieClickListener {

    companion object {
        const val EXTRA_NEWS_CATEGORY = "extra_news_category"
    }

    private lateinit var binding: ActivityListBinding
    private lateinit var newsCategory: NewsCategory
    private lateinit var newsAdapter: NewsPagingDataAdapter

    private val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsCategory = try {
            NewsCategory.valueOf(intent.getStringExtra(EXTRA_NEWS_CATEGORY) ?: "")
        } catch (err: Exception) {
            NewsCategory.None
        }

        initToolbar()
        initRecyclerView()
        initLoadState()
        initObserver()

    }

    private fun initToolbar() {
        with(binding.toolbar) {
            title = "${newsCategory.name} News"
            setNavigationOnClickListener { finish() }
            setOnMenuItemClickListener {  menu ->
                if (menu.itemId == R.id.action_search) {
                    navigateToSearchActivity()
                }
                true
            }
        }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsPagingDataAdapter(this)
        newsAdapter.withLoadStateFooter(NewsFooterAdapter())
        binding.rvList.adapter = newsAdapter
    }

    private fun initLoadState() {
        lifecycleScope.launch {
            newsAdapter.loadStateFlow.collectLatest { loadState ->
                val isLoading = loadState.refresh is LoadState.Loading
                if (isLoading) {
                    binding.msvList.showLoading()
                    return@collectLatest
                }
                binding.msvList.showContent()
            }
        }
    }

    private fun initObserver() {
        viewModel.getHeadlineNews().observe(this) { pagingData ->
            lifecycleScope.launch {
                newsAdapter.submitData(pagingData)
            }
        }
    }

    override fun onMovieClick(news: News) {
        navigateToDetailActivity(news)
    }

    override fun onSaveMovieClick(news: News) {
        Toast.makeText(this, "Not yet implement!", Toast.LENGTH_SHORT).show()
    }


}