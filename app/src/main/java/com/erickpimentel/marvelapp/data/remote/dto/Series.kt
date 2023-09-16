package com.erickpimentel.marvelapp.data.remote.dto

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)