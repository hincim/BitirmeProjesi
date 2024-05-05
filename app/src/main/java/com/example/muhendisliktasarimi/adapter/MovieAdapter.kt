package com.example.muhendisliktasarimi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ItemMovieRowBinding
import com.example.muhendisliktasarimi.state.MoviesState
import com.example.muhendisliktasarimi.view.MovieFragmentDirections


class MovieAdapter(var movieList: MoviesState): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(val binding: ItemMovieRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = DataBindingUtil.inflate<ItemMovieRowBinding>(LayoutInflater.from(parent.context),
            R.layout.item_movie_row,parent,false)
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.movie = movieList.movies[position]
        holder.binding.cardViewMovie.setOnClickListener {
            Navigation.findNavController(it).navigate(MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movieList.movies[position].imdbID))
        }
    }

    override fun getItemCount(): Int {
        return movieList.movies.size
    }

}