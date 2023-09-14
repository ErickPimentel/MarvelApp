package com.erickpimentel.marvelapp.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.erickpimentel.marvelapp.R
import com.erickpimentel.marvelapp.data.network.ApiResult
import com.erickpimentel.marvelapp.databinding.FragmentHomeBinding
import com.erickpimentel.marvelapp.presentation.adapter.CarouselAdapter
import com.erickpimentel.marvelapp.presentation.adapter.CharacterAdapter
import com.erickpimentel.marvelapp.presentation.adapter.LoadMoreAdapter
import com.erickpimentel.marvelapp.presentation.ui.characterDetails.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.erickpimentel.marvelapp.util.SnackBarUtil.Companion.showSnackBar
import java.net.UnknownHostException

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val characterDetailsViewModel: CharacterDetailsViewModel by activityViewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = characterAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    characterAdapter.retry()
                }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            getFirstFiveCharacters()

            setupCharactersList()

            setupLoadStateHandling()

        }

        setOnItemClickListener()

    }

    private fun FragmentHomeBinding.getFirstFiveCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            when (val apiResult = homeViewModel.getFirstFiveCharacters()) {
                is ApiResult.Success -> {
                    val characters = apiResult.response.body()?.data?.results?.map { it.toCharacter() } ?: emptyList()
                    val carouselAdapter = CarouselAdapter(characters)
                    carouselRecyclerView.adapter = carouselAdapter
                }

                is ApiResult.Error -> {
                    requireView().showSnackBar(R.string.no_internet_connection)
                }
            }
        }
    }

    private fun setupCharactersList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                homeViewModel.charactersList.collect {
                    characterAdapter.submitData(it)
                }
            }
        }
    }

    private fun FragmentHomeBinding.setupLoadStateHandling() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                characterAdapter.loadStateFlow.collect { loadState ->
                    val state = loadState.refresh
                    progressBar.isVisible = state is LoadState.Loading

                    if (state is LoadState.Error) {
                        when (state.error) {
                            is UnknownHostException -> requireView().showSnackBar(R.string.no_internet_connection)
                        }
                    }
                }
            }
        }
    }

    private fun setOnItemClickListener() {
        characterAdapter.setOnItemClickListener { character ->
            characterDetailsViewModel.setCurrentCharacter(character)
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCharacterDetailsFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}