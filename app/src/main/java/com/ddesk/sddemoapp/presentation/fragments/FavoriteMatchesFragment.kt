package com.ddesk.sddemoapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.ddesk.sddemoapp.R
import com.ddesk.sddemoapp.databinding.FragmentFavoriteMatchesBinding
import com.ddesk.sddemoapp.presentation.MainActivity
import com.ddesk.sddemoapp.presentation.adapters.FavoriteMatchesAdapter
import com.ddesk.sddemoapp.presentation.viewmodel.MatchesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Collections


class FavoriteMatchesFragment : Fragment() {

    private lateinit var viewModel: MatchesViewModel
    private lateinit var fragmentFavoriteMatchesBinding: FragmentFavoriteMatchesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentFavoriteMatchesBinding = FragmentFavoriteMatchesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        viewModel.getFavoriteMatches()
        lifecycleScope.launch {
            viewModel.favoriteMatches.collectLatest {
                val adapter = FavoriteMatchesAdapter(it?: Collections.emptyList())
                fragmentFavoriteMatchesBinding.rvMatch.adapter = adapter

                adapter.setOnDeleteClickListener { user ->
                    viewModel.deleteFavoriteMatch(user)
                }
            }
        }

    }

}