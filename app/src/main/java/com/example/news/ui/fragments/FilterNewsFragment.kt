package com.example.news.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentFilterNewsBinding
import com.example.news.databinding.ItemErrorMessageBinding
import com.example.news.models.NewsResponseItem
import com.example.news.ui.NewsActivity
import com.example.news.ui.NewsViewModel
import com.example.news.util.Resource


class FilterNewsFragment : Fragment(R.layout.fragment_filter_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsTypeList: ArrayList<String>
    val TAG = "FilterNewsFragment"
    var selectedFilter = "All"
    private lateinit var binding: FragmentFilterNewsBinding
    private lateinit var errorMessageBinding: ItemErrorMessageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilterNewsBinding.bind(view)
        errorMessageBinding = ItemErrorMessageBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        newsTypeList = ArrayList<String>()
        newsTypeList.add("All")
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        binding.spType.adapter = ArrayAdapter(
            activity as NewsActivity,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            newsTypeList
        ) as SpinnerAdapter
        binding.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Empty Block
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                selectedFilter = type
                viewModel.filterNews("news")
            }

        }

        viewModel.filterNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { newsResponse ->
                        val originalList = newsResponse.toList()
                        addDataToSpinner(originalList)
                        when (selectedFilter) {
                            "All" -> newsAdapter.differ.submitList(originalList)
                            else -> newsAdapter.differ.submitList(originalList.filter {
                                it.type.equals(selectedFilter)
                            })
                        }

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        errorMessageBinding.btnRetry.setOnClickListener {
            viewModel.filterNews("news")
        }

    }

    private fun addDataToSpinner(responseList: List<NewsResponseItem>) {
        val allTypes = ArrayList<String>()
        for ((i, item) in responseList.withIndex()) {
            if (!allTypes.contains(responseList[i].type)) {
                responseList[i].type?.let { allTypes.add(it) }
            }
        }
        newsTypeList.clear()
        newsTypeList.add("All")
        newsTypeList.addAll(allTypes)
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        binding.rvFilterNews.visibility = View.VISIBLE
        binding.llErrorView.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        binding.rvFilterNews.visibility = View.INVISIBLE
        binding.llErrorView.visibility = View.VISIBLE
        errorMessageBinding.tvErrorMessage.text = message
        isError = true
    }

    var isError = false
    var isLoading = false


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvFilterNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}