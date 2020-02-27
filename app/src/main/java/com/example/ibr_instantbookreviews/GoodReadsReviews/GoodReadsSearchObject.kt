package com.example.ibr_instantbookreviews.GoodReadsReviews

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "GoodreadsResponse", strict = true)
data class GoodReadsSearchObject(
    @field:Element(name = "search")
    var search: Search? = null
)

@Root(name = "search", strict = true)
data class Search(
    @field:Element(name = "query")
    var query: String? = null,

    @field:Element(name = "results-start")
    var results_start: Int? = null,

    @field:Element(name = "results-end")
    var results_end: Int? = null,

    @field:Element(name = "total-results")
    var total_results: String? = null,

    @field:Element(name = "source",required = false)
    var source: String? = "",

    @field:Element(name = "query-time-seconds",required = false)
    var query_time_seconds: String? = null,

    @field:Element(name = "results")
    var results: Works? = null
)


@Root(name = "results", strict = true)
data class Works(
    @field:ElementList(entry = "work", inline = true)
    var works: List<WorkResults>? = null
)

@Root(name = "work", strict = true)
data class WorkResults(

    @field:Element(name = "books_count", required = false)
    var books_count: String? = null,

    @field:Element(name = "ratings_count", required = false)
    var ratings_count : String? = null,

    @field:Element(name = "text_reviews_count", required = false)
    var text_reviews_count : String? = null,

    @field:Element(name = "original_publication_year", required = false)
    var original_publication_year  : String? = null,

    @field:Element(name = "original_publication_month", required = false)
    var original_publication_month  : String? = null,

    @field:Element(name = "original_publication_day", required = false)
    var original_publication_day  : String? = null,

    @field:Element(name = "best_book")
    var best_book: BestBook? = null

)

@Root(name = "best_book", strict = true)
data class BestBook(

    @field:Element(name = "id")
    var id: Int? = null,

    @field:Element(name = "title")
    var title: String? = null,

    @field:Element(name = "author")
    var author : Author? = null,

    @field:Element(name = "image_url",required = false)
    var image_url : String? = null,

    @field:Element(name = "small_image_url",required = false)
    var small_image_url : String? = null

)


