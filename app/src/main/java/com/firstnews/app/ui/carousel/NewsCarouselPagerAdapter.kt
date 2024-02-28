package com.ahr.detikcom.ui.carousel

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.firstnews.app.ui.carousel.NewsCarouselFragment

class NewsCarouselPagerAdapter(fm: Fragment, private val fragments: List<NewsCarouselFragment>) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}