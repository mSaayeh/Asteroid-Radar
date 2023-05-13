package com.msayeh.asteroid.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.msayeh.asteroid.Asteroid
import com.msayeh.asteroid.databinding.AsteroidItemBinding

class MainAdapter : ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder =
        AsteroidViewHolder.from(parent)

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class AsteroidItemCallback : ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
        oldItem == newItem

}

class AsteroidViewHolder private constructor(private val binding: AsteroidItemBinding) :
    ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }

    fun bind(asteroid: Asteroid) {
        binding.asteroid = asteroid
        binding.executePendingBindings()
    }
}