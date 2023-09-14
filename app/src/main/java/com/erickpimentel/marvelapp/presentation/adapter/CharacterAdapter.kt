package com.erickpimentel.marvelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erickpimentel.marvelapp.databinding.CharacterViewBinding
import com.erickpimentel.marvelapp.domain.model.Character
import javax.inject.Inject


class CharacterAdapter @Inject constructor(): PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(differCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.CharacterViewHolder {
        return CharacterViewHolder(CharacterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CharacterAdapter.CharacterViewHolder, position: Int) {
        val currentCharacter = getItem(position)

        if (currentCharacter != null){
            holder.bind(currentCharacter)
        }
    }

    inner class CharacterViewHolder(private val binding: CharacterViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character){
            binding.apply {
                characterName.text = character.name

                val imageUrl = "${character.thumbnail}/portrait_xlarge.${character.thumbnailExtension}"
                Glide.with(binding.root.context).load(imageUrl).into(characterImageView)

                characterDescription.text = character.description

                root.setOnClickListener {
                    onItemClickListener?.invoke(character)
                }
            }
        }
    }

    private var onItemClickListener: ((Character) -> Unit)? = null
    fun setOnItemClickListener(listener: (Character) -> Unit){
        onItemClickListener = listener
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Character>(){
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}