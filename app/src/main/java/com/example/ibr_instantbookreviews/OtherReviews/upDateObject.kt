package com.example.ibr_instantbookreviews.OtherReviews

import com.google.gson.annotations.SerializedName

data class upDateObject(
    @SerializedName("range")
    var range: String,
    @SerializedName("majorDimension")
    var majorDimension: String,
    @SerializedName("values")
    var values: ArrayList<ArrayList<String>>
    )

