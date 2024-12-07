package com.example.muhendisliktasarimi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentQuizPageScreenBinding
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel
import com.google.android.material.snackbar.Snackbar


class QuizPageScreen : Fragment() {
    private lateinit var _fragmentBinding: FragmentQuizPageScreenBinding
    private lateinit var viewModel: WordsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_page_screen,container,false)

        return _fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WordsViewModel::class.java]

        _fragmentBinding.cardViewWordCards.setOnClickListener {
            Navigation.findNavController(it).navigate(QuizPageScreenDirections.actionQuizPageScreenToExercisePageScreen())
        }

        _fragmentBinding.cardViewSolveQuestion.setOnClickListener {
            viewModel.getDataFromSQLite()
            if (viewModel.words.value.isNullOrEmpty() || viewModel.words.value!!.size < 4){
                Snackbar.make(_fragmentBinding.root, "Lütfen en az 4 kelime ekleyin", Snackbar.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(requireContext(), SolveQuestionActivity::class.java))
            }
        }
        _fragmentBinding.cardViewScoreTable.setOnClickListener {
            startActivity(Intent(requireContext(), FeedActivity::class.java))
        }
    }

}