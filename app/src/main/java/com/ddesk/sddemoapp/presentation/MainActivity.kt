package com.ddesk.sddemoapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ddesk.sddemoapp.R
import com.ddesk.sddemoapp.databinding.ActivityMainBinding
import com.ddesk.sddemoapp.presentation.fragments.FavoriteMatchesFragment
import com.ddesk.sddemoapp.presentation.fragments.NewMatchesFragment
import com.ddesk.sddemoapp.presentation.utils.isNetworkAvailable
import com.ddesk.sddemoapp.presentation.viewmodel.MatchesViewModel
import com.ddesk.sddemoapp.presentation.viewmodel.MatchesViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    @Inject
    lateinit var factory: MatchesViewModelFactory
    lateinit var viewModel: MatchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,factory)[MatchesViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                    if(isNetworkAvailable(this@MainActivity)) NewMatchesFragment()
                    else FavoriteMatchesFragment()
                )
                .commit()
        }

    }
}