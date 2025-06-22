package com.ddesk.sddemoapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddesk.sddemoapp.R
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.data.util.Resource
import com.ddesk.sddemoapp.databinding.FragmentNewMatchesBinding
import com.ddesk.sddemoapp.presentation.MainActivity
import com.ddesk.sddemoapp.presentation.adapters.ItemAdapter
import com.ddesk.sddemoapp.presentation.viewmodel.MatchesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Collections


class NewMatchesFragment : Fragment() {

    private lateinit var viewModel: MatchesViewModel
    private lateinit var fragmentNewMatchesBinding : FragmentNewMatchesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentNewMatchesBinding = FragmentNewMatchesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        setupUI()
        observeViewModel()
        loadData()

        fragmentNewMatchesBinding.ivFavoriteMatches.setOnClickListener {
            navigateToFavorites()
        }
    }

    private fun setupUI() {
        // Initialize views for different states
        fragmentNewMatchesBinding.apply {
            rvMatch.layoutManager = LinearLayoutManager(requireContext())
            retryButton.setOnClickListener { loadData() }
            swipeRefreshLayout.setOnRefreshListener { loadData() }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.newMatches.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> showLoadingState(resource.data)
                    is Resource.Success -> showSuccessState(resource.data)
                    is Resource.Error -> showErrorState(resource.message, resource.data)
                }
                fragmentNewMatchesBinding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun loadData() {
        viewModel.getNewMatches()
    }

    private fun showLoadingState(partialData: List<UserEntity>?) {
        fragmentNewMatchesBinding.apply {
            if (partialData != null) {
                // Show partial data while loading more
                setupRecyclerView(partialData)
                progressBar.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                emptyView.visibility = View.GONE
            } else {
                // Initial load
                rvMatch.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                emptyView.visibility = View.GONE
            }
        }
    }

    private fun showSuccessState(data: List<UserEntity>?) {
        fragmentNewMatchesBinding.apply {
            if (data.isNullOrEmpty()) {
                showEmptyState()
            } else {
                setupRecyclerView(data)
                rvMatch.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                errorView.visibility = View.GONE
                emptyView.visibility = View.GONE
            }
        }
    }

    private fun showErrorState(message: String?, partialData: List<UserEntity>?) {
        fragmentNewMatchesBinding.apply {
            if (partialData != null) {
                // Show partial data with error message
                setupRecyclerView(partialData)
                errorView.visibility = View.VISIBLE
                errorTextView.text = message ?: "Error loading new data"
                progressBar.visibility = View.GONE
                emptyView.visibility = View.GONE
            } else {
                // No data available, show full error state
                rvMatch.visibility = View.GONE
                progressBar.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                errorTextView.text = message ?: "Error loading data"
                emptyView.visibility = View.GONE
            }
        }
    }

    private fun showEmptyState() {
        fragmentNewMatchesBinding.apply {
            rvMatch.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(data: List<UserEntity>) {
        val adapter = ItemAdapter(data)
        adapter.setOnCancelClickListener { user ->
            viewModel.removeUserFromList(user)
        }
        adapter.setOnSelectClickListener { user ->
            viewModel.saveUserToDb(user)
            Toast.makeText(requireActivity(), "User Added to Favorite", Toast.LENGTH_LONG).show()
        }
        fragmentNewMatchesBinding.rvMatch.adapter = adapter
    }

    private fun navigateToFavorites() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FavoriteMatchesFragment())
            .addToBackStack(null)
            .commit()
    }
}