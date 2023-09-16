package com.erickpimentel.marvelapp.presentation.ui.search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.erickpimentel.marvelapp.R
import com.erickpimentel.marvelapp.databinding.FragmentSearchBinding
import com.erickpimentel.marvelapp.presentation.ui.adapter.CharacterAdapter
import com.erickpimentel.marvelapp.presentation.ui.adapter.LoadMoreAdapter
import com.erickpimentel.marvelapp.presentation.ui.characterDetails.CharacterDetailsViewModel
import com.erickpimentel.marvelapp.util.SnackBarUtil.Companion.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val characterDetailsViewModel: CharacterDetailsViewModel by activityViewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupRecyclerView()

        val cursorAdapter = setCursorAdapter()

        setOnQueryTextListener(cursorAdapter, searchViewModel.suggestionsList)

        setOnSuggestionListener()

        setupCharactersList()

        setupLoadStateHandling()

        setOnItemClickListener()

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

    private fun setupLoadStateHandling() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                characterAdapter.loadStateFlow.collectLatest { loadState ->
                    val state = loadState.refresh
                    binding.recyclerView.isVisible =
                        characterAdapter.itemCount > 0 && state !is LoadState.Error
                    binding.noResults.isVisible =
                        (characterAdapter.itemCount == 0 || state is LoadState.Error) && !searchViewModel.currentQuery.value.isNullOrEmpty()

                    if (state is LoadState.Error) {
                        when (state.error) {
                            is UnknownHostException -> {
                                requireView().showSnackBar(R.string.no_internet_connection)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupCharactersList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                searchViewModel.charactersListSearch.collect {
                    if (!searchViewModel.currentQuery.value.isNullOrEmpty()) characterAdapter.submitData(it)
                }
            }
        }
    }

    private fun setOnItemClickListener() {
        characterAdapter.setOnItemClickListener { character ->
            searchViewModel.addSuggestion(binding.searchView.query.toString())
            characterDetailsViewModel.setCurrentCharacter(character)
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCharacterDetailsFragment())
        }
    }

    private fun setCursorAdapter(): SimpleCursorAdapter {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        binding.searchView.suggestionsAdapter = cursorAdapter
        return cursorAdapter
    }

    private fun setOnSuggestionListener() {
        binding.searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(p0: Int): Boolean {
                return false
            }


            override fun onSuggestionClick(p0: Int): Boolean {
                view?.hideKeyboard()
                val cursor = binding.searchView.suggestionsAdapter.getItem(p0) as Cursor
                val columnIndex = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1)
                if (columnIndex >= 0){
                    val selection = cursor.getString(columnIndex)
                    binding.searchView.setQuery(selection, false)
                }
                return true
            }

        })
    }

    private fun setOnQueryTextListener(cursorAdapter: SimpleCursorAdapter, suggestions: List<String>) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != searchViewModel.currentQuery.value || query.isNullOrEmpty()){
                    view?.hideKeyboard()
                    searchViewModel.updateQuery(query)

                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {

                            if (query.isNullOrEmpty()) characterAdapter.submitData(PagingData.empty())
                            else searchViewModel.refreshCharactersList(characterAdapter)

                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != searchViewModel.currentQuery.value || query.isNullOrEmpty()){
                    searchViewModel.updateQuery(query)

                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {

                            if (query.isNullOrEmpty()) characterAdapter.submitData(PagingData.empty())
                            else {
                                populateCursorAdapterWithMatchingSuggestions(query, suggestions, cursorAdapter)
                                searchViewModel.refreshCharactersList(characterAdapter)
                            }

                        }
                    }
                }
                return false
            }
        })
    }

    private fun populateCursorAdapterWithMatchingSuggestions(newText: String?, suggestions: List<String>, cursorAdapter: SimpleCursorAdapter) {
        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
        newText?.let {
            suggestions.forEachIndexed { index, suggestion ->
                if (suggestion.contains(newText, true) && suggestion.isNotEmpty()) {
                    cursor.addRow(arrayOf(index, suggestion))
                }
            }
        }
        cursorAdapter.changeCursor(cursor)
    }

    fun View.hideKeyboard(){
        val imn = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}