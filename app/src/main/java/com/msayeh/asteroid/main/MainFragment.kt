package com.msayeh.asteroid.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msayeh.asteroid.Asteroid
import com.msayeh.asteroid.R
import com.msayeh.asteroid.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainAdapter()
        binding.asteroidRecycler.adapter = adapter

        insertDummyData(adapter)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun insertDummyData(adapter: MainAdapter) {
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
        return true
    }
}
