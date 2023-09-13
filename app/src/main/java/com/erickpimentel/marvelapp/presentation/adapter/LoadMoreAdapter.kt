package com.erickpimentel.marvelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erickpimentel.marvelapp.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry: () -> Unit): LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMoreAdapter.ViewHolder {
        return ViewHolder(LoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false), retry)
    }

    override fun onBindViewHolder(holder: LoadMoreAdapter.ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: LoadMoreBinding, retry: () -> Unit): RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(states: LoadState){
            binding.apply {
                progressBarLoadMore.isVisible = states is LoadState.Loading
                textViewError.isVisible = states is LoadState.Error
                buttonRetry.isVisible = states is LoadState.Error
            }
        }
    }
}