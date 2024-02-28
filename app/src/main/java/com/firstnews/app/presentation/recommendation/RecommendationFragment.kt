package com.firstnews.app.presentation.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firstnews.app.databinding.FragmentRecommendationBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.ui.news.SectionNewsByComment
import com.firstnews.app.ui.news.SectionNewsRecommendationGrid
import com.firstnews.app.ui.news.SectionNewsRecommendationVertical
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendationFragment : Fragment(), OnMovieClickListener {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding: FragmentRecommendationBinding get() = _binding!!

    private val viewModel: RecommendationViewModel by viewModel()

    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBusinessRecommendation()
        initEntertainmentRecommendation()
        initSportRecommendation()
        initGeneralRecommendation()
        initHealthRecommendation()
        binding.rvRecommendation.adapter = groupieAdapter
    }

    private fun initBusinessRecommendation() {
        groupieAdapter.clear()
        groupieAdapter.add(0, SectionNewsRecommendationVertical(
            owner = viewLifecycleOwner,
            label = "Business Recommendation",
            news = viewModel.getBusinessRecommendation(),
            onMovieClickListener = this
        )
        )
    }

    private fun initEntertainmentRecommendation() {
        groupieAdapter.add(1, SectionNewsRecommendationGrid(
            owner = viewLifecycleOwner,
            label = "Entertainment Recommendation",
            news = viewModel.getTechnologyRecommendation(),
            onClick = ::onMovieClick
        )
        )
    }

    private fun initSportRecommendation() {
        groupieAdapter.add(2, SectionNewsRecommendationVertical(
            owner = viewLifecycleOwner,
            label = "Sport Recommendation",
            news = viewModel.getSportRecommendation(),
            onMovieClickListener = this
        ))
    }

    private fun initGeneralRecommendation() {
        groupieAdapter.add(3, SectionNewsByComment(
            owner = viewLifecycleOwner,
            label = "The Most Comment News",
            news = viewModel.getGeneralRecommendation(),
            onMovieClickListener = this
        )
        )
    }

    private fun initHealthRecommendation() {
        groupieAdapter.add(4, SectionNewsRecommendationVertical(
            owner = viewLifecycleOwner,
            label = "Health Recommendation",
            news = viewModel.getHealthRecommendation(),
            onMovieClickListener = this
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(news: News) {

    }

    override fun onSaveMovieClick(news: News) {
        Toast.makeText(context, "Not yet implement!", Toast.LENGTH_SHORT).show()
    }

}