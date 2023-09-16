package com.erickpimentel.marvelapp.data.remote.dto

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)