package com.example.muhendisliktasarimi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentQuizPageScreenBinding


class QuizPageScreen : Fragment() {
    private lateinit var _fragmentBinding: FragmentQuizPageScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentBinding =
            FragmentQuizPageScreenBinding.inflate(inflater,container,false)

        _fragmentBinding.cardViewWordCards.setOnClickListener {
            Navigation.findNavController(it).navigate(QuizPageScreenDirections.actionQuizPageScreenToExercisePageScreen())
        }
        _fragmentBinding.cardViewSolveQuestion.setOnClickListener {
            startActivity(Intent(requireContext(), SolveQuestionActivity::class.java))
        }
        _fragmentBinding.cardViewScoreTable.setOnClickListener {
            startActivity(Intent(requireContext(), FeedActivity::class.java))
        }
        return _fragmentBinding.root
    }

}