package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.adapter.MovieAdapter
import com.example.muhendisliktasarimi.databinding.FragmentMovieBinding
import com.example.muhendisliktasarimi.event.MoviesEvent
import com.example.muhendisliktasarimi.state.MoviesState
import com.example.muhendisliktasarimi.viewmodel.MovieViewModel


class MovieFragment : Fragment(R.layout.fragment_movie), SearchView.OnQueryTextListener {
    private lateinit var _fragmentBinding: FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel : MovieViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false)
        (activity as AppCompatActivity).setSupportActionBar(_fragmentBinding.toolbarMovie)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_search,menu)
                val item = menu.findItem(R.id.action_search).actionView as SearchView
                item.setOnQueryTextListener(this@MovieFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
        return _fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
        movieAdapter = MovieAdapter(MoviesState())
        _fragmentBinding.rvMovie.adapter = movieAdapter
        _fragmentBinding.rvMovie.layoutManager = LinearLayoutManager(view.context)

        getData()


    }


    private fun getData(){

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { data ->
                println(it.movies)
                when {
                    data.isLoading -> {
                        _fragmentBinding.progressBarMovie.visibility = View.VISIBLE
                        _fragmentBinding.textViewMovieNoData.visibility = View.GONE
                    }
                    data.error == "Error" -> {
                        _fragmentBinding.textViewMovieNoData.visibility = View.VISIBLE
                        _fragmentBinding.rvMovie.visibility = View.GONE
                        _fragmentBinding.progressBarMovie.visibility = View.GONE
                    }
                    else -> {
                        _fragmentBinding.progressBarMovie.visibility = View.GONE
                        _fragmentBinding.textViewMovieNoData.visibility = View.GONE
                        _fragmentBinding.rvMovie.visibility = View.VISIBLE
                        movieAdapter.movieList = it
                        movieAdapter.notifyDataSetChanged()
                    }
                }

            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            if (query.isNotEmpty())
                viewModel.onEvent(MoviesEvent.Search(query.lowercase().trim()))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}