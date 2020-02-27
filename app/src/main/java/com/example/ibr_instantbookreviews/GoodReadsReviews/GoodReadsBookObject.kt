package com.example.ibr_instantbookreviews.GoodReadsReviews

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "GoodreadsResponse", strict = false)
data class GoodReadsBookObject(
    @field:Element(name = "book")
    var book: Book? = null
)

@Root(name = "book", strict = true)
data class Book(
    @field:Element(name = "id")
    var id: Int? = null,

    @field:Element(name = "title")
    var title: String? = null,

    @field:Element(name = "isbn",required = false)
    var isbn: String? = null,

    @field:Element(name = "isbn13")
    var isbn13: String? = null,

    @field:Element(name = "asin",required = false)
    var asin: String? = "",

    @field:Element(name = "kindle_asin",required = false)
    var kindle_asin: String? = null,

    @field:Element(name = "marketplace_id",required = false)
    var marketplace_id: String? = null,

    @field:Element(name = "country_code",required = false)
    var country_code: String? = null,

    @field:Element(name = "image_url",required = false)
    var image_url: String? = null,

    @field:Element(name = "small_image_url",required = false)
    var small_image_url: String? = null,

    @field:Element(name = "publication_year",required = false)
    var publication_year: String? = null,

    @field:Element(name = "publication_month",required = false)
    var publication_month: String? = null,

    @field:Element(name = "publication_day",required = false)
    var publication_day: String? = null,


    @field:Element(name = "publisher",required = false)
    var publisher: String? = null,

    @field:Element(name = "language_code",required = false)
    var language_code: String? = null,

    @field:Element(name = "is_ebook",required = false)
    var is_ebook: String? = null,

    @field:Element(name = "description",required = false)
    var description: String? = null,

    @field:Element(name = "work",required = false)
    var work: Work? = null,

    @field:Element(name = "average_rating",required = false)
    var average_rating: String? = null,

    @field:Element(name = "num_pages",required = false)
    var num_pages: String? = null,


    @field:Element(name = "format",required = false)
    var format: String? = null,

    @field:Element(name = "edition_information",required = false)
    var edition_information: String? = null,

    @field:Element(name = "ratings_count",required = false)
    var ratings_count: String? = null,

    @field:Element(name = "text_reviews_count",required = false)
    var text_reviews_count: String? = null,

    @field:Element(name = "url",required = false)
    var url: String? = null,

    @field:Element(name = "link",required = false)
    var link: String? = null,

    @field:Element(name = "authors", required = false)
    var authors: Authors? = null,

    @field:Element(name = "reviews_widget",required = false)
    var reviews_widget: String? = null,

    @field:Element(name = "book_links",required = false)
    var book_links: String? = null,

    @field:Element(name = "buy_links",required = false)
    var buy_links: String? = null,

    @field:Element(name = "series_works",required = false)
    var series_works: String? = null

)

data class Work(
    @field:Element(name = "id")
    var id: Int? = null,

    @field:Element(name = "books_count",required = false)
    var books_count: String? = null,

    @field:Element(name = "best_book_id",required = false)
    var best_book_id: String? = null,

    @field:Element(name = "reviews_count",required = false)
    var reviews_count: String? = null,

    @field:Element(name = "ratings_sum",required = false)
    var ratings_sum: String? = null,

    @field:Element(name = "ratings_count",required = false)
    var ratings_count : String? = null,

    @field:Element(name = "text_reviews_count",required = false)
    var text_reviews_count : String? = null,

    @field:Element(name = "original_publication_year",required = false)
    var original_publication_year  : String? = null,

    @field:Element(name = "original_publication_month",required = false)
    var original_publication_month  : String? = null,

    @field:Element(name = "original_publication_day",required = false)
    var original_publication_day  : String? = null,

    @field:Element(name = "original_title",required = false)
    var original_title : String? = null,

    @field:Element(name = "original_language_id",required = false)
    var original_language_id  : String? = null,

    @field:Element(name = "media_type",required = false)
    var media_type  : String? = null,

    @field:Element(name = "rating_dist",required = false)
    var rating_dist  : String? = null,

    @field:Element(name = "desc_user_id",required = false)
    var desc_user_id   : String? = null,

    @field:Element(name = "default_chaptering_book_id",required = false)
    var default_chaptering_book_id   : String? = null,


    @field:Element(name = "default_description_language_code",required = false)
    var default_description_language_code   : String? = null,


    @field:Element(name = "work_uri",required = false)
    var work_uri  : String? = null
)

@Root(name = "authors", strict = false)
data class Authors(
    @field:ElementList(entry = "author", inline = true)
    var author: List<Author>? = null
)

@Root(name = "author", strict = true)
data class Author(

    @field:Element(name = "id")
    var id: Int? = null,

    @field:Element(name = "name")
    var name: String? = null,

    @field:Element(name = "role",required = false)
    var role: String? = null,

    @field:Element(name = "image_url",required = false)
    var image_url : String? = null,

    @field:Element(name = "small_image_url",required = false)
    var small_image_url : String? = null,

    @field:Element(name = "link",required = false)
    var link : String? = null,

    @field:Element(name = "average_rating",required = false)
    var average_rating : String? = null,

    @field:Element(name = "ratings_count",required = false)
    var ratings_count  : String? = null,

    @field:Element(name = "text_reviews_count",required = false)
    var text_reviews_count  : String? = null


)
