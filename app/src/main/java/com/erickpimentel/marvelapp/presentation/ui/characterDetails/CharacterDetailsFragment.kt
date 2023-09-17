package com.erickpimentel.marvelapp.presentation.ui.characterDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.erickpimentel.marvelapp.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding

    private val characterDetailsViewModel: CharacterDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        binding?.apply {
            characterDetailsViewModel.currentCharacter.value?.let { character ->
                characterName.text = character.name
                characterDescription.text = character.description

                val imageUrl = "${character.thumbnail}/portrait_xlarge.${character.thumbnailExtension}"
                Glide.with(root.context).load(imageUrl).into(characterImage)
                Glide.with(root.context).load(imageUrl).into(characterBackgroundImage)
            }
        }

        return binding?.root
    }
}