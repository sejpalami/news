package com.example.news.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.news.R
import com.example.news.databinding.FragmentArticleBinding
import com.example.news.ui.NewsActivity
import com.example.news.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleBinding.bind(view)

        viewModel = (activity as NewsActivity).viewModel

        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.typeAttributes?.url?.let { loadUrl(it) }
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}