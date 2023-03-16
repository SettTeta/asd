package sett.teta.moviefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.GeneralAPI
import api.ListMovieResponse
import api.MovieResponse
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieRecView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder().baseUrl("https://www.majorcineplex.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val generalAPI = retrofit.create(GeneralAPI::class.java)

        goToFavouritedButton.setOnClickListener {
            val intent = Intent(this, FavouritePage::class.java)
            startActivity(intent)
        }

        val getListMovieResponse: Call<ListMovieResponse> = generalAPI.getListOfMovies()
        getListMovieResponse.enqueue(object : Callback<ListMovieResponse>{
            override fun onResponse(
                call: Call<ListMovieResponse>,
                response: Response<ListMovieResponse>
            ) {
                val result = response.body()
                if (result != null){
                    val movieData = result.movieResponse

                    movieRecView.adapter = MovieAdapter(sampleMovie(movieData))
                }
            }

            override fun onFailure(call: Call<ListMovieResponse>, t: Throwable) {
                Log.e("GENERAL-API", "Failed to request GoogleResponse ${t.message}")
            }
        })
    }

    private fun sampleMovie(movieData: List<MovieResponse>): List<MovieResponse>{
        val sampleData = mutableListOf<MovieResponse>()
        for ( i in movieData.indices){
            val m = MovieResponse()
            m.poster_url = movieData[i].poster_url
            m.genre = movieData[i].genre
            m.title_en = movieData[i].title_en
            m.release_date = movieData[i].release_date
            m.synopsis_en = movieData[i].synopsis_en
            m.isFavourite = movieData[i].isFavourite
            sampleData.add(m)
        }
        return sampleData
    }

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val movTitle = itemView.titleTextView
        val movGenre = itemView.genreTextView
        val movDate = itemView.dateTextView
        val movPoster = itemView.posterImageView

        var inMovTitle = ""
        var inMovGenre = ""
        var inMovSynopsis = ""
        var inMovPoster = ""

        var isFavourited = false

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(v!!.context, ViewMovie::class.java)
            intent.putExtra("title", inMovTitle)
            intent.putExtra("genre", inMovGenre)
            intent.putExtra("syn", inMovSynopsis)
            intent.putExtra("poster", inMovPoster)

            startActivity(intent)
        }

    }

    inner class MovieAdapter(var movies: List<MovieResponse>): RecyclerView.Adapter<MovieHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view = layoutInflater.inflate(R.layout.row_item, parent, false)
            return MovieHolder(view)
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            holder.movTitle.text = movies[position].title_en
            holder.movDate.text = movies[position].release_date
            holder.movGenre.text = movies[position].genre

            holder.inMovTitle = movies[position].title_en
            holder.inMovGenre = movies[position].genre
            holder.inMovSynopsis = movies[position].synopsis_en
            holder.inMovPoster = movies[position].poster_url

            Glide.with(holder.itemView.context)
                .load(movies[position].poster_url)
                .into(holder.movPoster)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

    }

}