package api

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(

    var id: String = "",

    @SerializedName("movies") var movieResponse: List<MovieResponse>
)
