package com.firstnews.app.ui.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.firstnews.app.R
import com.firstnews.app.databinding.FragmentNewsCarouselBinding
import com.firstnews.app.domain.model.News
import com.firstnews.app.ui.listener.OnMovieClickListener
import com.firstnews.app.util.toRelativeDateFormat

class NewsCarouselFragment : Fragment() {

    companion object {
        const val EXTRA_NEWS = "extra_news"
        fun getInstance(news: News, listener: OnMovieClickListener) =
            NewsCarouselFragment().apply {
                arguments = bundleOf(
                    EXTRA_NEWS to news
                )
                setCarouselClickListener(listener)
            }
    }

    private var _binding: FragmentNewsCarouselBinding? = null
    private val binding: FragmentNewsCarouselBinding get() = _binding!!

    private var onClickListener: OnMovieClickListener? = null

    private var news: News? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsCarouselBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news = arguments?.getParcelable(EXTRA_NEWS)

        initUi()

    }

    private fun initUi() {
        with(binding) {
            tvTitle.text = news?.title
            tvFooter.text = root.context.getString(R.string.format_footer, news?.source, news?.publishedAt?.toRelativeDateFormat())
            ivPhoto.load(news?.urlToImage) {
                placeholder(R.drawable.img_placeholder_large)
                error(R.drawable.img_placeholder_large)
                crossfade(true)
            }

            if (news == null) return@with

            cvCarousel.setOnClickListener {
                onClickListener?.onMovieClick(news!!)
            }
            btnSave.setOnClickListener {
                onClickListener?.onSaveMovieClick(news!!)
            }
        }
    }

    fun setCarouselClickListener(listener: OnMovieClickListener) {
        onClickListener = listener
    }

}