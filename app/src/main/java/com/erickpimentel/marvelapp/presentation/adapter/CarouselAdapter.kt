package com.erickpimentel.marvelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erickpimentel.marvelapp.databinding.CarouselItemViewBinding
import com.erickpimentel.marvelapp.domain.model.Character

class CarouselAdapter (private val charactersList: List<Character>) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>(){

    inner class CarouselViewHolder(private val binding: CarouselItemViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character){
            binding.apply {
                characterName.text = character.name
                val imageUrl = "${character.thumbnail}/portrait_xlarge.${character.thumbnailExtension}"
                Glide.with(binding.root.context).load(imageUrl).into(reflectionImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselAdapter.CarouselViewHolder {
        return CarouselViewHolder(CarouselItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CarouselAdapter.CarouselViewHolder, position: Int) {
        val currentCharacter = charactersList[position]
        holder.bind(currentCharacter)
    }

    override fun getItemCount(): Int = charactersList.size

}