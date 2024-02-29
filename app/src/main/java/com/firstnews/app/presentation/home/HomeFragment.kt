package com.firstnews.app.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firstnews.app.R
import com.firstnews.app.databinding.FragmentHomeBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.ui.news.SectionNewsByComment
import com.firstnews.app.ui.news.SectionNewsCarousel
import com.firstnews.app.ui.news.SectionNewsHorizontal
import com.firstnews.app.ui.news.SectionNewsRecommendationGrid
import com.firstnews.app.ui.news.SectionNewsVertical
import com.firstnews.app.util.navigateToDetailActivity
import com.firstnews.app.util.navigateToListActivity
import com.firstnews.app.util.navigateToSearchActivity
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), OnMovieClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRvLatest()
        initTechnologyNews()
        initSportsNews()
        initBusinessNews()
        initHealthNews()
        initGeneralNews()
        initScienceNews()

        binding.rvLatest.adapter = groupieAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menu ->
            if (menu.itemId == R.id.action_search) {
                context?.navigateToSearchActivity()
            }
            true
        }
    }

    private fun initRvLatest() {
        groupieAdapter.clear()
        groupieAdapter.add(0, SectionNewsCarousel(
            fragment = this,
            owner = viewLifecycleOwner,
            headlineNews = viewModel.getHeadlineNews(),
            listener = this
        ))
    }

    private fun initTechnologyNews() {
        groupieAdapter.add(1,
            SectionNewsHorizontal(
                owner = requireActivity(),
                label = "Technology News",
                news = viewModel.getHeadlineNews(category = NewsCategory.Technology),
                onClick = ::onMovieClick,
                onSeeAllClick = { context?.navigateToListActivity(NewsCategory.Technology) }
            )
        )
    }

    private fun initSportsNews() {
        groupieAdapter.add(2,
            SectionNewsVertical(
                owner = requireActivity(),
                label = "Sports News",
                news = viewModel.getHeadlineNews(category = NewsCategory.Sports),
                onMovieClickListener = this,
                onSeeAllClick = { context?.navigateToListActivity(NewsCategory.Sports) }
            )
        )
    }

    private fun initBusinessNews() {
        groupieAdapter.add(3, SectionNewsRecommendationGrid(
            owner = viewLifecycleOwner,
            label = "Business News",
            news = viewModel.getHeadlineNews(category = NewsCategory.Business, pageSize = 4),
            onMovieClickListener = this,
            onSeeAllClick = { context?.navigateToListActivity(NewsCategory.Business) }
        ))
    }

    private fun initHealthNews() {
        groupieAdapter.add(4,
            SectionNewsVertical(
                owner = requireActivity(),
                label = "Health News",
                news = viewModel.getHeadlineNews(category = NewsCategory.Health),
                onMovieClickListener = this,
                onSeeAllClick = { context?.navigateToListActivity(NewsCategory.Health) }
            ))
    }

    private fun initGeneralNews() {
        groupieAdapter.add(5, SectionNewsByComment(
            owner = viewLifecycleOwner,
            label = "The Most Comment News",
            news = viewModel.getHeadlineNews(category = NewsCategory.General),
            onMovieClickListener = this,
            onSeeAllClick = { context?.navigateToListActivity(NewsCategory.General) }
        ))
    }

    private fun initScienceNews() {
        groupieAdapter.add(6,
            SectionNewsVertical(
                owner = requireActivity(),
                label = "Science News",
                news = viewModel.getHeadlineNews(category = NewsCategory.Science),
                onMovieClickListener = this,
                onSeeAllClick = { context?.navigateToListActivity(NewsCategory.Science) }
            )
        )
    }

    override fun onMovieClick(news: News) {
        context?.navigateToDetailActivity(news)
    }

    override fun onSaveMovieClick(news: News) {
        Toast.makeText(context, "Not yet implement!", Toast.LENGTH_SHORT).show()
    }
}