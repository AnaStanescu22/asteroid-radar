package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment(), AsteroidAdapter.AdapterListener {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var asteroidAdapter: AsteroidAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        asteroidAdapter = AsteroidAdapter(this)
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.asteroidRecycler.adapter = asteroidAdapter

        viewModel.state.onEach { asteroidState ->
            asteroidAdapter.setAsteroids(asteroidState.asteroids)
        }.launchIn(lifecycleScope)

        viewModel.loadingState.onEach { isLoading ->
            binding.statusLoadingWheel.isVisible = isLoading
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun myListener(asteroid: Asteroid) {
        TODO("Not yet implemented")
    }
}
