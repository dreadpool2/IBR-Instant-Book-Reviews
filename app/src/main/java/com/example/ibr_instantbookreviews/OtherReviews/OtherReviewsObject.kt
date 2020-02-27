package com.example.ibr_instantbookreviews.OtherReviews

import com.google.gson.annotations.SerializedName
//URL https://sheets.googleapis.com/v4/spreadsheets/1Ix_4vX6wufBw4Zt04a4vgwNSaAW3gobewZYWJNOakJc/values/Sheet1!A27:B28?key=AIzaSyBa8lxt2dSjV5aw9RkU0uh38h6jYCI9mm8

data class OtherReviewsObject(
    @SerializedName("range")
    var range: String,
    @SerializedName("majorDimension")
    var majorDimension: String,
    @SerializedName("values")
    var values: ArrayList<ArrayList<String>>
)
