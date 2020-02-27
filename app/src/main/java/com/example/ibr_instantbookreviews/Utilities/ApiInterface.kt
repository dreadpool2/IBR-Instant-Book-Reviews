package com.example.ibr_instantbookreviews.Utilities

import com.example.ibr_instantbookreviews.GoodReadsReviews.GoodReadsBookObject
import com.example.ibr_instantbookreviews.GoodReadsReviews.GoodReadsSearchObject
import com.example.ibr_instantbookreviews.GoogleBooksReviews.GoogleBooksObject
import com.example.ibr_instantbookreviews.OtherReviews.OtherReviewsObject
import com.example.ibr_instantbookreviews.OtherReviews.upDateObject
import com.example.ibr_instantbookreviews.OtherReviews.updateCellObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query


interface ApiInterface {
    @GET("book/isbn?format=xml&key=5yfvlbLhJ1Lu7ENx7lVuw")
    fun getXml(@Query("isbn") is_bn: String): Call<GoodReadsBookObject>

    @GET("book/isbn?format=xml&key=5yfvlbLhJ1Lu7ENx7lVuw")
    fun getXmlId(@Query("id") id: String): Call<GoodReadsBookObject>

    @GET("search/index.xml?key=5yfvlbLhJ1Lu7ENx7lVuw")
    fun getSearchedBooks(@Query("q") query: String, @Query("page") page: String): Call<GoodReadsSearchObject>
//q = isbn:1234567890
    @GET("books/v1/volumes")
    fun getJsonGoogle(@Query("q") is_bn: String): Call<GoogleBooksObject>

    @PUT("1Ix_4vX6wufBw4Zt04a4vgwNSaAW3gobewZYWJNOakJc/values/Sheet1!A1:D5?valueInputOption=USER_ENTERED")
    fun updateCell(@Query("access_token") token : String, @Body body: upDateObject) : Call<updateCellObject>

    @GET("1Ix_4vX6wufBw4Zt04a4vgwNSaAW3gobewZYWJNOakJc/values/Sheet1!A41:B43?key=AIzaSyBa8lxt2dSjV5aw9RkU0uh38h6jYCI9mm8")
    fun getJsonOtherReviews() : Call<OtherReviewsObject>
}
