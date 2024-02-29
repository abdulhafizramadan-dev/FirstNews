package com.firstnews.app.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.firstnews.app.R
import com.firstnews.app.databinding.FragmentFavoriteBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.adapter.NewsFooterAdapter
import com.firstnews.app.ui.adapter.NewsPagingDataAdapter
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.navigateToDetailActivity
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
                newsAdapter.submitData(it)
            }
        }
    }

    override fun onMovieClick(news: News) {
        context?.navigateToDetailActivity(news)
    }

    override fun onSaveMovieClick(news: News) {
        TODO("Not yet implemented")
    }

}