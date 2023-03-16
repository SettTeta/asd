package api

import retrofit2.Call
import retrofit2.http.GET

interface GeneralAPI {
    @GET("/apis/get_movie_available")
    fun getListOfMovies(): Call<ListMovieResponse>
}