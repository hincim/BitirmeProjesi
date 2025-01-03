package com.example.muhendisliktasarimi.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentToolsPageScreenBinding
import com.example.muhendisliktasarimi.domain.model.Words
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
                Toast.makeText(requireContext(),"Yanlış cevap bulunamadı",Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(requireContext(), AIActivity::class.java))
            }
        }
        return _toolsPageScreen.root
    }
}