package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentWordAddBinding
import com.example.muhendisliktasarimi.databinding.FragmentWordUpdateBinding
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel


class WordUpdateFragment : Fragment(R.layout.fragment_word_update) {

    private var _fragmentBinding: FragmentWordUpdateBinding? = null
    private lateinit var viewModel: WordsViewModel
    private var word_id: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWordUpdateBinding.bind(view)
        _fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity())[WordsViewModel::class.java]

        arguments?.let {
            word_id = WordUpdateFragmentArgs.fromBundle(it).wordId
            val engWord = WordUpdateFragmentArgs.fromBundle(it).wordEng
            val trWord = WordUpdateFragmentArgs.fromBundle(it).wordTr
            binding.editTextEng.setText(engWord)
            binding.editTextTr.setText(trWord)
        }

        println(word_id)

        binding.buttonUpdate.setOnClickListener {
            val engWord = binding.editTextEng.text.toString()
            val trWord = binding.editTextTr.text.toString()
            viewModel.updateWordById(word_id!!, engWord, trWord)
            requireActivity().onBackPressed()
        }

    }

}