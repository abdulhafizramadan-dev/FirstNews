package com.firstnews.app.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.firstnews.app.databinding.ActivitySearchBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.adapter.NewsFooterAdapter
import com.firstnews.app.ui.adapter.NewsPagingDataAdapter
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(), OnMovieClickListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var imm: InputMethodManager
    private lateinit var newsAdapter: NewsPagingDataAdapter

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.tilSearch.editText?.requestFocus()

        initToolbar()
        initAdapter()
        initSearchBar()
        initLoadState()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initSearchBar() {
        binding.tilSearch.editText?.setOnEditorActionListener { editText, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                val query = binding.tilSearch.editText?.text.toString()
                viewModel.searchNews(query).observe(this) { pagingData ->
                    lifecycleScope.launch {
                        newsAdapter.submitData(pagingData)
                    }
                }
            }
            true
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsPagingDataAdapter(this)
        newsAdapter.withLoadStateFooter(
            NewsFooterAdapter()
        )
        binding.rvNews.adapter = newsAdapter
    }

    private fun initLoadState() {
        lifecycleScope.launch {
            newsAdapter.loadStateFlow.collectLatest { loadState ->
                val isLoading = loadState.refresh is LoadState.Loading
                if (isLoading) {
                    binding.msvSearch.showLoading()
                    return@collectLatest
                }
                binding.msvSearch.showContent()
            }
        }
    }

    override fun onMovieClick(news: News) {
    }

    override fun onSaveMovieClick(news: News) {
        Toast.makeText(this, "Not yet implement!", Toast.LENGTH_SHORT).show()
    }
}