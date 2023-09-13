package com.erickpimentel.marvelapp.domain.model

data class Character(
    val id : Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val thumbnailExtension: String,
    val comics: List<String>
)