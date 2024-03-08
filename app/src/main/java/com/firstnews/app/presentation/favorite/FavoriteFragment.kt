package com.firstnews.app.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.firstnews.app.databinding.FragmentFavoriteBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.adapter.NewsFooterAdapter
import com.firstnews.app.ui.adapter.NewsPagingDataAdapter
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.navigateToDetailActivity
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showEmpty
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), OnMovieClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!

    private lateinit var newsAdapter: NewsPagingDataAdapter

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        if (viewModel.isLoad.value == false) {
            initFavoriteNews()
            initObserver()
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsPagingDataAdapter(this)
        newsAdapter.withLoadStateFooter(
            NewsFooterAdapter()
        )
        binding.rvFavorite.adapter = newsAdapter
    }

    private fun initFavoriteNews() {
        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.setIsLoad(true)
                newsAdapter.submitData(it)
            }
        }
    }

    private fun initObserver() {
        newsAdapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached ) {
                if (newsAdapter.itemCount < 1) {
                    binding.msvFavorite.showEmpty()
                } else {
                    binding.msvFavorite.showContent()
                }
            }
        }
    }

    override fun onMovieClick(news: News) {
        context?.navigateToDetailActivity(news)
    }

    override fun onSaveMovieClick(news: News) {
    }

}