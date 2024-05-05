package com.example.muhendisliktasarimi.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentDictionaryPageScreenBinding
import com.example.muhendisliktasarimi.databinding.FragmentMovieBinding
import com.example.muhendisliktasarimi.domain.model.TranslationRequest
import com.example.muhendisliktasarimi.event.TranslateEvent
import com.example.muhendisliktasarimi.viewmodel.DictionaryViewModel
import com.example.muhendisliktasarimi.viewmodel.MovieViewModel


class DictionaryPageScreen : Fragment(R.layout.fragment_dictionary_page_screen) {
    private lateinit var _fragmentBinding: FragmentDictionaryPageScreenBinding
    private lateinit var viewModel : DictionaryViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dictionary_page_screen,container,false)

        return _fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DictionaryViewModel::class.java]


        _fragmentBinding.button.setOnClickListener {
            viewModel.onEvent(TranslateEvent.Search(TranslationRequest("en","tr","hello")))
        }
    }

    private fun getData(){

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { data ->
                println(it)
            }
        })
    }

}