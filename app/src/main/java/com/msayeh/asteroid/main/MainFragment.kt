package com.msayeh.asteroid.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.msayeh.asteroid.domain.Asteroid
import com.msayeh.asteroid.R
import com.msayeh.asteroid.database.AsteroidsDatabase
import com.msayeh.asteroid.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val database: AsteroidsDatabase by lazy {
        AsteroidsDatabase.getInstance(requireContext())
    }

    private val viewModel: MainViewModel by lazy {
        val factory = MainViewModelFactory(database)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setupRecyclerView(binding)
        viewModel.triggerLoading()

        viewModel.navigateToDetails.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            }
            viewModel.doneNavigating()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView(binding: FragmentMainBinding) {
        val adapter = MainAdapter(AsteroidListener {
            viewModel.onAsteroidClicked(it)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                viewModel.triggerDone()
            } else if(isInternetConnected()) {
                viewModel.triggerLoading()
            } else {
                viewModel.triggerError()
            }
            adapter.submitList(it)
        }
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun insertDummyDataToAdapter(adapter: MainAdapter) {
        adapter.submitList(
            listOf(
                Asteroid(
                    2,
                    "Testing",
                    "15-5-2023",
                    16.1,
                    12.3,
                    12.2,
                    12.0,
                    false
                ),
                Asteroid(
                    2,
                    "Testing",
                    "15-1-2023",
                    16.1,
                    12.3,
                    12.2,
                    12.0,
                    false
                ),
                Asteroid(
                    2,
                    "Testng",
                    "15-5-2023",
                    16.1,
                    12.3,
                    12.2,
                    12.0,
                    false
                ),
                Asteroid(
                    3,
                    "Testing2",
                    "15-6-2023",
                    16.1,
                    15.3,
                    12.2,
                    13.0,
                    true
                ),
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu -> viewModel.updateFilter(Filter.WEEK)
            R.id.show_today_menu -> viewModel.updateFilter(Filter.DAY)
            else -> viewModel.updateFilter(Filter.SAVED)
        }
        return true
    }
}
