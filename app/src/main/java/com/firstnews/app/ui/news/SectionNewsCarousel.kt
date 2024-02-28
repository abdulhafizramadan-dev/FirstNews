package com.firstnews.app.ui.news

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.ahr.detikcom.ui.carousel.NewsCarouselPagerAdapter
import com.firstnews.app.R
import com.firstnews.app.databinding.SectionNewsCarouselBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.ui.carousel.NewsCarouselFragment
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.showContent
import com.firstnews.app.util.showLoading
import com.xwray.groupie.viewbinding.BindableItem

class SectionNewsCarousel(
    private val fragment: Fragment,
    private val owner: LifecycleOwner,
    private val headlineNews: LiveData<Resource<List<News>>>,
    private val listener: OnMovieClickListener
) : BindableItem<SectionNewsCarouselBinding>() {

    private lateinit var viewPagerAdapter: NewsCarouselPagerAdapter

    override fun getLayout(): Int = R.layout.section_news_carousel

    override fun initializeViewBinding(view: View): SectionNewsCarouselBinding = SectionNewsCarouselBinding.bind(view)

    override fun bind(viewBinding: SectionNewsCarouselBinding, position: Int) {
        initObserver(viewBinding)
    }

    private fun initObserver(binding: SectionNewsCarouselBinding) {
        headlineNews.observe(owner) { resource ->
            when(resource) {
                Resource.Loading -> binding.msvCarousel.showLoading()
                is Resource.Success -> {
                    val newsCarouselFragments = resource.data.map {
                        NewsCarouselFragment.getInstance(it, listener)
                    }
                    binding.msvCarousel.showContent()
                    viewPagerAdapter = NewsCarouselPagerAdapter(fragment, newsCarouselFragments)
                    binding.viewpagerCarousel.adapter = viewPagerAdapter
                }
                is Resource.Error -> {
                    val newsCarouselFragments = resource.data?.map {
                        NewsCarouselFragment.getInstance(it, listener)
                    } ?: emptyList()
                    binding.msvCarousel.showContent()
                    viewPagerAdapter = NewsCarouselPagerAdapter(fragment, newsCarouselFragments)
                    binding.viewpagerCarousel.adapter = viewPagerAdapter
                    val context = binding.root.context
                    Toast.makeText(context, resource.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}