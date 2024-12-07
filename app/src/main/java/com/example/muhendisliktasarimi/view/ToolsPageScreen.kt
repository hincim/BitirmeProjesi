package com.example.muhendisliktasarimi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.muhendisliktasarimi.databinding.FragmentToolsPageScreenBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File


class ToolsPageScreen : Fragment() {

    private lateinit var _toolsPageScreen: FragmentToolsPageScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _toolsPageScreen = FragmentToolsPageScreenBinding.inflate(inflater, container, false)

        _toolsPageScreen.cardMean.setOnClickListener {
            findNavController().navigate(ToolsPageScreenDirections.actionToolsPageScreenToFragmentWordMeanFragment(""))
        }

//        _toolsPageScreen.cardWeather.setOnClickListener {
//            findNavController().navigate(ToolsPageScreenDirections.actionToolsPageScreenToWeatherInfoFragment())
//        }

//        _toolsPageScreen.cardMovie.setOnClickListener {
//            findNavController().navigate(ToolsPageScreenDirections.actionToolsPageScreenToMovieFragment())
//        }
        _toolsPageScreen.cardMovie.setOnClickListener {
            val file = File(requireContext().filesDir, "failed_words.csv")
            if (!file.exists()) {
                Snackbar.make(it.rootView, "Yanlış cevap bulunamadı", Snackbar.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(requireContext(), AIActivity::class.java))
            }
        }
        return _toolsPageScreen.root
    }
}