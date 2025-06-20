package com.ddesk.sddemoapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ddesk.sddemoapp.R
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

        viewModel.getNewMatches()
        lifecycleScope.launch {
            viewModel.newMatches.collectLatest {
                val adapter = ItemAdapter(it.data?:Collections.emptyList())
                adapter.setOnCancelClickListener { user ->
                    viewModel.removeUserFromList(user)
                }
                adapter.setOnSelectClickListener { user ->
                    viewModel.saveUserToDb(user)
                    Toast.makeText(requireActivity(),"User Added to Favorite", Toast.LENGTH_LONG).show()
                }

                fragmentNewMatchesBinding.rvMatch.adapter = adapter
            }
        }

        fragmentNewMatchesBinding.ivFavoriteMatches.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FavoriteMatchesFragment())
                .addToBackStack(null)
                .commit()
        }

    }

}