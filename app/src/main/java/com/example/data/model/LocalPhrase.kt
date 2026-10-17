package com.example.data.model

data class LocalPhrase(
    val id: String,
    val region: DestinationRegion,
    val categoryName: String,
    val indonesian: String,
    val regionalText: String,
    val pronunciation: String,
    val englishMeaning: String,
    val culturalNote: String? = null
)
