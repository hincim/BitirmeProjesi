package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentMovieDetailBinding
import com.example.muhendisliktasarimi.viewmodel.MovieDetailViewModel

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    private var _fragmentBinding: FragmentMovieDetailBinding? = null
    private lateinit var viewModel : MovieDetailViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MovieDetailViewModel::class.java]

        val binding = FragmentMovieDetailBinding.bind(view)
        _fragmentBinding = binding

        arguments?.let {

            viewModel.getMovieDetail(MovieDetailFragmentArgs.fromBundle(it).movieId)
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { data ->
                when {
                    data.isLoading -> {
                        binding.progressBarMovieDetail.visibility = View.VISIBLE
                        binding.textViewErrorMovieDetail.visibility = View.GONE
                        binding.cardViewFilm.visibility = View.GONE
                    }
                    data.error == "Error" -> {
                        binding.progressBarMovieDetail.visibility = View.GONE
                        binding.textViewErrorMovieDetail.visibility = View.VISIBLE
                        binding.cardViewFilm.visibility = View.GONE
                    }
                    else -> {
                        binding.progressBarMovieDetail.visibility = View.GONE
                        binding.textViewErrorMovieDetail.visibility = View.GONE
                        binding.cardViewFilm.visibility = View.VISIBLE
                        binding.selectedMovie = it.movies
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentBinding = null
    }
}