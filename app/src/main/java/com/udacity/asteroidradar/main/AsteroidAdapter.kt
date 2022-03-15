package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.model.Asteroid

class AsteroidAdapter(private val adapterListener: AdapterListener) :
    RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {


    private var asteroids: List<Asteroid> = emptyList()
    private lateinit var binding: ItemAsteroidBinding

    fun setAsteroids(data: List<Asteroid>) {
        this.asteroids = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        binding =
            ItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.bindAsteroid(asteroid)
    }

    override fun getItemCount() = asteroids.size


    inner class ViewHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindAsteroid(asteroid: Asteroid) {
            binding.asteroid = asteroid
        }
    }

    interface AdapterListener {
        fun myListener(asteroid: Asteroid)
    }
}

