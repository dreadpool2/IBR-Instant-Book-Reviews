package com.example.ibr_instantbookreviews.GoogleBooksReviews

import com.google.gson.annotations.SerializedName

data class GoogleBooksObject(
    @SerializedName("items")
    var googleBooks: List<GoogleBooks>? = null
)

data class GoogleBooks(
    var id: String? = null,
    @SerializedName("volumeInfo")
    var volumeInfo: VolumeInfo? = null
)

data class VolumeInfo(
    @SerializedName("averageRating")
    var averageRating: Double? = 0.0,

    @SerializedName("ratingsCount")
    var ratingsCount: Int? = 0
)
