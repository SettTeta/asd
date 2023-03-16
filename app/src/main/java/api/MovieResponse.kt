package api

data class MovieResponse (
    var title_en: String = "",
    var release_date: String = "",
    var synopsis_en: String = "",
    var genre: String = "",
    var poster_url: String = "",
    var isFavourite: Boolean = false
)
