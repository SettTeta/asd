package sett.teta.moviefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_movie.*

class ViewMovie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        val movieTitle = intent.getStringExtra("title")
        val movieGenre = intent.getStringExtra("genre")
        val movieSyn = intent.getStringExtra("syn")
        val moviePoster = intent.getStringExtra("poster")

        viewTitleTextView.text = movieTitle
        viewGenreTextView.text = movieGenre
        viewSynopsisTextView.text = movieSyn

        Glide.with(this)
            .load(moviePoster)
            .into(viewPosterImageView)

    }
}